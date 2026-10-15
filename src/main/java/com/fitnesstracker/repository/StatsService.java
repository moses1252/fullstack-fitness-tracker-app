package com.fitnesstracker.service;

import com.fitnesstracker.dto.StatsResponse;
import com.fitnesstracker.entity.Exercise;
import com.fitnesstracker.entity.ExerciseUnit;
import com.fitnesstracker.entity.WorkoutEntry;
import com.fitnesstracker.repository.ExerciseRepository;
import com.fitnesstracker.repository.WorkoutEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final WorkoutEntryRepository workoutEntryRepository;
    private final ExerciseRepository exerciseRepository;

    @Transactional(readOnly = true)
    public StatsResponse weeklyStats(Long userId, LocalDate anyDayInWeek, Long exerciseId) {
        // Week runs Monday to Sunday. Any date in the week maps to that week's Monday.
        LocalDate day = (anyDayInWeek != null) ? anyDayInWeek : LocalDate.now();
        LocalDate weekStart = day.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate weekEnd = weekStart.plusDays(6);

        // Map each of this user's exercises to its unit.
        // Archived exercises are included so old history still counts.
        Map<Long, ExerciseUnit> unitByExerciseId = new HashMap<>();
        for (Exercise exercise : exerciseRepository.findByUserId(userId)) {
            unitByExerciseId.put(exercise.getId(), exercise.getUnit());
        }

        // Headline uses REPS. A single exercise uses its own unit.
        ExerciseUnit unit = ExerciseUnit.REPS;
        if (exerciseId != null) {
            unit = unitByExerciseId.get(exerciseId);
            if (unit == null) {
                throw new IllegalArgumentException("Exercise not found");
            }
        }

        List<WorkoutEntry> entries =
                workoutEntryRepository.findByUserIdAndPerformedOnBetween(userId, weekStart, weekEnd);

        long total = 0;
        Set<LocalDate> activeDays = new HashSet<>();

        for (WorkoutEntry entry : entries) {
            boolean counts;
            if (exerciseId != null) {
                counts = exerciseId.equals(entry.getExerciseId());
            } else {
                counts = unitByExerciseId.get(entry.getExerciseId()) == ExerciseUnit.REPS;
            }

            if (!counts) {
                continue;
            }

            total += (long) entry.getSetsCount() * entry.getRepsCount();
            activeDays.add(entry.getPerformedOn());
        }

        int activeDayCount = activeDays.size();
        double perActiveDay = (activeDayCount == 0) ? 0.0 : (double) total / activeDayCount;

        return new StatsResponse(
                weekStart,
                weekEnd,
                exerciseId,
                unit.name(),
                total,
                activeDayCount,
                perActiveDay
        );
    }
}