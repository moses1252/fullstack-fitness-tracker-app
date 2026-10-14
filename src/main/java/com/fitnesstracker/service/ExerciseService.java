package com.fitnesstracker.service;

import com.fitnesstracker.dto.ExerciseRequest;
import com.fitnesstracker.dto.ExerciseResponse;
import com.fitnesstracker.entity.Exercise;
import com.fitnesstracker.entity.ExerciseUnit;
import com.fitnesstracker.repository.ExerciseRepository;
import com.fitnesstracker.repository.WorkoutEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutEntryRepository workoutEntryRepository;

    public ExerciseResponse create(Long userId, ExerciseRequest request) {
        if (exerciseRepository.existsByUserIdAndName(userId, request.name())) {
            throw new IllegalArgumentException("You already have an exercise with that name");
        }

        Exercise exercise = Exercise.builder()
                .userId(userId)
                .name(request.name())
                .description(request.description())
                .unit(request.unit() != null ? request.unit() : ExerciseUnit.REPS)
                .build();
        return ExerciseResponse.from(exerciseRepository.save(exercise));
    }

    public List<ExerciseResponse> listActive(Long userId) {
        return exerciseRepository.findByUserIdAndArchivedFalse(userId)
                .stream()
                .map(ExerciseResponse::from)
                .toList();
    }

    public Optional<ExerciseResponse> update(Long userId, Long id, ExerciseRequest request) {
        return exerciseRepository.findByIdAndUserId(id, userId)
                .map(exercise -> {
                    if (exerciseRepository.existsByUserIdAndNameAndIdNot(userId, request.name(), id)) {
                        throw new IllegalArgumentException("You already have an exercise with that name");
                    }
                    // Unit lock: once entries exist, the unit can't change
                    if (request.unit() != null
                            && request.unit() != exercise.getUnit()
                            && workoutEntryRepository.existsByExerciseId(id)) {
                        throw new IllegalArgumentException("Unit can't change after workout entries exist");
                    }
                    exercise.setName(request.name());
                    exercise.setDescription(request.description());
                    if (request.unit() != null) {
                        exercise.setUnit(request.unit());
                    }
                    return ExerciseResponse.from(exerciseRepository.save(exercise));
                });
    }

    public boolean archive(Long userId, Long id) {
        return exerciseRepository.findByIdAndUserId(id, userId)
                .map(exercise -> {
                    exercise.setArchived(true);
                    exerciseRepository.save(exercise);
                    return true;
                })
                .orElse(false);
    }
}