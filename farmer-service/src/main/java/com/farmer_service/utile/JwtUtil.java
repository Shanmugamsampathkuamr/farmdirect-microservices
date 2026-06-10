package com.farmer_service.utile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY ="u9X2Kz6mB4vW7qN1xY3pE5sL8tJ0wQ2zM4vR6xY8bN1pQ3sT5vW7xZ9aC2eF4gH6";

    private static final long EXPIRATION_TIME = 86400000;//24 h

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email, String role , Long userId){
        Map<String , Object> claims = new HashMap<>();
        claims.put("role",role);
        claims.put("userId",userId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }
    public String extractRole(String token){
        return getClaims(token).get("role",String.class);
    }

    public Long extractUserId(String token){
        return getClaims(token).get("userId",Long.class);

    }

    public boolean validateToken(String token){
        try{
            getClaims(token);
            return true;

        }catch (Exception e){
            return false;
        }
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
