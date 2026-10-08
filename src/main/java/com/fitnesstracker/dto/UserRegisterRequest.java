package com.fitnesstracker.dto;

import jakarta.validation.constraints.*;

public class UserRegisterRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    public String password;

    @NotBlank(message = "Invite code is required")
    public String inviteCode;

    public UserRegisterRequest(String email, String password, String inviteCode) {
        this.email = email;
        this.password = password;
        this.inviteCode = inviteCode;
    }

    public UserRegisterRequest() {}
}