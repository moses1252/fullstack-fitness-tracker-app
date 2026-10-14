package com.fitnesstracker.dto;

import com.fitnesstracker.entity.WorkoutEntry;

import java.time.LocalDate;

public record WorkoutEntryResponse(
        Long id,
        Long exerciseId,
        LocalDate performedOn,
        Integer setsCount,
        Integer repsCount,
        Double weightKg,
        String notes
) {

    public static WorkoutEntryResponse from(WorkoutEntry entry) {
        return new WorkoutEntryResponse(
                entry.getId(),
                entry.getExerciseId(),
                entry.getPerformedOn(),
                entry.getSetsCount(),
                entry.getRepsCount(),
                entry.getWeightKg(),
                entry.getNotes()
        );
    }
}