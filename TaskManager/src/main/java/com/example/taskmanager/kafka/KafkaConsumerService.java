package com.example.taskmanager.kafka;

import com.example.taskmanager.dto.TaskStatusChangeDto;
import com.example.taskmanager.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${app.kafka.topic.task-status-changed}",
            groupId = "${app.kafka.group-id}"
    )
    public void consume(TaskStatusChangeDto event, Acknowledgment ack) {
        notificationService.sendStatusChangeNotification(event.getTaskId(), event.getStatus());
        ack.acknowledge();
    }
}