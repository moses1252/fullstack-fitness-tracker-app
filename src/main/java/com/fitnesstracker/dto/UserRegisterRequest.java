package com.fitnesstracker.dto;

import jakarta.validation.constraints.*;

public class UserRegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    public String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    public String password;

    @NotBlank(message = "Invite code is required")
    public String inviteCode;

    public UserRegisterRequest(String username, String email, String password, String inviteCode) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.inviteCode = inviteCode;
    }

    public UserRegisterRequest() {}
}