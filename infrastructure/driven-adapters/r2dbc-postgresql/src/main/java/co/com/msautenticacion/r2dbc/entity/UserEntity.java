package co.com.msautenticacion.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("last_name")
    private String lastName;

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("phone_number")
    private String phoneNumber;

    @Column("email")
    private String email;

    @Column("direction_address")
    private String directionAddress;

    @Column("salary")
    private BigDecimal salary;
}
