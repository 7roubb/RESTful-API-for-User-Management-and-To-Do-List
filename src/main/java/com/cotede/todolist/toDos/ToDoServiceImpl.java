package com.cotede.todolist.toDos;

import com.cotede.todolist.exceptions.CustomExceptions;
import com.cotede.todolist.users.User;
import com.cotede.todolist.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> getToDos(int page, int limit, String username) {
        Pageable pageable = PageRequest.of(page - 1 , limit);
        Page<ToDo> toDos = toDoRepository.findAllByUser_Username(username, pageable);

        List<ToDoResponseDTO> toDoResponseList = toDos.getContent()
                .stream()
                .map(ToDoMapper::toDoResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", toDoResponseList);
        response.put("page", page);
        response.put("limit", limit);
        response.put("total", toDos.getTotalElements());

        return response;
    }


    @Override
    public ToDo createToDo(ToDoRequestDTO toDoRequestDTO) {
        ToDo toDo = ToDoMapper.toEntityToDo(toDoRequestDTO);
        toDo.setCreatedAt(LocalDateTime.now());
        User user = userRepository.findByUsername(toDoRequestDTO.getUsername()).orElseThrow(
                ()-> new CustomExceptions.UserNotFound(toDoRequestDTO.getUsername())
        );
        toDo.setUser(user);
        toDo = toDoRepository.save(toDo);
        return toDo;
    }

    @Override
    public Boolean deleteToDo(Long id) {
        ToDo toDo = toDoRepository.getToDoById(id)
                .orElseThrow(() -> new CustomExceptions.ToDoNotFound(id.toString()));
        toDoRepository.delete(toDo);
        return true;
    }

    @Override
    public ToDoResponseDTO updateToDo(ToDoRequestDTO toDoRequestDTO, Long id) {
        ToDo existingToDo = toDoRepository.getToDoById(id)
                .orElseThrow(() -> new CustomExceptions.ToDoNotFound(id.toString()));
        existingToDo.setDescription(toDoRequestDTO.getDescription());
        existingToDo.setUpdatedAt(LocalDateTime.now());
        ToDo updatedToDo = toDoRepository.save(existingToDo);
        return ToDoMapper.toDoResponse(updatedToDo);
    }
}
