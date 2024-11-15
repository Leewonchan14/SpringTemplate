package com.example.security.SecurityFiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        // csrf 설정을 비활성화
        .csrf(Customizer.withDefaults())
        // http 기본 인증 해제
        .httpBasic(Customizer.withDefaults())
        // form 기반 인증 해제
        .formLogin(Customizer.withDefaults())
        // 세션 생성 정책 설정 (STATELESS: 세션을 사용하지 않음)
        .sessionManagement(
            authorize -> authorize
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorize -> authorize
                // 모든 요청에 대해 허용
                .requestMatchers("/**")
                .permitAll())

        .addFilterBefore(
            jwtAuthFilter, UsernamePasswordAuthenticationFilter.class //
        );

    return http.build();
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
