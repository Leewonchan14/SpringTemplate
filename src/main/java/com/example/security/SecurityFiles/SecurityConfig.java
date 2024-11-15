package com.example.security.SecurityFiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthFilter jwtAuthFilter;
  // 허용하는 URL 패턴 목록

  private static final String[] ALLOWED_URLS = {
      "/swagger-ui/**",
      "/v3/api-docs*/**",
      "/api/auth/**",
      "/api/public/**",
  };

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // csrf 설정을 비활성화
        .csrf(AbstractHttpConfigurer::disable)
        // http 기본 인증 해제
        .httpBasic(AbstractHttpConfigurer::disable)
        // form 기반 인증 해제
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(authorize -> {
          authorize.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        })
        .authorizeHttpRequests(authorize -> {
          authorize
              .requestMatchers(ALLOWED_URLS).permitAll()
              .anyRequest().authenticated();
        })
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
