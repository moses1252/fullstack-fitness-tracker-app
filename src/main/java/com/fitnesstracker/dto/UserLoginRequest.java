package com.fitnesstracker.dto;

import jakarta.validation.constraints.*;

public class UserLoginRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;

    @NotBlank(message = "Password is required")
    public String password;

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserLoginRequest() {}
}