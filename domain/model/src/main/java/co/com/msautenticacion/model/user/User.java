package co.com.msautenticacion.model.user;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String directionAddress;
    private BigDecimal salary;
}
