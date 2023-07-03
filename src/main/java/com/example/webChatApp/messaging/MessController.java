package com.example.webChatApp.messaging;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.webChatApp.user.User;
import com.example.webChatApp.user.UserRepository;

@Controller
public class MessController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private Map<String, String> onlineUsers;  //change it to map key:uid value:firstName + " " + lastName
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public MessController(SimpMessagingTemplate simpMessagingTemplate, MessageRepository messageRepository, UserRepository userRepository){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        onlineUsers = new HashMap<>();
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
        if(!onlineUsers.containsKey(message.getContent())){
            User user = userRepository.findByUID(message.getContent()).get();
            onlineUsers.put(message.getContent(), user.getFirstName()+" "+user.getLastName());
        }
        simpMessagingTemplate.convertAndSend("/sys/user-list", onlineUsers);
        return message;
    }

    @MessageMapping("/update-users")
    public Message newConnection(@Payload Message message) throws Exception{
        System.out.println("in /update-users: "+message.toString());
        onlineUsers.remove(message.getSender());
        simpMessagingTemplate.convertAndSend("/sys/user-list", onlineUsers);
        return message;
    }
}