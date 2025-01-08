package com.cotede.todolist.common;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ApiResponse<T> {

    private T content;
    private HttpStatus status;
    private String message;

    public static <T> ApiResponse<T> success(T content, HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .content(content)
                .status(status)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .content(null)
                .status(status)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> of(T content, HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .content(content)
                .status(status)
                .message(message)
                .build();
    }

}