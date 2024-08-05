package com.example.movieapp.api.code;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SuccessCode  implements BaseCode {
    MOVIE_OK(HttpStatus.OK, "MOVIE_2000", "성공");

    final HttpStatus status;
    final String code;
    final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
