package com.cotede.todolist.tasks;

import java.util.Map;

public interface TaskService {
    Map<String, Object> getTasks(int page, int limit);

    Task createTask(TaskRequestDTO taskRequestDTO);

    Boolean deleteTask(Long id);

    TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO, Long id);
}