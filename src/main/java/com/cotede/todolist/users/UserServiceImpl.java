package com.cotede.todolist.users;

import com.cotede.todolist.exceptions.CustomExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponseDTO getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomExceptions.UserNotFound(username));
        return UserMapper.toUserResponse(user);
    }


    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        userRepository.findByUsername(userRequestDTO.getUsername())
                .ifPresent(user -> {
                    throw new CustomExceptions.UserAlreadyExistsException(userRequestDTO.getUsername());
                });
        userRepository.findByEmail(userRequestDTO.getEmail())
                .ifPresent(user -> {
                    throw new CustomExceptions.EmailAlreadyExistsException(userRequestDTO.getEmail());
                });

        User user = UserMapper.toUser(userRequestDTO);
        user.setPassword(user.getPassword());
        User savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }


    @Override
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO) {
        return userRepository.findById(userRequestDTO.getId())
                .map(existingUser -> {
                    Optional.ofNullable(userRequestDTO.getUsername()).ifPresent(existingUser::setUsername);
                    Optional.ofNullable(userRequestDTO.getPassword()).ifPresent(existingUser::setPassword);
                    Optional.ofNullable(userRequestDTO.getEmail()).ifPresent(existingUser::setEmail);
                    existingUser.setUpdatedAt(LocalDateTime.now());
                    userRepository.save(existingUser);
                    return UserMapper.toUserResponse(existingUser);
                })
                .orElseThrow(() -> new CustomExceptions.UserNotFound(userRequestDTO.getId().toString()));
    }


    @Override
    @Transactional
    public Boolean deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomExceptions.UserNotFound(username));
        userRepository.delete(user);
        return true;
    }
}
