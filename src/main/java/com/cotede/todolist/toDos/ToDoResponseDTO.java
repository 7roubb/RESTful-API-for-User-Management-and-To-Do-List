package com.cotede.todolist.toDos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoResponseDTO {
    private Long id;
    private String title;
    private String description;
}
