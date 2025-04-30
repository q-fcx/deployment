package com.khaled.bank_management_system.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

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
    @Pattern(regexp = "^05\\d{8}$")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;
}
