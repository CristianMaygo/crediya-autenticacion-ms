package co.com.msautenticacion.usecase;

import co.com.msautenticacion.exception.EmailAlreadyExistsException;
import co.com.msautenticacion.exception.InvalidUserDataException;
import co.com.msautenticacion.model.user.User;
import co.com.msautenticacion.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> register(User user) {
        return validateUser(user)
                .then(checkEmailNotExists(user.getEmail()))
                .then(userRepository.saveUser(user));
    }

    private Mono<Void> validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return Mono.error(new InvalidUserDataException("El nombre es requerido"));
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            return Mono.error(new InvalidUserDataException("El apellido es requerido"));
        }
        if (user.getBirthDate() == null) {
            return Mono.error(new InvalidUserDataException("La fecha de nacimiento es requerida"));
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty()) {
            return Mono.error(new InvalidUserDataException("El número de teléfono es requerido"));
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return Mono.error(new InvalidUserDataException("El email es requerido"));
        }
        if (!isValidEmail(user.getEmail())) {
            return Mono.error(new InvalidUserDataException("El email debe tener un formato válido"));
        }
        if (user.getDirectionAddress() == null || user.getDirectionAddress().isEmpty()) {
            return Mono.error(new InvalidUserDataException("La dirección es requerida"));
        }
        if (user.getSalary() == null || user.getSalary().doubleValue() <= 0) {
            return Mono.error(new InvalidUserDataException("El salario debe ser mayor que cero"));
        }
        if (user.getSalary().doubleValue() > 15000000) {
            return Mono.error(new InvalidUserDataException("El salario no debe exceder 15,000,000"));
        }
        return Mono.empty();
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private Mono<Void> checkEmailNotExists(String email) {
        return userRepository.existsByEmail(email)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new EmailAlreadyExistsException(email));
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
