package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;

    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private String password;

    private String repeatPassword;
}
