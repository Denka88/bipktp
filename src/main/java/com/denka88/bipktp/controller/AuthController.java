package com.denka88.bipktp.controller;

import com.denka88.bipktp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("user", userService.findByLogin(user.getUsername()));
        
        return "index";
    }
    
}
