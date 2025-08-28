package co.com.msautenticacion.model.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("El email ya está registrado");
    }

    public EmailAlreadyExistsException(String email) {
        super("El email " + email + " ya está registrado");
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
