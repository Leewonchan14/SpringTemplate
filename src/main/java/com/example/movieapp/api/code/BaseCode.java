package com.example.movieapp.api.code;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
