package com.example.webChatApp.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/app")
public class UserController {
    private UserRepository repository;

    @GetMapping("/test")
    public String test(Model model){
        List<User> users = repository.findAll();
        model.addAttribute("users", users);
        return "test";
    }

    @GetMapping("/insertJohn")
    public String insertJohn(){
        User user = new User("Jon", "snow", "{noop}test", "johnsnow@gmail.com");
        repository.save(user);
        return "test";
    }
}
