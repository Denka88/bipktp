package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.UserDto;
import com.denka88.bipktp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    
    private final UserService userService;
    
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        
        if (!userService.isLoginAvailable(userDto.getLogin())) {
            System.out.println("Логин занят!");
            bindingResult.rejectValue("username", "error.username", "Имя пользователя уже занято!");
            return "auth/register";
        }
        userService.save(userDto);
        return "redirect:/login";
    }
    
    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("user", userService.findByLogin(user.getUsername()));
        
        return "index";
    }
    
}
