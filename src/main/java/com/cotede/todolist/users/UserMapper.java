package com.cotede.todolist.users;

import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserMapper {
    public static User toUser(UserRequestDTO userRequest) {
        return  Optional.ofNullable(userRequest).map(
                req ->{
                    User user = new User();
                    user.setUsername(userRequest.getUsername());
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());
                    user.setFullName(userRequest.getFullName());

                    user.setCreatedBy(user.getUsername());
                    return user;
                }
        ).orElse(null);


    }
    public static UserResponseDTO toUserResponse(User user) {
        return Optional.ofNullable(user).map(u ->{
            UserResponseDTO userResponse = new UserResponseDTO();
            userResponse.setId(u.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setFullName(user.getFullName());
            return userResponse;
        }).orElse(null);
    }
}
