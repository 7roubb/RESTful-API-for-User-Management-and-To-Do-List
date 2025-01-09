package com.cotede.todolist.tasks;

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
        name = "\"tasks\"",
        indexes = {
                @Index(name = "idx_task_title", columnList = "title"),
                @Index(name = "idx_task_user", columnList = "user_username")
        }
)
public class Task extends BaseEntity {
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
