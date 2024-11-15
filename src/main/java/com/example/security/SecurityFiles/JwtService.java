package com.example.security.SecurityFiles;

import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
  String extractToken(HttpServletRequest request);

  String extractEmail(String token);

  // 람다 함수를 사용하여 토큰에서 클레임을 추출
  // ex) 토큰에서 userId 추출
  // extractClaim(jwt, Claims::getSubject)
  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  // 추가적인 클레임이 없을때 오버로딩
  String generateToken(User user);

  String generateToken(Map<String, Object> extraClaims, User user);

  Claims extractAllClaims(String token);

  boolean isTokenValid(String token, User user);
}
