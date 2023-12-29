package com.example.actions.Repository;

import com.example.actions.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  ToDoRepository extends JpaRepository<ToDo,Long> {
}
