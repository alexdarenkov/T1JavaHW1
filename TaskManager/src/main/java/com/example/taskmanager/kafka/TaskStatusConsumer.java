package com.example.taskmanager.kafka;

import com.example.taskmanager.dto.TaskStatusChangeEvent;
import com.example.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskStatusConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "task-status-changed")
    public void handleTaskStatusChange(TaskStatusChangeEvent event) {
        notificationService.sendStatusChangeNotification(event.getTaskId(), event.getNewStatus());
    }
}