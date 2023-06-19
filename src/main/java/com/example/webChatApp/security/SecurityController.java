package com.example.webChatApp.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/signIn")
    public String signIn(){
        return "sign-in";
    }
}
