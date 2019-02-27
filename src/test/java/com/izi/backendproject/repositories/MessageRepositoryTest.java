package com.izi.backendproject.repositories;

import com.izi.backendproject.entities.Message;
import com.izi.backendproject.entities.MessageMedium;
import com.izi.backendproject.entities.MessageStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    private Message message;

    @Before
    public void setup() {
        message = new Message();

        message.setStatus(MessageStatus.SENT);
        message.setMedium(MessageMedium.sms);
        message.setContent("content");
        message.setDestination("671425336");
    }

    @Test
    public void saveTest() {
        Message res = messageRepository.save(message);
        assertNotNull(res.getId());
    }

    @Test
    public void findAllTest() {
        messageRepository.save(message);
        List<Message> res = messageRepository.findAll();
        assertEquals(1, res.size());
    }
}