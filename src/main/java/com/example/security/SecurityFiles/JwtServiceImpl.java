package com.example.security.SecurityFiles;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServiceImpl implements JwtService {
  // jwt token은 최소 256비트 이상의 키를 사용해야함
  final static private String SECRET_KEY = "b9Q4SZaZmAYmL/F7p+NDbZHIrHoOp1CFR6JAJu7opyQ=";
  final static private Integer SECOND = 1000;
  final static private Integer MINUTE = SECOND * 60;
  final static private Integer HOUR = MINUTE * 60;

  public String extractToken(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    if (authorization == null) {
      return null;
    }
    authorization = authorization.trim();
    if (!authorization.startsWith("Bearer")) {
      return null;
    }
    // 헤더에서 Bearer 뒤에 있는 토큰을 추출
    return authorization.substring(7);
  }

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // 람다 함수를 사용하여 토큰에서 클레임을 추출
  // ex) 토큰에서 userId 추출
  // extractClaim(jwt, Claims::getSubject)
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // 추가적인 클레임이 없을때 오버로딩
  public String generateToken(User user) {
    return generateToken(new HashMap<>(), user);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      User user) {
    return Jwts.builder()
        // 서명
        .signWith(getSecretKey())
        // 클레임에 추가적인 클레임 추가
        .claims(extraClaims)
        // 클레임에 userId 추가
        .subject(user.getEmail())
        // 발급시간을 현재 시간으로 설정
        .issuedAt(new Date(System.currentTimeMillis()))
        // 만료시간을 24시간으로 설정
        .expiration(new Date(System.currentTimeMillis() + HOUR * 24))
        .compact();
  }

  public Claims extractAllClaims(String token) {
    // 11 jjwt 버전
    // return
    // Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    return Jwts
        // jwt 파서 생성
        .parser()
        // 서명키 설정
        .verifyWith(getSecretKey())
        .build()
        // 파싱할 토큰 설정
        .parseSignedClaims(token)
        // 토큰의 페이로드 반환
        .getPayload();
  }

  public boolean isTokenValid(String token, User user) {
    String email = extractEmail(token);
    // 제공한 userId와 토큰의 userId가 같고 토큰이 만료되지 않았다면 true 반환
    return (email.equals(user.getEmail())) && !isTokenExpired(token);
  }

  // 토큰이 만료되었는지 확인
  private boolean isTokenExpired(String token) {
    // 지금 시간이 토큰의 만료시간보다 늦다면 true 반환
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // 서명키
  private SecretKey getSecretKey() {
    // 서명키의 바이트 배열을 생성
    byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    // hmacShaKeyFor는 주어진 바이트 배열을 사용하여 HMAC-SHA 키를 생성합니다.
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
