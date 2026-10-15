package com.fitnesstracker.dto;

import java.time.LocalDate;

public record StatsResponse(
        LocalDate weekStart,
        LocalDate weekEnd,
        Long exerciseId,
        String unit,
        long total,
        int activeDays,
        double perActiveDay
) {}