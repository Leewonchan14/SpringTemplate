package com.example.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.Dtos.UserDTO;
import com.example.security.SecurityFiles.JwtService;
import com.example.security.SecurityFiles.User;
import com.example.security.Services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

  final private JwtService jwtService;
  final private UserService userService;

  @PostMapping()
  public String createToken(
      @RequestBody UserDTO.CreateRequest request) {

    User findUser = userService.findOrCreate(request);

    return jwtService.generateToken(findUser);
  }

  @GetMapping()
  public String isAuthenticated() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("auth = " + auth);
    System.out.println("auth.getPrincipal() = " + auth.getPrincipal());
    System.out.println("auth.getAuthorities() = " + auth.getAuthorities());
    System.out.println("auth.getCredentials() = " + auth.getCredentials());
    System.out.println("auth.getName() = " + auth.getName());
    System.out.println("auth.getDetails() = " + auth.getDetails());

    return "" + !auth.getPrincipal().toString().equals("anonymousUser");
  }

}
