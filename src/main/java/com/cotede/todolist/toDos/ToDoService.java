package com.cotede.todolist.toDos;

import java.util.Map;

public interface ToDoService {
    Map<String, Object> getToDos(int page, int limit, String username);

    ToDo createToDo(ToDoRequestDTO toDoRequestDTO);

    Boolean deleteToDo(Long id);

    ToDoResponseDTO updateToDo(ToDoRequestDTO toDoRequestDTO, Long id);
}