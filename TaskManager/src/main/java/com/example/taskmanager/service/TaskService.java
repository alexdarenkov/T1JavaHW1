package com.example.taskmanager.service;

import com.example.taskmanager.aspect.annotation.ExceptionHandling;
import com.example.taskmanager.aspect.annotation.LogTime;
import com.example.taskmanager.aspect.annotation.Loggable;
import com.example.taskmanager.aspect.annotation.ResultHandling;
import com.example.taskmanager.dto.TaskStatusChangeDto;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.kafka.KafkaProducerService;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.TaskStatus;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final KafkaProducerService kafkaProducerService;

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public Task createTask(Task task) {
        return repository.save(task);
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public Task getTaskById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public Task updateTask(Long id, Task newTask) {
        Task oldTask = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
        TaskStatus oldStatus = oldTask.getStatus();
        oldTask.setId(newTask.getId());
        oldTask.setTitle(newTask.getTitle());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setUserId(newTask.getUserId());

        if (oldStatus != newTask.getStatus()) {
            kafkaProducerService.sendMessage(
                    new TaskStatusChangeDto(id, newTask.getStatus())
            );
            oldTask.setStatus(newTask.getStatus());
        }

        return repository.save(oldTask);
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public void deleteTask(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
        repository.delete(task);
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = repository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (task.getStatus() != newStatus) {
            task.setStatus(newStatus);
            repository.save(task);
            kafkaProducerService.sendMessage(
                    new TaskStatusChangeDto(taskId, newStatus)
            );
        }

        return task;
    }
}