package co.com.msautenticacion.r2dbc;

import co.com.msautenticacion.model.user.User;
import co.com.msautenticacion.model.user.gateways.UserRepository;
import co.com.msautenticacion.r2dbc.entity.UserEntity;
import co.com.msautenticacion.r2dbc.repository.UserDataRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final UserDataRepository userDataRepository;

    public UserRepositoryAdapter(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.fromCallable(() -> {
                    return new UserEntity(
                            null,
                            user.getName(),
                            user.getLastName(),
                            user.getBirthDate(),
                            user.getPhoneNumber(),
                            user.getEmail(),
                            user.getDirectionAddress(),
                            user.getSalary()
                    );
                })
                .flatMap(userDataRepository::save)
                .map(this::toUser);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userDataRepository.existsByEmail(email);
    }

    private UserEntity userEntity(User user){
        return new UserEntity(
                null,
                user.getName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getDirectionAddress(),
                user.getSalary()
        );
    }
    private User toUser(UserEntity userEntity){
        User user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setLastName(userEntity.getLastName());
        user.setBirthDate(userEntity.getBirthDate());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setEmail(userEntity.getEmail());
        user.setDirectionAddress(userEntity.getDirectionAddress());
        user.setSalary(userEntity.getSalary());
        return user;
    }
}
