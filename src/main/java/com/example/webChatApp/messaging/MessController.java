package com.example.webChatApp.messaging;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MessController {
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/process-message")
    public SysMessage processMessage(@Payload SysMessage sysMessage) throws Exception{
        System.out.println("in process");
        simpMessagingTemplate.convertAndSend("/chat/"+sysMessage.getTo(), sysMessage);
        return sysMessage;
    }
}


