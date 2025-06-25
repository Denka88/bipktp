package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.*;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.model.Discipline;
import org.springframework.security.core.userdetails.User;
import com.denka88.bipktp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private final CTPService ctpService;
    
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
    public String entities(@AuthenticationPrincipal User user, Model model) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        return "admin/entities";
    }
    
    @GetMapping("/entities/committees")
    public String committees(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("committees", committeeService.findAll());
        return "admin/entities/committees";
    }
    
    @GetMapping("/entities/disciplines")
    public String disciplines(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("disciplines", disciplineService.findAll());
        return "admin/entities/disciplines";
    }
    
    @GetMapping("/entities/periods")
    public String periods(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("periods", periodService.findAll());
        return "admin/entities/periods";
    }
    
    @GetMapping("/entities/specialities")
    public String specialities(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("specialities", specialityService.findAll());
        return "admin/entities/specialities";
    }
    
    @GetMapping("/entities/teachMethods")
    public String teachMethods(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("teachMethods", teachMethodService.findAll());
        return "admin/entities/teachMethods";
    }
    
    @GetMapping("/entities/lessonTypes")
    public String lessonTypes(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("lessonTypes", lessonTypeService.findAll());
        return "admin/entities/lessonTypes";
    }
    
    @GetMapping("/entities/users")
    public String users(Model model, @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        model.addAttribute("users", userService.findAllWithDisciplines());
        model.addAttribute("disciplines", disciplineService.findAll());
        return "admin/entities/users";
    }
    
    @GetMapping("/entities/ctps")
    public String ctps(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       @AuthenticationPrincipal User user) {
        if(user != null){
            model.addAttribute("user", userService.findByLogin(user.getUsername()));
        }else {
            model.addAttribute("user", null);
        }
        
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(9);

        Page<CTP> ctpPage = ctpService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        
        model.addAttribute("ctpPage", ctpPage);
        int totalPages = ctpPage.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        model.addAttribute("ctps", ctpPage.getContent());
        return "admin/entities/ctps/allCtps";
    }
    
}
