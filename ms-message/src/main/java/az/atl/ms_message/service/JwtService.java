package az.atl.ms_message.service;

import az.atl.ms_message.dao.entity.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateToken(UserEntity userEntity);
    String generateToken(Map<String, Object> extraClaims, UserEntity userEntity);
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims,T> claimsResolver);
    Claims extractClaims(String token);
    Long extractId(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
