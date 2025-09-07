package com.bank.bank.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Firstname cannot be empty")
    private String firstName;

    @NotBlank(message = "Lastname cannot be empty")
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "National ID cannot be empty")
    private String nationalId;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be empty")
    private String emailAddress;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

}
