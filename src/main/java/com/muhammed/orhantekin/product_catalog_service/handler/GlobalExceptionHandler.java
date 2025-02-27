package com.muhammed.orhantekin.product_catalog_service.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // DTO Validasyon Hatası (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, List<String>> errorsMap = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errorsMap.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>()).add(fieldError.getDefaultMessage());
            }
        }
        return buildErrorResponse(errorsMap, HttpStatus.BAD_REQUEST, request);
    }


    // JSON Formatı Hatalıysa (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return buildErrorResponse("Invalid or empty request body. Please provide a valid JSON input.", HttpStatus.BAD_REQUEST, request);
    }



    // Parametre Türü Yanlışsa (400)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError<String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = String.format("Invalid value for parameter '%s': '%s'. Expected type: %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, request);
    }



    // Aranan Kayıt Bulunamazsa , pathvariable ve request param anotasyonları için(404)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError<String>> handleBaseException(BaseException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }


    // Endpoint Bulunamadığında (404)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError<String>> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        return buildErrorResponse("Endpoint not found: " + ex.getRequestURL(), HttpStatus.NOT_FOUND, request);
    }


    // HTTP Method Yanlış Kullanılırsa (405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError<String>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String message = String.format("HTTP Method '%s' not allowed. Supported methods: %s",
                ex.getMethod(), String.join(", ", ex.getSupportedMethods()));
        return buildErrorResponse(message, HttpStatus.METHOD_NOT_ALLOWED, request);
    }


    // Genel Exception Yakalama (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError<String>> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    // Hata Yanıtı Oluşturma
    private <E> ResponseEntity<ApiError<E>> buildErrorResponse(E message, HttpStatus status, WebRequest request) {
        ApiError<E> apiError = new ApiError<>();

        apiError.setStatus(status.value());

        Exceptionn<E> exceptionn = new Exceptionn<>();
        exceptionn.setCreateTime(new Date());
        exceptionn.setPath(request.getDescription(false).substring(4));
        exceptionn.setMessage(message);

        apiError.setExceptionn(exceptionn);
        return ResponseEntity.status(status).body(apiError);
    }
}
