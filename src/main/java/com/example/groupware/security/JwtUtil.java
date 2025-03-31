package com.example.groupware.security;

import com.example.groupware.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // 비밀키는 환경변수나 보안 vault를 통해 관리하는 것이 안전함
    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkeymysecretkey"; // 너무 길면 base64 인코딩해서 저장하는 방법도 있음
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24시간

    // 비밀키를 HMAC SHA 방식으로 생성
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 생성
    public String createToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username) // subject로 username 설정
                .claim("role", role.name()) // 역할 정보 추가
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .signWith(getSigningKey()) // 서명
                .compact();
    }

    // JWT에서 username 추출
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // JWT에서 Role 추출
    public Role extractRole(String token) {
        String roleStr = getClaims(token).get("role", String.class); // "role" claim에서 역할 정보 추출
        return Role.valueOf(roleStr); // String -> Role Enum 변환
    }

    // JWT 유효성 검사
    public boolean isValid(String token) {
        try {
            return extractUsername(token) != null && !isTokenExpired(token); // 유효성 검사
        } catch (JwtException e) {
            return false; // JWT 예외가 발생하면 유효하지 않음
        }
    }

    // 토큰 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰 만료 시간 추출
    private Date extractExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    // Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 서명 검증
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 갱신 (만료된 경우)
    public String refreshToken(String oldToken) {
        if (isTokenExpired(oldToken)) {
            String username = extractUsername(oldToken);
            Role role = extractRole(oldToken);
            return createToken(username, role); // 새 토큰 생성
        }
        return oldToken; // 만료되지 않았으면 기존 토큰 반환
    }
}
