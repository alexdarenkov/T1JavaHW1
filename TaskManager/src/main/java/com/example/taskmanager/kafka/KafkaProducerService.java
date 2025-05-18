package com.example.taskmanager.kafka;

import com.example.taskmanager.dto.TaskStatusChangeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    @Value("${app.kafka.topic.task-status-changed}")
    private String topic;
    private final KafkaTemplate<String, TaskStatusChangeDto> kafkaTemplate;

    public void sendMessage(TaskStatusChangeDto taskStatusChangeDTO) {
        kafkaTemplate.send(topic, taskStatusChangeDTO);
    }
}