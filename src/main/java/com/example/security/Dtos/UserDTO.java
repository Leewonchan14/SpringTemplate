package com.example.security.Dtos;

import lombok.Getter;

public class UserDTO {

  @Getter
  public class CreateRequest {
    private String email;
    private String password;
  }
}
