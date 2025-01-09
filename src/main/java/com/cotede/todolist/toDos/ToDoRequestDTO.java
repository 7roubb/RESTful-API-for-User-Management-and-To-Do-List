package com.cotede.todolist.toDos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoRequestDTO {
    private String title;
    private String description;
    private String username;
}
