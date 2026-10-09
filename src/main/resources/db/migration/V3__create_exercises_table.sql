CREATE TABLE exercises (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT fk_exercises_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_exercises_user_id ON exercises(user_id);