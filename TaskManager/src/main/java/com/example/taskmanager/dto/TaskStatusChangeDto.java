package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatusChangeDto {
    private Long taskId;
    private TaskStatus status;
}