package com.fitnesstracker.repository;

import com.fitnesstracker.entity.WorkoutEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {

    List<WorkoutEntry> findByUserIdAndPerformedOnOrderByIdAsc(Long userId, LocalDate performedOn);

    List<WorkoutEntry> findByUserIdAndPerformedOnBetween(Long userId, LocalDate start, LocalDate end);

    Optional<WorkoutEntry> findByIdAndUserId(Long id, Long userId);

    boolean existsByExerciseId(Long exerciseId);
}