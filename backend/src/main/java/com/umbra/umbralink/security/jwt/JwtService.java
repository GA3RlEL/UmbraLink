package com.umbra.umbralink.security.jwt;

import java.time.Instant;
import java.util.*;

import javax.crypto.SecretKey;

import com.umbra.umbralink.model.UserEntity;
import com.umbra.umbralink.repository.UserRepository;
import com.umbra.umbralink.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final UserRepository userRepository;

    @Value("${SECRET_KEY}")
    private String secretKey;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateToken(UserDetails user) {
        Optional<UserEntity> userFound = userRepository.findByEmail(user.getUsername());
        Map<String, Long> claims = new HashMap<>();
        if (userFound.isPresent()) {
            UserEntity userTaken = userFound.get();
            claims.put("id", userTaken.getId());
        }
        return Jwts.builder()
                .issuer("UmbraLink Dev Team")
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusMillis(3600000 * 72)))
                .signWith(generateKey())
                .claims(claims)
                .compact();
    }

    private SecretKey generateKey() {
        byte[] key = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public String extractEmail(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
    }

    public boolean isTokenValid(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    public Long extractId(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.get("id", Long.class);
    }

}
