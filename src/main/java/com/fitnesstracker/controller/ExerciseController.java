package com.fitnesstracker.controller;

import com.fitnesstracker.dto.ExerciseRequest;
import com.fitnesstracker.dto.ExerciseResponse;
import com.fitnesstracker.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    // TODO: replace X-User-Id header with the user ID from the JWT filter before deployment

    @PostMapping
    public ResponseEntity<ExerciseResponse> create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody ExerciseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(exerciseService.create(userId, request));
    }

    @GetMapping
    public List<ExerciseResponse> list(@RequestHeader("X-User-Id") Long userId) {
        return exerciseService.listActive(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponse> update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("id") Long id,
            @Valid @RequestBody ExerciseRequest request) {
        return exerciseService.update(userId, id, request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> archive(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("id") Long id) {
        return exerciseService.archive(userId, id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}