ALTER TABLE exercises
    ADD COLUMN unit VARCHAR(10) NOT NULL DEFAULT 'REPS',
    ADD CONSTRAINT chk_exercises_unit CHECK (unit IN ('REPS', 'SECONDS'));

ALTER TABLE exercises
    ADD CONSTRAINT uq_exercises_user_name UNIQUE (user_id, name);