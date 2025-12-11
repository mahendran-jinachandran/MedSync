package com.medsync.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final JwtProperties properties;
    private final Key key;

    public JwtTokenProvider(JwtProperties properties) {
        this.properties = properties;
        this.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    public String generateAccessToken(Long userId, String email, Set<String> roles) {

        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(properties.getAccessTokenExpirationSeconds());

        // store as comma-separated string, e.g. "PATIENT,CLINIC_ADMIN"
        String rolesString = String.join(",", roles);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim("email", email)
                .claim("roles", rolesString)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public Set<String> getRoles(String token) {
        Claims claims = getClaims(token);
        Object raw = claims.get("roles");

        if (raw == null) {
            return Collections.emptySet();
        }

        // Case 1: stored as "CLINIC_ADMIN" or "CLINIC_ADMIN,SYSTEM_ADMIN"
        if (raw instanceof String) {
            String s = (String) raw;
            if (s.trim().isEmpty()) {
                return Collections.emptySet();
            }
            return Arrays.stream(s.split(","))
                    .map(String::trim)
                    .filter(str -> !str.isEmpty())
                    .collect(Collectors.toSet());
        }

        // Case 2: stored as JSON array in future: ["CLINIC_ADMIN", "SYSTEM_ADMIN"]
        if (raw instanceof Collection) {
            Collection<?> coll = (Collection<?>) raw;
            return (Set<String>) coll.stream()
                    .map(Object::toString)
                    .map(String::trim)
                    .filter(str -> !str.isEmpty())
                    .collect(Collectors.toSet());
        }

        // Fallback: single non-null value
        return Collections.singleton(raw.toString().trim());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
