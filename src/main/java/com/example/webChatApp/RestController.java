package com.example.webChatApp;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webChatApp.security.AuthRequest;
import com.example.webChatApp.security.JwtTokenUtil;
import com.example.webChatApp.user.User;
import com.example.webChatApp.user.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
// @org.springframework.web.bind.annotation.RestController
@Controller
@RequestMapping("/test")
public class RestController {
    private UserRepository repository;
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
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            return "register";
        }else{
            try{
                User temp = repository.findByEmail(user.getEmail()).get();
                return "redirect:/test/register?error";
            }catch(NoSuchElementException e){
                String encodedPass = bcrypt.encode(user.getPassword());
                user.setPassword(encodedPass);
                repository.save(user);
                System.out.println(user.toString());
                return "redirect:/test/home";
            }
        }
    }

}
