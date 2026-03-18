package org.example.springbootdb_restapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
  private static final String SECRET = "mysupersecretmysupersecretmysupersecret";
  private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
  public static String generateToken(String username){

    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
            .signWith(key)
            .compact();
  }
  public static String extractUsername(String token){

    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }
}
