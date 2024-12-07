package com.devano.blog_app.service;

import com.devano.blog_app.properties.SecretProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtService {

    private SecretProperties secretProperties;



    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        claims.put("iss",secretProperties.getJwtIss());
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(10*60)))
                .signWith(generateKey()).compact();
    }

    public SecretKey generateKey() {
        byte[] secrets = Base64.getDecoder().decode(secretProperties.getSecretKey());
        return Keys.hmacShaKeyFor(secrets);
    }

   public String getUsername(String token) {
        return getClaims(token).getSubject();
   }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token) {
        return null;
    }

    public Boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(Date.from(Instant.now()));
    }
}
