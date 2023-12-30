package com.example.actions.Repository;

import com.example.actions.domain.Emotion;
import com.example.actions.domain.ToDo;
import com.example.actions.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmotionRepository extends JpaRepository<Emotion,Long> {
    Long countByToDo(ToDo toDo);

    boolean existsByUserAndToDo(User user, ToDo toDo);

    Optional<Emotion> findByUserAndToDo(User user, ToDo toDo);
}
