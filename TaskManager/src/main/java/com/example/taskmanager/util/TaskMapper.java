package com.example.taskmanager.util;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {
    public TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .userId(task.getUserId())
                .status(task.getStatus())
                .build();
    }

    public Task toEntity(TaskDto taskDto) {
        return Task.builder()
                .id(taskDto.getId())
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .userId(taskDto.getUserId())
                .status(taskDto.getStatus())
                .build();
    }

    public List<TaskDto> toDtoList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toDto)
                .toList();
    }
}
