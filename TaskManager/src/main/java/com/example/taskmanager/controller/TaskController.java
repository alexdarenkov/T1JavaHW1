package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.dto.TaskStatusChangeDto;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.util.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {
    private final TaskService service;
    private final TaskMapper taskMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto create(@RequestBody TaskDto taskDto) {
        return taskMapper.toDto(
                service.createTask(taskMapper.toEntity(taskDto))
        );
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        return taskMapper.toDto(
                service.getTaskById(id)
        );
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        return taskMapper.toDto(
                service.updateTask(id, taskMapper.toEntity(taskDto))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteTask(id);
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return taskMapper.toDtoList(
                service.getAllTasks()
        );
    }

    @PatchMapping("/status")
    public TaskDto updateStatus(@RequestBody TaskStatusChangeDto taskStatusChangeDto) {
        return taskMapper.toDto(
                service.updateTaskStatus(
                        taskStatusChangeDto.getTaskId(),
                        taskStatusChangeDto.getStatus()
                )
        );
    }
}