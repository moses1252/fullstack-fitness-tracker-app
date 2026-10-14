package com.fitnesstracker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record WorkoutEntryRequest(
        @NotNull(message = "Exercise is required")
        Long exerciseId,

        @NotNull(message = "Date is required")
        LocalDate performedOn,

        @NotNull(message = "Sets are required")
        @Min(value = 1, message = "Sets must be greater than 0")
        Integer setsCount,

        @NotNull(message = "Amount is required")
        @Min(value = 1, message = "Amount must be greater than 0")
        Integer repsCount,

        @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
        Double weightKg,

        @Size(max = 500, message = "Notes must be 500 characters or less")
        String notes
) {}