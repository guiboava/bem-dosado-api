package io.github.guiboava.bem_dosado.controller.common;

import io.github.guiboava.bem_dosado.controller.dto.ErrorField;
import io.github.guiboava.bem_dosado.controller.dto.ErrorResponse;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.EntityInUseException;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import io.github.guiboava.bem_dosado.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnhandledException(RuntimeException e, HttpServletRequest request) {
        System.out.println(e.getMessage());
        return new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado, entre em contato com a administração.",
                List.of(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                               HttpServletRequest request) {
        List<ErrorField> errorsList = e.getFieldErrors()
                .stream()
                .map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                errorsList,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateRegisterException(DuplicateRegisterException e, HttpServletRequest request) {
        return ErrorResponse.conflict(e.getMessage(),
                request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        return new ErrorResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "O parâmetro '" + e.getName() + "' deve ser um UUID válido.",
                List.of(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EntityInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleEntityInUseException(EntityInUseException e, HttpServletRequest request) {
        return new ErrorResponse(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                List.of(),
                request.getRequestURI());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOperationNotPermittedException(OperationNotPermittedException e, HttpServletRequest request) {
        return ErrorResponse.defaultError(e.getMessage(),
                request.getRequestURI());
    }

}
