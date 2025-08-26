package co.com.msautenticacion.model.user.gateways;

import co.com.msautenticacion.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono <User> saveUser(User user);
    Mono<Boolean> existsByEmail(String email);
}
