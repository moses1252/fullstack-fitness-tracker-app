package com.fitnesstracker.service;

import com.fitnesstracker.dto.WorkoutEntryRequest;
import com.fitnesstracker.dto.WorkoutEntryResponse;
import com.fitnesstracker.entity.WorkoutEntry;
import com.fitnesstracker.repository.ExerciseRepository;
import com.fitnesstracker.repository.WorkoutEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutEntryService {

    @Autowired
    private WorkoutEntryRepository workoutEntryRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public WorkoutEntryResponse create(Long userId, WorkoutEntryRequest request) {
        // The exercise must belong to the logged-in user and must not be archived
        var exercise = exerciseRepository.findByIdAndUserId(request.exerciseId(), userId)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found"));
        if (exercise.getArchived()) {
            throw new IllegalArgumentException("Exercise is archived");
        }

        WorkoutEntry entry = WorkoutEntry.builder()
                .userId(userId)
                .exerciseId(exercise.getId())
                .performedOn(request.performedOn())
                .setsCount(request.setsCount())
                .repsCount(request.repsCount())
                .weightKg(request.weightKg())
                .notes(request.notes())
                .build();

        return WorkoutEntryResponse.from(workoutEntryRepository.save(entry));
    }

    public List<WorkoutEntryResponse> listByDate(Long userId, LocalDate date) {
        return workoutEntryRepository.findByUserIdAndPerformedOnOrderByIdAsc(userId, date)
                .stream()
                .map(WorkoutEntryResponse::from)
                .toList();
    }

    public boolean delete(Long userId, Long id) {
        return workoutEntryRepository.findByIdAndUserId(id, userId)
                .map(entry -> {
                    workoutEntryRepository.delete(entry);
                    return true;
                })
                .orElse(false);
    }
}