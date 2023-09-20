package az.atl.ms_auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable().authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/swagger-ui",
                        "/swagger-ui/**",
                        "/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/users/getAll").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/admin/{userId}").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET,"/users/get/{userName}").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/users/current").permitAll()
                .requestMatchers(HttpMethod.DELETE,"/users/admin/{userId}").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/users/updateUser/").permitAll()
                .requestMatchers(HttpMethod.PUT,"/users/password/").permitAll()
                .requestMatchers(HttpMethod.GET, "/test/user/hello").permitAll()
                .requestMatchers(HttpMethod.GET, "/test/admin/hello").hasAnyAuthority("ADMIN")


                .requestMatchers(HttpMethod.GET,"/message/send").permitAll()
                .requestMatchers(HttpMethod.GET, "/message/myMessages").permitAll()
                .requestMatchers(HttpMethod.GET, "/message/myDialogue").permitAll()
                .requestMatchers(HttpMethod.GET, "/message/admin/get").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/message/admin/delete").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/message/delete/{messageId}").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}