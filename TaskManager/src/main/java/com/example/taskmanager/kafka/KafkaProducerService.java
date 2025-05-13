package com.example.taskmanager.kafka;

import com.example.taskmanager.dto.TaskStatusChangeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private static final String TOPIC = "task-status-changed";
    private final KafkaTemplate<String, TaskStatusChangeDTO> kafkaTemplate;

    public void sendMessage(TaskStatusChangeDTO taskStatusChangeDTO) {
        kafkaTemplate.send(TOPIC, taskStatusChangeDTO);
    }
}