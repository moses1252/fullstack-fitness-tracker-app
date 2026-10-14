package com.fitnesstracker.dto;

import com.fitnesstracker.entity.Exercise;
import com.fitnesstracker.entity.ExerciseUnit;

public record ExerciseResponse(Long id, String name, String description, ExerciseUnit unit, Boolean archived) {

    public static ExerciseResponse from(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getUnit(),
                exercise.getArchived()
        );
    }
}