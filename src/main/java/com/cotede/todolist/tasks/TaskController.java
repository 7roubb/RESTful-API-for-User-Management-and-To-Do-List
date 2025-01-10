package com.cotede.todolist.tasks;


import com.cotede.todolist.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor

public class TaskController {
    private final TaskService taskService;
    private final MessageSource messageSource;

    @GetMapping
    public ApiResponse<Map<String, Object>> getTasks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        Map<String, Object> response = taskService.getTasks(page, limit);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ApiResponse.success(response,HttpStatus.OK,getMessage("user.get.tasks.success",username));
    }

    @PostMapping
    public ApiResponse<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        Task task = taskService.createTask(taskRequestDTO);
        return ApiResponse.success(TaskMapper.taskResponse(task),HttpStatus.OK,getMessage("task.create.success",task.getId().toString()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteTask(@PathVariable Long id) {
        Boolean flag = taskService.deleteTask(id);
        return ApiResponse.success(flag, HttpStatus.OK,getMessage("task.delete.success", id.toString()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@RequestBody TaskRequestDTO taskRequestDTO, @PathVariable Long id) {
        TaskResponseDTO updatedTask = taskService.updateTask(taskRequestDTO,id);
        return ResponseEntity.ok(updatedTask);
    }


    public String getMessage(String code , String message) {
        return messageSource.getMessage(code, new Object[]{message}, LocaleContextHolder.getLocale());
    }
}