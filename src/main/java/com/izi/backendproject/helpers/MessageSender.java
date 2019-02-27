package com.izi.backendproject.helpers;

import com.izi.backendproject.entities.Message;
import com.izi.backendproject.entities.MessageStatus;
import com.izi.backendproject.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.izi.backendproject.conf.Constants.PROVIDER_URL;

@Component
public class MessageSender {

    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public MessageSender(MessageRepository messageRepository, RestTemplate restTemplate) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
    }

    @Async("threadPoolTaskExecutor")
    public void sendMessage(Message message) {
        String url = PROVIDER_URL + message.getMedium() + "?phone=" + message.getDestination() + "&message=" + message.getContent();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode() == HttpStatus.OK) message.setStatus(MessageStatus.SENT);
            else message.setStatus(MessageStatus.FAILED);
        } catch (Exception e) {
            message.setStatus(MessageStatus.FAILED);
        }

        messageRepository.save(message);
    }
}
