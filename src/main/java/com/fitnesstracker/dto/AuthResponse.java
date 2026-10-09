package com.fitnesstracker.dto;

public record AuthResponse(boolean success, String message, Long userId, String token) {}


//package com.fitnesstracker.dto;
//
//public class AuthResponse {
//    public boolean success;
//    public String message;
//    public Long userId;
//
//    public AuthResponse(boolean success, String message, Long userId) {
//        this.success = success;
//        this.message = message;
//        this.userId = userId;
//    }
//}




//package com.fitnesstracker.dto;
//
//public class AuthResponse {
//    public String token;
//    public String email;
//    public Long userId;
//
//    public AuthResponse(String token, String email, Long userId) {
//        this.token = token;
//        this.email = email;
//        this.userId = userId;
//    }
//}