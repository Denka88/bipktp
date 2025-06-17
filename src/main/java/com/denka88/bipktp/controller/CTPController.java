package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.CTPDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/entities/ctps")
@RequiredArgsConstructor
public class CTPController {
    
    private final CTPService ctpService;
    private final CommitteeService committeeService;
    private final DisciplineService disciplineService;
    private final SpecialityService specialityService;
    private final PeriodService periodService;
    
    @ModelAttribute("newCTP")
    public CTPDto newCTP() {
        return new CTPDto();
    }
    
    @GetMapping("/addCtp")
    public String ctps(Model model) {
        model.addAttribute("periods", periodService.findAll());
        model.addAttribute("disciplines", disciplineService.findAll());
        model.addAttribute("committees", committeeService.findAll());
        model.addAttribute("specialities", specialityService.findAll());
        return "/admin/entities/ctps/addCtp";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute("newCTP") CTPDto ctpDto) {
        ctpService.save(ctpDto);
        return "redirect:/admin/entities/ctps";
    }
    
}
