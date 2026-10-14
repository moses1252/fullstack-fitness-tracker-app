package com.fitnesstracker.controller;

import com.fitnesstracker.dto.WorkoutEntryRequest;
import com.fitnesstracker.dto.WorkoutEntryResponse;
import com.fitnesstracker.service.WorkoutEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutEntryController {

    @Autowired
    private WorkoutEntryService workoutEntryService;

    // TODO: replace X-User-Id header with the user ID from the JWT filter before deployment

    @PostMapping
    public ResponseEntity<WorkoutEntryResponse> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody WorkoutEntryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(workoutEntryService.create(userId, request));
    }

    @GetMapping
    public List<WorkoutEntryResponse> listByDate(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return workoutEntryService.listByDate(userId, date);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("id") Long id) {
        return workoutEntryService.delete(userId, id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}