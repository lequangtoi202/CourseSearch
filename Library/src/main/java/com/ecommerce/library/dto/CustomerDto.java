package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String fullName;
    private boolean gender;
    private LocalDate dateOfBirth;
    private String address;
    private String email;
    private String phone;
}
