package com.example.movieapp.api.exception;
import com.example.movieapp.api.code.BaseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private final BaseCode baseCode;

    public CustomException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
