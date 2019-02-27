package com.izi.backendproject.services;

import com.izi.backendproject.entities.Message;
import com.izi.backendproject.helpers.MessageSender;
import com.izi.backendproject.repositories.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock private MessageRepository messageRepository;
    @Mock private MessageSender messageSender;

    @InjectMocks private MessageService messageService;

    private Message message;

    @Before
    public void setup() {
        message = new Message();
        message.setId(1L);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageRepository.findAll()).thenReturn(Arrays.asList(message, message));
    }

    @Test
    public void sendMessage() {
        String result = messageService.sendMessage(message);
        assertEquals("Message queued", result);
    }

    @Test
    public void getAllMessages() {
        List<Message> res = messageService.getAllMessages();
        assertEquals(Arrays.asList(message, message), res);
    }
}
