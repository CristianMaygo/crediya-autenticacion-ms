package co.com.msautenticacion.api.exception;

import co.com.msautenticacion.api.dto.ErrorResponse;
import co.com.msautenticacion.model.exception.InvalidUserDataException;
import co.com.msautenticacion.model.exception.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            ServerWebExchange exchange) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                409,
                "Conflicto",
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(error));
    }

    @ExceptionHandler(InvalidUserDataException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInvalidUserData(
            InvalidUserDataException ex,
            ServerWebExchange exchange) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                400,
                "Datos de Usuario Inválidos",
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationErrors(
            WebExchangeBindException ex,
            ServerWebExchange exchange) {

        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                400,
                "Error de Validación",
                message,
                exchange.getRequest().getURI().getPath()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleGenericException(
            Exception ex,
            ServerWebExchange exchange) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                500,
                "Error Interno del Servidor",
                "Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.",
                exchange.getRequest().getURI().getPath()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error));
    }
}
