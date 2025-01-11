package com.umbra.umbralink.security.jwt;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private final long expiration = 15 * 60 * 1000;
  private final Date expirationTime = new Date(System.currentTimeMillis() + expiration);

  @Value("${SECRET_KEY}")
  private String secretKey;

  public String generateToken(UserDetails user) {
    return Jwts.builder()
        .issuer("UmbraLink Dev Team")
        .subject(user.getUsername())
        .issuedAt(new Date())
        .expiration(expirationTime)
        .signWith(generateKey())
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
    Claims claims = Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
    return claims;
  }

  public boolean isTokenValid(String jwt) {
    Claims claims = getClaims(jwt);
    return claims.getExpiration().after(Date.from(Instant.now()));
  }

}
