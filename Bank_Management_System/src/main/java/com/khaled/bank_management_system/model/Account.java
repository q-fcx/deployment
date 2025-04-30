package com.khaled.bank_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String accountNumber;

    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "double not null")
    private Double balance;

    @AssertFalse
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @JsonIgnore
    private Customer customer;
}