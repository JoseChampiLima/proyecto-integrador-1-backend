package com.deportido.refactor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.deportido.exception.BadRequestException;
import com.deportido.exception.ConflictException;
import com.deportido.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, String>> respuesta(HttpStatus status, String mensaje) {
        Map<String, String> body = new LinkedHashMap<>();
        body.put("mensaje", mensaje);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(NotFoundException ex) {
        return respuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> manejarSolicitudIncorrecta(BadRequestException ex) {
        return respuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> manejarConflicto(ConflictException ex) {
        return respuesta(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonIncorrecto(HttpMessageNotReadableException ex) {
        return respuesta(HttpStatus.BAD_REQUEST,
                "El JSON enviado contiene un valor o formato inválido. Revise fechas, horas y tipos de datos.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarRuntimeException(RuntimeException ex) {
        return respuesta(HttpStatus.BAD_REQUEST,
                ex.getMessage() == null ? "No se pudo procesar la solicitud" : ex.getMessage());
    }
}
