package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.CommitteeDto;
import com.denka88.bipktp.dto.DisciplineDto;
import com.denka88.bipktp.dto.PeriodDto;
import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.model.Discipline;
import com.denka88.bipktp.service.CommitteeService;
import com.denka88.bipktp.service.DisciplineService;
import com.denka88.bipktp.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CommitteeService committeeService;
    private final DisciplineService disciplineService;
    private final PeriodService periodService;
    
    @ModelAttribute("newCommittee")
    public CommitteeDto newCommittee() {
        return new CommitteeDto();
    }
    
    @ModelAttribute("newDiscipline")
    public DisciplineDto newDiscipline() {
        return new DisciplineDto();
    }
    
    @ModelAttribute("newPeriod")
    public PeriodDto newPeriod() {
        return new PeriodDto();
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
    
    @GetMapping("/entities/disciplines")
    public String disciplines(Model model) {
        model.addAttribute("disciplines", disciplineService.findAll());
        return "admin/entities/disciplines";
    }
    
    @GetMapping("/entities/periods")
    public String periods(Model model) {
        model.addAttribute("periods", periodService.findAll());
        return "admin/entities/periods";
    }
    
}
