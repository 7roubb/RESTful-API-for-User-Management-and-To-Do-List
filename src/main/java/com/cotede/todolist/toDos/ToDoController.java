package com.cotede.todolist.toDos;


import com.cotede.todolist.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor

public class ToDoController {
    private final ToDoService toDoService;
    private final MessageSource messageSource;

    @GetMapping
    public ApiResponse<Map<String, Object>> getToDo(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestHeader String username
    ) {
        Map<String, Object> response = toDoService.getToDos(page, limit, username);
        return ApiResponse.success(response,HttpStatus.OK,getMessage("user.get.todos.success",username));
    }

    @PostMapping
    public ApiResponse<ToDoResponseDTO> createToDo(@RequestBody ToDoRequestDTO toDoRequestDTO) {
        ToDo toDo = toDoService.createToDo(toDoRequestDTO);
        return ApiResponse.success(ToDoMapper.toDoResponse(toDo),HttpStatus.OK,getMessage("todo.create.success",toDo.getId().toString()));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteToDo(@PathVariable Long id) {
        Boolean flag = toDoService.deleteToDo(id);
        return ApiResponse.success(flag, HttpStatus.OK,getMessage("todo.delete.success", id.toString()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoResponseDTO> updateToDo(@RequestBody ToDoRequestDTO toDoRequestDTO,@PathVariable Long id) {
        ToDoResponseDTO updatedToDo = toDoService.updateToDo(toDoRequestDTO,id);
        return ResponseEntity.ok(updatedToDo);
    }


    public String getMessage(String code , String message) {
        return messageSource.getMessage(code, new Object[]{message}, LocaleContextHolder.getLocale());
    }
}