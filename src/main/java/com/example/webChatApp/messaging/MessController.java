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

    public MessController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
        onlineUsers = new LinkedList<String>(); //change this to include uid and first and last name
    }

    @MessageMapping("/process-message")
    public Message processMessage(@Payload Message message) throws Exception{
        System.out.println("in process: "+message.toString());
        // simpMessagingTemplate.convertAndSend("/chat/"+message.getTo(), message);
        return message;
    }

    @MessageMapping("/init-users")
    public Message initUsers(@Payload Message message) throws Exception{
        System.out.println("in /init-users: "+message.toString());
        onlineUsers.add(message.getContent());
        simpMessagingTemplate.convertAndSend("/sys/user-list", onlineUsers.toArray());
        return message;
    }

    @MessageMapping("/update-users")
    public Message newConnection(@Payload Message message) throws Exception{
        System.out.println("in /update-users: "+message.toString());
        onlineUsers.remove(message.getFrom());
        simpMessagingTemplate.convertAndSend("/sys/user-list", onlineUsers.toArray());
        return message;
    }
}