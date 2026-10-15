package com.fitnesstracker.repository;

import com.fitnesstracker.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByUserIdAndArchivedFalse(Long userId);

    List<Exercise> findByUserId(Long userId);

    Optional<Exercise> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndName(Long userId, String name);

    boolean existsByUserIdAndNameAndIdNot(Long userId, String name, Long id);
}