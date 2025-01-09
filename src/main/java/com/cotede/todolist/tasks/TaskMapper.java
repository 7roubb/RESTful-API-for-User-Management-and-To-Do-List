package com.cotede.todolist.tasks;


import java.util.Optional;

public class TaskMapper {

    public static Task toEntityTask(TaskRequestDTO taskRequest) {
        return Optional.ofNullable(taskRequest).map(
                req -> {
                    Task toDo = new Task();
                    toDo.setTitle(taskRequest.getTitle());
                    toDo.setDescription(taskRequest.getDescription());
                    return toDo;
                }
        ).orElse(null);

    }

    public static TaskResponseDTO taskResponse(Task task) {
        return Optional.ofNullable(task).map(
                req -> {
                    TaskResponseDTO taskResponse = new TaskResponseDTO();
                    taskResponse.setId(task.getId());
                    taskResponse.setTitle(task.getTitle());
                    taskResponse.setDescription(task.getDescription());
                    return taskResponse;
                }
        ).orElse(null);
    }
}
