package br.ueg.posse.tsi.exceptions;


import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Essa classe implementamos as exceções mais comuns e um método mais geral para tratar as exceções que não foram tratadas individualmente
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PeopleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
        PeopleNotFoundException e){
            return buildErrorResponse(HttpStatus.NOT_FOUND, 
                "NOT_FOUND", e.getMessage());
    }
    @ExceptionHandler(PeopleAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(PeopleAlreadyExistsException e){
        return buildErrorResponse(HttpStatus.CONFLICT, 
                "CONFLICT", e.getMessage());
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException e){
         return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY,
                "BUSINESS_ERROR",
                 e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException e
    ){
        String message= e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(f -> f.getField()+":"+f.getDefaultMessage())
                        .collect(Collectors.joining(", "));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, 
            "VALIDATION_ERROR", message);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception e){
         return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
            "INTERNAL_SERVER_ERROR", e.getMessage());
    }
    private ResponseEntity<Map<String, Object>> buildErrorResponse(
        HttpStatus status, String error, String message){
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("status", status.value());
            body.put("error", error);
            body.put("message", message);
            body.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(status).body(body);
            
    }
}
