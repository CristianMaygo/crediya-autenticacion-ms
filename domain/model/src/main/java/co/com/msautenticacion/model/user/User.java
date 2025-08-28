package co.com.msautenticacion.model.user;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String directionAddress;
    private BigDecimal salary;
}
