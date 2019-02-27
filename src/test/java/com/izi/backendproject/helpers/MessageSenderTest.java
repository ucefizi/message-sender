package com.izi.backendproject.helpers;

import com.izi.backendproject.entities.Message;
import com.izi.backendproject.entities.MessageMedium;
import com.izi.backendproject.entities.MessageStatus;
import com.izi.backendproject.repositories.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.izi.backendproject.conf.Constants.PROVIDER_URL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageSenderTest {
    @Mock private MessageRepository messageRepository;
    @Mock private RestTemplate restTemplate;

    @InjectMocks private MessageSender messageSender;

    private ResponseEntity response;


    @Test
    public void sendMessageSuccess() {
        response = new ResponseEntity<String>(HttpStatus.OK);
        when(restTemplate.getForEntity(PROVIDER_URL + "sms?phone=666666666&message=msg", String.class)).thenReturn(response);
        Message msg = new Message();
        msg.setMedium(MessageMedium.sms);
        msg.setDestination("666666666");
        msg.setContent("msg");
        messageSender.sendMessage(msg);
        assertEquals(MessageStatus.SENT, msg.getStatus());
    }

    @Test
    public void sendMessageFail() {
        response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(PROVIDER_URL + "sms?phone=666666666&message=msg", String.class)).thenReturn(response);
        Message msg = new Message();
        msg.setMedium(MessageMedium.sms);
        msg.setDestination("666666666");
        msg.setContent("msg");
        messageSender.sendMessage(msg);
        assertEquals(MessageStatus.FAILED, msg.getStatus());
    }
}