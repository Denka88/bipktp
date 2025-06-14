package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.CommitteeDto;
import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.service.CommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CommitteeService committeeService;
    
    @ModelAttribute("newCommittee")
    public CommitteeDto newCommittee() {
        return new CommitteeDto();
    }

    @GetMapping("/entities")
    public String entities() {
        return "admin/entities";
    }
    
    @GetMapping("/entities/committees")
    public String committees(Model model) {
        model.addAttribute("committees", committeeService.findAll());
        return "admin/entities/committees";
    }
}
