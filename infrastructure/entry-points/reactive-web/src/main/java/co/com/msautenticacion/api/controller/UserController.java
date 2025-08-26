package co.com.msautenticacion.api.controller;

import co.com.msautenticacion.api.dto.UserRequest;
import co.com.msautenticacion.api.dto.UserResponse;
import co.com.msautenticacion.model.user.User;
import co.com.msautenticacion.usecase.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        return Mono.just(toUser(userRequest))
                .flatMap(registerUserUseCase::register)
                .map(this::toUserResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    private User toUser(UserRequest request) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setBirthDate(request.getBirthDate());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setDirectionAddress(request.getDirectionAddress());
        user.setSalary(request.getSalary());
        return user;

    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getDirectionAddress(),
                user.getSalary()
        );
    }
}

