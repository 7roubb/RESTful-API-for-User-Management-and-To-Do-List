package com.cotede.todolist.toDos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    Optional<ToDo> getToDoByTitle(String title);
    Optional<ToDo> getToDoById(Long id);
    Page<ToDo> findAllByUser_Username(String username, Pageable pageable);

}
