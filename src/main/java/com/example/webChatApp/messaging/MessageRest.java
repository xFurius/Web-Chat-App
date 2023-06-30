package com.example.webChatApp.messaging;

import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.webChatApp.security.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class MessageRest {
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/test/messages")
    public String messages(Model model, HttpServletRequest request){
        model.addAttribute("UID", jwtTokenUtil.extractUID(jwtTokenUtil.getToken(request)));
        return "messages";
    }

    @PostMapping("/test/messages")
    public String messagesPOST(@RequestBody Message body, Model model){
        System.out.println(body.getTo());
        // model.addAttribute("data", body);
        return "messages";
    }
}
