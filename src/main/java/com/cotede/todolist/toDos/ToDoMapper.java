package com.cotede.todolist.toDos;


import java.util.Optional;

public class ToDoMapper {

    public static ToDo toEntityToDo(ToDoRequestDTO toDoRequest) {
        return Optional.ofNullable(toDoRequest).map(
                req -> {
                    ToDo toDo = new ToDo();
                    toDo.setTitle(toDoRequest.getTitle());
                    toDo.setDescription(toDoRequest.getDescription());
                    return toDo;
                }
        ).orElse(null);

    }

    public static ToDoResponseDTO toDoResponse(ToDo toDo) {
        return Optional.ofNullable(toDo).map(
                req -> {
                    ToDoResponseDTO toDoResponse = new ToDoResponseDTO();
                    toDoResponse.setId(toDo.getId());
                    toDoResponse.setTitle(toDo.getTitle());
                    toDoResponse.setDescription(toDo.getDescription());
                    return toDoResponse;
                }
        ).orElse(null);
    }
}
