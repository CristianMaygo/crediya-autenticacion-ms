package co.com.msautenticacion.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "El nombre es requerido")
    @Size (min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido es requerido")
    @Size (min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;

    @NotNull(message = "La fecha de nacimiento es requerida")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "El número de teléfono es requerido")
    @Pattern(regexp = "^[0-9]{10}$", message = "El número de teléfono debe tener entre 7 y 15 caracteres")
    private String phoneNumber;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La dirección es requerida")
    @Size (min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
    private String directionAddress;

    @NotNull(message = "El salario es requerido")
    @DecimalMin(value = "0.01", message = "El salario debe ser mayor que cero")
    @DecimalMax(value = "15000000", message = "El salario no debe exceder 15,000,000")
    private BigDecimal salary;

}
