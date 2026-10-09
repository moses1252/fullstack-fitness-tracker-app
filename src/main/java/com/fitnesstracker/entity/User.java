package com.fitnesstracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "weight_kg")
    private Double weightKg;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "target_weight_kg")
    private Double targetWeightKg;

    @Column(name = "daily_calorie_goal")
    private Integer dailyCalorieGoal;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Gender {
        MALE, FEMALE, OTHER
    }
    public static String hashPassword(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }
//    public static String hashPassword(String password) {
//        return password; // TODO: add proper hashing later
//    }

    public boolean checkPassword(String rawPassword) {
        return ENCODER.matches(rawPassword, this.password);
    }
//    public boolean checkPassword(String rawPassword) {
//        return this.password.equals(rawPassword); // TODO: add proper comparison later
//    }
}