package io.github.guiboava.bem_dosado.controller.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String message,
        List<ErrorField> errors,
        String path
) {

    // Erro padrão 400
    public static ErrorResponse defaultError(String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message,
                List.of(),
                path
        );
    }

    // Erro de conflito 409
    public static ErrorResponse conflict(String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                message,
                List.of(),
                path
        );
    }

    // Criação manual de ErrorResponse genérica, caso queira
    public static ErrorResponse of(int status, String message, List<ErrorField> errors, String path) {
        return new ErrorResponse(LocalDateTime.now(), status, message, errors, path);
    }
}