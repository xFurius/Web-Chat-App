package com.example.webChatApp.messaging;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private MessageRepository messageRepository;

    public MessController(SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/process-message")
    public Message processMessage(@Payload Message message) throws Exception{
        System.out.println("in process: "+message.toString());
        System.out.println(message.getReceiver());
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/chat/"+message.getReceiver(), message);
        return message;
    }
}