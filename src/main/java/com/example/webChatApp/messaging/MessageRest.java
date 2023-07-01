package com.example.webChatApp.messaging;

import org.hibernate.mapping.Collection;
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

    @GetMapping(value = {"/messages", "/messages/{uid}"})
    public String messages(@PathVariable(required = false, name = "uid") String uid, Model model, HttpServletRequest request){
        if(uid != null){
            User user = userRepository.findByUID(uid).get();
            user.getLastName();
            System.out.println(user);
            model.addAttribute("person", user.getFirstName()+" "+user.getLastName());
            model.addAttribute("data", uid);
        }
        
        model.addAttribute("UID", jwtTokenUtil.extractUID(jwtTokenUtil.getToken(request)));
        return "messages";
    }
    
    // @PostMapping("/test/messages")
    // public ModelAndView messagesPOST(@RequestBody Message body){
    //    ModelAndView model = new ModelAndView("conversation");
    //    model.addObject("data", body.getFrom());
    //    return model;
    // }

}
