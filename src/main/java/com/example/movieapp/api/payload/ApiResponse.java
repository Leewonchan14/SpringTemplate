package com.example.movieapp.api.payload;

import com.example.movieapp.api.code.BaseCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class ApiResponse<T> {
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T result;

    public static <T> ApiResponse<T> onSuccess(BaseCode baseCode, T result) {
        return new ApiResponse<>(baseCode.getCode(), baseCode.getMessage(), result);
    }

    // 실패한 경우 응답 생성
    public static <T> ApiResponse<T> onFailure(BaseCode baseCode, T data) {
        return new ApiResponse<>(baseCode.getCode(), baseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
