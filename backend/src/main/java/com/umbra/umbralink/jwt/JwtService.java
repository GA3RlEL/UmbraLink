package com.umbra.umbralink.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.umbra.umbralink.model.UserEntity;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

  private final long expiration = 15 * 60 * 1000;
  private final Date expirationTime = new Date(System.currentTimeMillis() + expiration);

  private String generateToken(UserEntity user) {
    return Jwts.builder()
        .issuer("UmbraLink Dev Team")
        .subject(user.getEmail())
        .issuedAt(new Date())
        .expiration(expirationTime)
        // .signWith() is required to add
        .compact();
  }

}
