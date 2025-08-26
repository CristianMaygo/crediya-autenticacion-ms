package co.com.msautenticacion.api.exception;

import co.com.msautenticacion.api.dto.ErrorResponse;
import co.com.msautenticacion.exception.InvalidUserDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import co.com.msautenticacion.exception.EmailAlreadyExistsException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            ServerHttpRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                409,
                "Conflicto",
                ex.getMessage(),
                request.getURI().getPath()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }

    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserData(
            InvalidUserDataException ex,
            ServerHttpRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                400,
                "Datos de Usuario Inválidos",
                ex.getMessage(),
                request.getURI().getPath()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            WebExchangeBindException ex,
            ServerHttpRequest request) {

        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                400,
                "Error de Validación",
                message,
                request.getURI().getPath()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            ServerHttpRequest request) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now().toString(),
                500,
                "Error Interno del Servidor",
                "Ocurrió un error inesperado. Por favor, inténtelo de nuevo más tarde.",
                request.getURI().getPath()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
