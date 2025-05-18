package com.example.taskmanager.dto;

import com.example.taskmanager.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    @JsonProperty("user_id")
    private Long userId;
    private TaskStatus status;
}
