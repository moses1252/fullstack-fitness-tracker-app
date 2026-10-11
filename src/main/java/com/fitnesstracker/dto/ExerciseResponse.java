package com.fitnesstracker.dto;

import com.fitnesstracker.entity.Exercise;

public record ExerciseResponse(Long id, String name, String description, Boolean archived) {

    public static ExerciseResponse from(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getArchived()
        );
    }
}