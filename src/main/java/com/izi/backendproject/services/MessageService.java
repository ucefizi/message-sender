package com.izi.backendproject.services;

import com.izi.backendproject.entities.Message;
import com.izi.backendproject.entities.MessageStatus;
import com.izi.backendproject.helpers.MessageSender;
import com.izi.backendproject.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageSender messageSender;

    @Autowired
    public MessageService(MessageRepository messageRepository, MessageSender messageSender) {
        this.messageRepository = messageRepository;
        this.messageSender = messageSender;
    }

    public String sendMessage(Message message) {
        message.setStatus(MessageStatus.PENDING);
        message = messageRepository.save(message);
        messageSender.sendMessage(message);
        return "Message queued";
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
