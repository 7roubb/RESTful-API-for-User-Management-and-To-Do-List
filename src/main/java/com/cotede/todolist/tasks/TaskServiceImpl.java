package com.cotede.todolist.tasks;

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
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Map<String, Object> getTasks(int page, int limit, String username) {
        Pageable pageable = PageRequest.of(page - 1 , limit);
        Page<Task> tasks = taskRepository.findAllByUser_Username(username, pageable);

        List<TaskResponseDTO> taskResponseList = tasks.getContent()
                .stream()
                .map(TaskMapper::taskResponse)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", taskResponseList);
        response.put("page", page);
        response.put("limit", limit);
        response.put("total", tasks.getTotalElements());

        return response;
    }


    @Override
    public Task createTask(TaskRequestDTO taskRequestDTO) {
        Task task = TaskMapper.toEntityTask(taskRequestDTO);
        task.setCreatedAt(LocalDateTime.now());
        User user = userRepository.findByUsername(taskRequestDTO.getUsername()).orElseThrow(
                ()-> new CustomExceptions.UserNotFound(taskRequestDTO.getUsername())
        );
        task.setUser(user);
        task = taskRepository.save(task);
        return task;
    }

    @Override
    public Boolean deleteTask(Long id) {
        Task task = taskRepository.getTaskById(id)
                .orElseThrow(() -> new CustomExceptions.TaskNotFound(id.toString()));
        taskRepository.delete(task);
        return true;
    }

    @Override
    public TaskResponseDTO updateTask(TaskRequestDTO taskRequestDTO, Long id) {
        Task existingTask = taskRepository.getTaskById(id)
                .orElseThrow(() -> new CustomExceptions.TaskNotFound(id.toString()));
        existingTask.setDescription(taskRequestDTO.getDescription());
        existingTask.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.save(existingTask);
        return TaskMapper.taskResponse(updatedTask);
    }
}
