package com.todo.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    SecretKey key =  Keys.hmacShaKeyFor(this.secret.getBytes(StandardCharsets.UTF_8));
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key ,Jwts.SIG.HS512)
                .compact();
    }

    public Claims extractUsername(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }


}
