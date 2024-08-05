package com.example.movieapp.api.code;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode implements BaseCode {
    // COMMON
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "Internal Server Error"),

    // MOVIE API
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "MOVIE_2001", "영화를 찾을 수 없습니다."),
    MOVIE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "MOVIE_2002", "이미 존재하는 영화입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;


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
