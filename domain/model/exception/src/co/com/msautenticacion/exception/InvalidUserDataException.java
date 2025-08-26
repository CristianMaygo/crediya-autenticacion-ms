package co.com.msautenticacion.exception;

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super(message);
    }

    public InvalidUserDataException(String field, String reason) {
        super("Inválido " + field + ": " + reason);
    }

    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
