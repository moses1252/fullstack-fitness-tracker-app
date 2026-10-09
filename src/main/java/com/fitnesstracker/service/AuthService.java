package com.fitnesstracker.service;

import com.fitnesstracker.dto.UserRegisterRequest;
import com.fitnesstracker.dto.UserLoginRequest;
import com.fitnesstracker.dto.AuthResponse;
import com.fitnesstracker.entity.User;
import com.fitnesstracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.invite-codes:ABC123,XYZ789}")
    private String inviteCodes;

    public AuthResponse register(UserRegisterRequest request) {
        if (userRepository.existsByUsername(request.username)) {
            return new AuthResponse(false, "Username already exists", null);
        }

        if (userRepository.existsByEmail(request.email)) {
            return new AuthResponse(false, "Email already exists", null);
        }

        if (!isValidInviteCode(request.inviteCode)) {
            return new AuthResponse(false, "Invalid invite code", null);
        }

        User user = User.builder()
                .username(request.username)
                .email(request.email)
                .password(User.hashPassword(request.password))
                .isActive(true)
                .build();

        userRepository.save(user);
        return new AuthResponse(true, "User registered successfully", user.getId());
    }

    public AuthResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email)
                .orElse(null);

        if (user == null) {
            return new AuthResponse(false, "User not found", null);
        }

        if (!user.checkPassword(request.password)) {
            return new AuthResponse(false, "Invalid password", null);
        }

        return new AuthResponse(true, "Login successful", user.getId());
    }

    private boolean isValidInviteCode(String code) {
        String[] codes = inviteCodes.split(",");
        for (String validCode : codes) {
            if (validCode.trim().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
//package com.fitnesstracker.service;
//
//import com.fitnesstracker.dto.UserRegisterRequest;
//import com.fitnesstracker.dto.UserLoginRequest;
//import com.fitnesstracker.dto.AuthResponse;
//import com.fitnesstracker.entity.User;
//import com.fitnesstracker.repository.UserRepository;
//import com.fitnesstracker.util.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Value("${app.invite-codes:ABC123,XYZ789}")
//    private String inviteCodes;
//
//    public AuthResponse register(UserRegisterRequest request) {
//        // 1. Check if email exists
//        if (userRepository.existsByEmail(request.email)) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        // 2. Validate invite code
//        if (!isValidInviteCode(request.inviteCode)) {
//            throw new RuntimeException("Invalid invite code");
//        }
//
//        // 3. Create user
//        User user = User.builder()
//                .email(request.email)
//                .password(User.hashPassword(request.password))
//                .isActive(true)
//                .build();
//
//        userRepository.save(user);
//
//        // 4. Generate token
//        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
//
//        return new AuthResponse(token, user.getEmail(), user.getId());
//    }
//
//    public AuthResponse login(UserLoginRequest request) {
//        // 1. Find user
//        User user = userRepository.findByEmail(request.email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 2. Check password
//        if (!user.checkPassword(request.password)) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        // 3. Generate token
//        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
//
//        return new AuthResponse(token, user.getEmail(), user.getId());
//    }
//
//    private boolean isValidInviteCode(String code) {
//        String[] codes = inviteCodes.split(",");
//        for (String validCode : codes) {
//            if (validCode.trim().equals(code)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}