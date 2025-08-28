package co.com.msautenticacion.r2dbc.repository;

import co.com.msautenticacion.r2dbc.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDataRepository extends ReactiveCrudRepository<UserEntity, String> {

    Mono<Boolean> existsByEmail(String email);

}
