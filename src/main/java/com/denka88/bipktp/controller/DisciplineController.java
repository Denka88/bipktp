package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.DisciplineDto;
import com.denka88.bipktp.model.Discipline;
import com.denka88.bipktp.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/entities/disciplines")
@RequiredArgsConstructor
public class DisciplineController {
    
    private final DisciplineService disciplineService;
    
    @ModelAttribute("newDiscipline")
    public DisciplineDto newDiscipline() {
        return new DisciplineDto();
    }
    
    @PostMapping("/save")
    public String disciplineSave(@ModelAttribute("newDiscipline") DisciplineDto disciplineDto){
        disciplineService.save(disciplineDto);
        return "redirect:/admin/entities/disciplines";
    }
    
    @PostMapping("/update")
    public String updateDiscipline(@ModelAttribute("updateDiscipline")Discipline discipline, @RequestParam Long id){
        discipline.setId(id);
        disciplineService.update(discipline);
        return "redirect:/admin/entities/disciplines";
    }
    
    @PostMapping("/delete")
    public String deleteDiscipline(@RequestParam Long id){
        disciplineService.deleteById(id);
        return "redirect:/admin/entities/disciplines";
    }
}
