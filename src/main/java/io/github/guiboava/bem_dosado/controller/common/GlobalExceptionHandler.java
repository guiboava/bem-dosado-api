package io.github.guiboava.bem_dosado.controller.common;

import io.github.guiboava.bem_dosado.controller.dto.ErrorField;
import io.github.guiboava.bem_dosado.controller.dto.ErrorResponse;
import io.github.guiboava.bem_dosado.exception.DuplicateRegisterException;
import io.github.guiboava.bem_dosado.exception.OperationNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnhandledException(RuntimeException e) {
        System.out.println(e.getMessage());
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado, entre em contato com a administração.",
                List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErrorField> errorsList = fieldErrors.stream().map(fe -> new ErrorField(fe.getField(), fe.getDefaultMessage())).collect(Collectors.toList());
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", errorsList);
    }


    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicateRegisterException(DuplicateRegisterException e) {
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "UUID inválido.",List.of());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOperationNotPermittedException(OperationNotPermittedException e) {
        return ErrorResponse.defaultError(e.getMessage());
    }

}
