package com.example.taskmanager.kafka;

import com.example.taskmanager.dto.TaskStatusChangeDTO;
import com.example.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final NotificationService notificationService;

    @KafkaListener(topics = "task-status-changed", groupId = "group_id")
    public void consume(TaskStatusChangeDTO event) {
        notificationService.sendStatusChangeNotification(event.getTaskId(), event.getNewStatus());
    }
}