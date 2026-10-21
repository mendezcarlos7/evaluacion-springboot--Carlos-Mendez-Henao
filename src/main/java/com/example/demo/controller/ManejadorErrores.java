package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class ManejadorErrores {

    // Errores de validación: código 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Los datos enviados no son válidos");

        return ResponseEntity.badRequest().body(Map.of(
                "error", "Error de validación",
                "mensaje", mensaje
        ));
    }

    // JSON incorrecto o incompleto: código 400
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(
            HttpMessageNotReadableException ex) {

        return ResponseEntity.badRequest().body(Map.of(
                "error", "JSON inválido",
                "mensaje", "Verifique que el JSON esté bien escrito y contenga los datos requeridos."
        ));
    }

    // Errores con un código HTTP específico
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> manejarErrorEstado(
            ResponseStatusException ex) {

        HttpStatus estado = HttpStatus.valueOf(
                ex.getStatusCode().value()
        );

        String mensaje = ex.getReason() != null
                ? ex.getReason()
                : "Ocurrió un error en la solicitud.";

        String error = estado == HttpStatus.NOT_FOUND
                ? "Recurso no encontrado"
                : estado == HttpStatus.BAD_REQUEST
                ? "Solicitud incorrecta"
                : "Error en la solicitud";

        return ResponseEntity.status(ex.getStatusCode()).body(Map.of(
                "error", error,
                "mensaje", mensaje
        ));
    }

    // Errores inesperados: código 500
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarErrorInesperado(
            RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Error interno del servidor",
                        "mensaje", "Ocurrió un error inesperado. Intente nuevamente."
                ));
    }
}

