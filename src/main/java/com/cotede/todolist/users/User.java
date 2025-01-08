package com.cotede.todolist.users;

import com.cotede.todolist.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@Validated
@ToString
@Entity
@Table(
        name = "\"user\"",
        indexes = {
                @Index(name = "idx_user_userName", columnList = "username"),
                @Index(name = "idx_user_email", columnList = "email")
        }
)

public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false,unique = true)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;


}
