package az.atl.ms_auth.service.impl;



import az.atl.ms_auth.dao.entity.UserEntity;
import az.atl.ms_auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl  implements JwtService {
    private static final String SECRET_KEY = "7B6C5D4E3F2G1H8I9J0K1L2M3N4O5P6Q7R8S9T0U1V2W3X4Y5Z6A7B8C9D0E1F2G";
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    public String generateToken(UserEntity userEntity){
        return generateToken(new HashMap<>(), userEntity);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserEntity userEntity){
        logger.info("Generating token for user: {}", userEntity.getUsername());

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userEntity.getUsername())
                .setHeader(Map.of("type", "JWT"))
                .addClaims(Map.of("id", userEntity.getId()))
                .addClaims(Map.of("name", userEntity.getUsername()))
                .addClaims(Map.of("role", userEntity.getRole()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(1000*60*24)))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

        logger.info("Successfully generated token for user: {}", userEntity.getUsername());

        return token;
    }

    public String extractUsername(String token){
        logger.info("Extracting username from token: {}", token);

        String username = extractClaim(token, Claims::getSubject);

        logger.info("Successfully extracted username from token: {}", username);

        return username;
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims =extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractClaims(String token) {
        return extractAllClaims(token);
    }

    public Long extractId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        logger.info("Checking if token is valid for user: {}", userDetails.getUsername());


        final String username=extractUsername(token);
        boolean isValid = username.equals(userDetails.getUsername()) &&
                !isTokenExpired(token);

        if (isValid) {
            logger.info("Token is valid for user: {}", userDetails.getUsername());
        } else {
            logger.error("Token is not valid for user: {}", userDetails.getUsername());
        }

        return isValid;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration );
    }



}
