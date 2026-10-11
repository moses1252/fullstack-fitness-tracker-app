package com.fitnesstracker.service;

import com.fitnesstracker.dto.ExerciseRequest;
import com.fitnesstracker.dto.ExerciseResponse;
import com.fitnesstracker.entity.Exercise;
import com.fitnesstracker.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public ExerciseResponse create(Long userId, ExerciseRequest request) {
        Exercise exercise = Exercise.builder()
                .userId(userId)
                .name(request.name())
                .description(request.description())
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
                    exercise.setName(request.name());
                    exercise.setDescription(request.description());
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