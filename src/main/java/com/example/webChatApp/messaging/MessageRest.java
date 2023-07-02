package com.example.webChatApp.messaging;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.webChatApp.security.JwtTokenUtil;
import com.example.webChatApp.user.User;
import com.example.webChatApp.user.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class MessageRest {
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    @GetMapping(value = {"/messages", "/messages/{receiver}"})
    public String messages(@PathVariable(required = false, name = "receiver") String receiver, Model model, HttpServletRequest request){
        String currentUser = jwtTokenUtil.extractUID(jwtTokenUtil.getToken(request));
        String conversationID = null;
        if(receiver != null){
            int r = Integer.parseInt(receiver);
            int cu = Integer.parseInt(currentUser);
            if(r<cu){
                conversationID = r + "-" +cu;
            }else{
                conversationID = cu + "-" +r;
            }
            User user = userRepository.findByUID(receiver).get();
            user.getLastName();
            System.out.println(user);
            model.addAttribute("person", user.getFirstName()+" "+user.getLastName());
            List<Message> m = messageRepository.findAllByConversationID(conversationID);
            m.stream().forEach(System.out::println);
            model.addAttribute("messages", m);
        }
        
        model.addAttribute("UID", currentUser);
        return "messages";
    }
}
