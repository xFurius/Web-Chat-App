package com.example.webChatApp.security;

import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.webChatApp.user.User;
import com.example.webChatApp.user.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class SecurityController {
    private AuthenticationManager authenticationManager;    
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository repository;

    @GetMapping("/signIn")
    public String signInGET(Model model){
        model.addAttribute("request", new AuthRequest());
        return "sign-in";
    }

    @PostMapping("/auth")
    public String signInPOST(@ModelAttribute AuthRequest authRequest, HttpServletResponse response){
        try{
            User temp = repository.findByEmail(authRequest.getEmail()).get();
            UsernamePasswordAuthenticationToken authInput = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            Authentication a = authenticationManager.authenticate(authInput);
            String token = jwtTokenUtil.generateToken(authRequest.getEmail());
            Cookie cookie = new Cookie("TOKEN",token);
            cookie.setPath("/");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            return "redirect:/messages";
        }catch(NoSuchElementException | AuthenticationException e){
            return "redirect:/signIn?error";
        }
    }
}