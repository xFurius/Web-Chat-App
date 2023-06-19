package com.example.webChatApp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.webChatApp.user.User;
import com.example.webChatApp.user.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class RestController {
    private UserRepository repository;
    private BCryptPasswordEncoder bcrypt;

    @GetMapping("/")
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
        String pass = user.getPassword();
        user.setPassword("{bcrypt}"+bcrypt.encode(pass));
        repository.save(user);
        return "redirect:/";
    }
}
