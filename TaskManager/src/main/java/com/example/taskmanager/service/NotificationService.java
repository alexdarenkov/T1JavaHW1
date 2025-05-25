package com.example.taskmanager.service;

import com.example.taskmanager.model.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final JavaMailSender mailSender;

    @Value("${notification.email.to}")
    private String recipientEmail;

    public void sendStatusChangeNotification(Long taskId, TaskStatus status) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Task Status Updated");
        message.setText(String.format(
                """
                TASK ID: %s
                NEW STATUS: %s
                """,
                taskId.toString(),
                status.toString()
        ));

        mailSender.send(message);
    }
}
