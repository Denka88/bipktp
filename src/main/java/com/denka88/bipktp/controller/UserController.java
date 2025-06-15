package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.UserDto;
import com.denka88.bipktp.model.Role;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/entities/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @ModelAttribute("newUser")
    public UserDto newUser() {
        return new UserDto();
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute("newUser") UserDto userDto, @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin) {
        userDto.setAdmin(isAdmin);
        userService.save(userDto);
        return "redirect:/admin/entities/users";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute("updateUser") User user, @RequestParam Long id, 
                         @RequestParam(value = "isAdmin", defaultValue = "false") boolean isAdmin) {
        user.setId(id);
        userService.update(user, isAdmin);
        return "redirect:/admin/entities/users";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/admin/entities/users";
    }
}
