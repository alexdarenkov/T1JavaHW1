package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Task {
    @Id
    private UUID id;
    private String title;
    private String description;
    @JsonProperty("user_id")
    private UUID userId;
}
