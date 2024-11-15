package com.example.security.SecurityFiles;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  final private JwtService jwtService;
  final private UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain //
  ) throws ServletException, IOException {
    String token = jwtService.extractToken(request);
    System.out.println("token = " + token);

    // 토큰이 없으면 다음 필터로 넘어감
    if (token == null) {
      System.out.println("token is null");
      filterChain.doFilter(request, response);
      return;
    }

    String email = jwtService.extractEmail(token);

    // 토큰이 유효하지 않거나 SecurityContextHolder에 인증 객체가 이미 있으면 다음 필터로 넘어감
    SecurityContext securityContext = SecurityContextHolder.getContext();
    if (email == null || securityContext.getAuthentication() != null) {
      filterChain.doFilter(request, response);
      return;
    }

    // 토큰에서 추출한 이메일로 유저를 찾음
    User findUser = userRepository.findByEmail(email).orElse(null);

    // 유저가 존재하지 않으면 다음 필터로 넘어감
    if (findUser == null) {
      filterChain.doFilter(request, response);
      return;
    }

    // 토큰이 유효하지 않으면 다음 필터로 넘어감
    if (!jwtService.isTokenValid(token, findUser)) {
      filterChain.doFilter(request, response);
      return;
    }

    // 토큰에서 이메일을 추출하여 해당하는 유저가 존재하면 SecurityContextHolder에 인증 객체를 넣어줌
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        findUser,
        null,
        findUser.getAuthorities());

    securityContext.setAuthentication(authToken);

    // 인증로직에 성공하면 다음 필터로 넘어가게끔 설정!
    filterChain.doFilter(request, response);
  }

}
