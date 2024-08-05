package com.example.movieapp.api.exception;

import com.example.movieapp.api.payload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.movieapp.api.code.ErrorCode._INTERNAL_SERVER_ERROR;


@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getBaseCode().getHttpStatus()).body(
                ApiResponse.onFailure(e.getBaseCode(), null)
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(500).body(
                ApiResponse.onFailure(_INTERNAL_SERVER_ERROR.getCode(), e.getMessage(), null)
        );
    }
}
