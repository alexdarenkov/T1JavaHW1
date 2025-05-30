package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private Long id;
    private String title;
    private String description;
    @JsonProperty("user_id")
    private Long userId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}
