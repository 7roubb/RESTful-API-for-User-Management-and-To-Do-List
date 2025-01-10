package com.cotede.todolist.exceptions;

import com.cotede.todolist.common.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(CustomExceptions.UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExistsException(CustomExceptions.UserAlreadyExistsException ex) {
        String message = messageSource.getMessage("exception.user.already.exists", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(message, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid user data")
                .content(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomExceptions.UserNotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(CustomExceptions.UserNotFound ex) {
        String message = messageSource.getMessage("user.not.found", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(message, HttpStatus.NOT_FOUND));
    }
    @ExceptionHandler(CustomExceptions.EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExists(CustomExceptions.EmailAlreadyExistsException ex) {
        String message = messageSource.getMessage("exception.email.already.exists", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(message, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(CustomExceptions.TaskNotFound.class)
    public ResponseEntity<ApiResponse<Void>> handleTaskNotFound(CustomExceptions.TaskNotFound ex) {
        String message = messageSource.getMessage("task.not.found", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(message, HttpStatus.NOT_FOUND));

    }
    @ExceptionHandler(CustomExceptions.WrongPasswordOrEmail.class)
    public ResponseEntity<ApiResponse<Void>> handleWrongPasswordOrEmail(CustomExceptions.WrongPasswordOrEmail ex) {
        String message = messageSource.getMessage("bad.credential", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(message, HttpStatus.UNAUTHORIZED));

    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException ex) {
        String message = messageSource.getMessage("bad.credential", new Object[]{ex.getMessage()}, LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(message, HttpStatus.UNAUTHORIZED));

    }


//    @ExceptionHandler(Exception.class)
//    public ProblemDetail handleSecurityException(Exception ex) {
//        ProblemDetail errorDetail = null;
//        if (ex instanceof BadCredentialsException) {
//            errorDetail = ProblemDetail
//                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
//            errorDetail.setProperty("access_denied_reason", "Authentication Failure");
//        }
//
//        if (ex instanceof AccessDeniedException) {
//            errorDetail = ProblemDetail
//                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            errorDetail.setProperty("access_denied_reason", "not_authorized!");
//
//        }
//
//        if (ex instanceof SignatureException) {
//            errorDetail = ProblemDetail
//                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            errorDetail.setProperty("access_denied_reason", "JWT Signature not valid");
//        }
//        if (ex instanceof ExpiredJwtException) {
//            errorDetail = ProblemDetail
//                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
//            errorDetail.setProperty("access_denied_reason", "JWT Token already expired !");
//        }
//
//        return errorDetail;
//    }
}
