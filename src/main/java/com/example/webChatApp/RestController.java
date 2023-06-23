package com.example.webChatApp;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webChatApp.security.AuthRequest;
import com.example.webChatApp.security.JwtTokenUtil;
import com.example.webChatApp.user.User;
import com.example.webChatApp.user.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
// @org.springframework.web.bind.annotation.RestController
@Controller
@RequestMapping("/test")
public class RestController {
    private UserRepository repository;
    private JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bcrypt;

    @GetMapping("/home")
    public String home(Model model){
        return "test";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        String encodedPass = bcrypt.encode(user.getPassword());
        user.setPassword(encodedPass);
        repository.save(user);
        System.out.println(user.toString());
        return "redirect:/test/home";
    }

}
