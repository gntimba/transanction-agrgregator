package gti.ingestorservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            ValidationException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("errors", ex.getErrors());

        return ResponseEntity.badRequest().body(body);
    }
}