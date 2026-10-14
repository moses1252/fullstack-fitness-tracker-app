package com.fitnesstracker.dto;

import com.fitnesstracker.entity.ExerciseUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseRequest(
        @NotBlank(message = "Exercise name is required")
        @Size(max = 100, message = "Name must be 100 characters or less")
        String name,

        @Size(max = 500, message = "Description must be 500 characters or less")
        String description,

        ExerciseUnit unit
) {}