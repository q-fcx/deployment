package com.khaled.bank_management_system.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    @NotEmpty
    @Size(min = 4, max = 10)
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;

    @NotEmpty
    @Size(min = 6, message = "size must be at least 6 characters")
    @Column(columnDefinition = "varchar(100) not null")
    private String password;

    @NotEmpty
    @Size(min = 2, max = 20)
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String position;

    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "double not null")
    private Double salary;
}
