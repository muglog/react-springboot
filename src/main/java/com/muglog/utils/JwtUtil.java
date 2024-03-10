package com.muglog.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static String jwtKey;

    @Value("${jwt.key}")
    public void setJwtKey(String value) {
        jwtKey = value;
    }

    public static String createJwtToken(Long userId) {
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("typ", "JWT");
        jwtHeader.put("alg", "HS512");

        Map<String, Object> jwtPayload = new HashMap<>();
        jwtPayload.put("user_id", userId.toString());

        Long expiredTime = 1000 * 60L * 60L * 24L * 3L; // 7일
        Date date = new Date();
        date.setTime(date.getTime() + expiredTime);

        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setHeader(jwtHeader)
                .setClaims(jwtPayload)
                .setExpiration(date)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public static String getUserIdByJwtToken(String jwtToken){
        return (String) Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("user_id");
    }
}
