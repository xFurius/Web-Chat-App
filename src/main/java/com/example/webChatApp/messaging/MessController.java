package com.example.webChatApp.messaging;

import java.util.LinkedList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private List<String> onlineUsers;
    private MessageRepository messageRepository;

    public MessController(SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        onlineUsers = new LinkedList<String>();
    }

    @MessageMapping("/process-message")
    public Message processMessage(@Payload Message message) throws Exception{
        System.out.println("in process: "+message.toString());
        System.out.println(message.getReceiver());
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSend("/chat/"+message.getReceiver(), message);
        return message;
    }

    @MessageMapping("/init-users")
    public Message initUsers(@Payload Message message) throws Exception{
        System.out.println("in /init-users: "+message.toString());
        if(!onlineUsers.contains(message.getContent())){
            onlineUsers.add(message.getContent()); // uid:firstName:lastName and split in js
        }
        simpMessagingTemplate.convertAndSend("/sys/user-list", onlineUsers.toArray());
        return message;
    }

    @MessageMapping("/update-users")
    public Message newConnection(@Payload Message message) throws Exception{
        System.out.println("in /update-users: "+message.toString());
        onlineUsers.remove(message.getSender());
        simpMessagingTemplate.convertAndSend("/sys/user-list", onlineUsers.toArray());
        return message;
    }
}