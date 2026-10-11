CREATE TABLE workout_entries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    performed_on DATE NOT NULL,
    sets_count INT NOT NULL,
    reps_count INT NOT NULL,
    weight_kg DOUBLE,
    notes VARCHAR(500),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_workout_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_workout_exercise FOREIGN KEY (exercise_id) REFERENCES exercises(id)
);

CREATE INDEX idx_workout_user_date ON workout_entries(user_id, performed_on);