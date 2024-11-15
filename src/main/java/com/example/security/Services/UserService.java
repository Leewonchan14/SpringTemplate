package com.example.security.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.Dtos.UserDTO;
import com.example.security.SecurityFiles.User;
import com.example.security.SecurityFiles.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
  final private UserRepository userRepository;
  final private PasswordEncoder passwordEncoder;

  public User findOrCreate(UserDTO.CreateRequest request) {
    Optional<User> findUserOpt = userRepository.findByEmailAndPassword(
        request.getEmail(), request.getPassword() //
    );

    return findUserOpt.orElseGet(
        () -> userRepository.save(User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build()) //
    );
  }
};
