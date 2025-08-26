package co.com.msautenticacion.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String directionAddress;
    private BigDecimal salary;
}
