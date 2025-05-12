package com.example.taskmanager.service;

import com.example.taskmanager.aspect.annotation.ExceptionHandling;
import com.example.taskmanager.aspect.annotation.LogTime;
import com.example.taskmanager.aspect.annotation.Loggable;
import com.example.taskmanager.aspect.annotation.ResultHandling;
import com.example.taskmanager.dto.TaskStatusChangeEvent;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private final KafkaTemplate<String, TaskStatusChangeEvent> kafkaTemplate;

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
    public Task getTaskById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public Task updateTask(UUID id, Task newTask) {
        Task oldTask = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
        oldTask.setId(newTask.getId());
        oldTask.setTitle(newTask.getDescription());
        oldTask.setDescription(newTask.getDescription());
        oldTask.setUserId(newTask.getUserId());
        return repository.save(oldTask);
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public void deleteTask(UUID id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
        repository.delete(task);
    }

    @Loggable
    @ExceptionHandling
    @LogTime
    @ResultHandling
    public Task updateTaskStatus(UUID taskId, String newStatus) {
        Task task = repository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        task.setStatus(newStatus);
        repository.save(task);
        kafkaTemplate.send("task-status-changed", new TaskStatusChangeEvent(taskId, newStatus));
        return task;
    }
}