package com.cotede.todolist.tasks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> getTaskByTitle(String title);
    Optional<Task> getTaskById(Long id);
    Page<Task> findAllByUser_Username(String username, Pageable pageable);

}
