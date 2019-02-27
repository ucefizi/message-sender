package com.izi.backendproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izi.backendproject.entities.Message;
import com.izi.backendproject.entities.MessageStatus;
import com.izi.backendproject.services.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    private Message msg;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(messageController).build();
        msg = new Message();
        msg.setStatus(MessageStatus.PENDING);
        when(messageService.sendMessage(msg)).thenReturn("Message queued");
        when(messageService.getAllMessages()).thenReturn(Arrays.asList(msg, msg));
    }

    @Test
    public void sendMessage() throws Exception {
        mockMvc.perform(
                post("/message/send")
                        .content(mapper.writeValueAsBytes(msg))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Message queued")));
    }

    @Test
    public void getAllMessages() throws Exception {
        mockMvc.perform(
                get("/message/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is("PENDING")));
    }
}