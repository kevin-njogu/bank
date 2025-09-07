package com.bank.bank.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String nationalId;
    private String emailAddress;
    private String phoneNumber;
}
