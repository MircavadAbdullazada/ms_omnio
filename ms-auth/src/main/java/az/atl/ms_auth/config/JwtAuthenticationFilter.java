package az.atl.ms_auth.config;

import az.atl.ms_auth.service.impl.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtServiceImpl jwtService;
    private final UserDetailsService userDetailsService;




    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwt;
        final String userName;
        logger.info("This is my own jwt filter");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
         jwt = authHeader.substring(7);


        userName = jwtService.extractUsername(jwt);
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                log.info("Claims {}", jwtService.extractClaims(jwt));
                log.info("User id {}", jwtService.extractId(jwt));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }


        filterChain.doFilter(request,response);
    }

}
