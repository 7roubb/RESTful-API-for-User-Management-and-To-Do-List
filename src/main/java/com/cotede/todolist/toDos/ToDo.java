package com.cotede.todolist.toDos;

import com.cotede.todolist.common.BaseEntity;
import com.cotede.todolist.users.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "\"toDo\"",
        indexes = {
                @Index(name = "idx_toDo_title", columnList = "title"),
                @Index(name = "idx_toDo_user", columnList = "user_username")
        }
)
public class ToDo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName = "username", nullable = false)
    private User user;
}
