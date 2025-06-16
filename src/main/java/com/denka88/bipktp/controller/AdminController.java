package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.*;
import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.model.Discipline;
import com.denka88.bipktp.service.*;
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
    private final SpecialityService specialityService;
    private final TeachMethodService teachMethodService;
    private final LessonTypeService lessonTypeService;
    private final UserService userService;
    
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
    
    @ModelAttribute("newSpeciality")
    public SpecialityDto newSpeciality() {
        return new SpecialityDto();
    }
    
    @ModelAttribute("newTeachMethod")
    public TeachMethodDto newTeachMethod() {
        return new TeachMethodDto();
    }
    
    @ModelAttribute("newLessonType")
    public LessonTypeDto newLessonType() {
        return new LessonTypeDto();
    }
    
    @ModelAttribute("newUser")
    public UserDto newUser() {
        return new UserDto();
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
    
    @GetMapping("/entities/specialities")
    public String specialities(Model model) {
        model.addAttribute("specialities", specialityService.findAll());
        return "admin/entities/specialities";
    }
    
    @GetMapping("/entities/teachMethods")
    public String teachMethods(Model model) {
        model.addAttribute("teachMethods", teachMethodService.findAll());
        return "admin/entities/teachMethods";
    }
    
    @GetMapping("/entities/lessonTypes")
    public String lessonTypes(Model model) {
        model.addAttribute("lessonTypes", lessonTypeService.findAll());
        return "admin/entities/lessonTypes";
    }
    
    @GetMapping("/entities/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAllWithDisciplines());
        model.addAttribute("disciplines", disciplineService.findAll());
        return "admin/entities/users";
    }
    
}
