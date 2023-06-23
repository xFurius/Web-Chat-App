package com.example.webChatApp.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/test")
public class SecurityController {
    private AuthenticationManager authenticationManager;    
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/signIn")
    public String signInGET(Model model){
        model.addAttribute("request", new AuthRequest());
        return "sign-in";
    }

    @PostMapping("/auth")
    public String signInPOST(@ModelAttribute AuthRequest authRequest, HttpServletResponse response){
         try{
            System.out.println("email: "+authRequest.getEmail()+", password: "+authRequest.getPassword());
            UsernamePasswordAuthenticationToken authInput = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
            Authentication a = authenticationManager.authenticate(authInput);
            System.out.println(a.isAuthenticated());
            String token = jwtTokenUtil.generateToken(authRequest.getEmail());
            Cookie cookie = new Cookie("TOKEN",token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/app/test";
        }catch(AuthenticationException e){
            throw new RuntimeException("Invalid data");
        }
    }
}
