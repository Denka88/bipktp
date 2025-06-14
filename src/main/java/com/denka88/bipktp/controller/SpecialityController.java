package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.SpecialityDto;
import com.denka88.bipktp.model.Speciality;
import com.denka88.bipktp.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/entities/specialities")
@RequiredArgsConstructor
public class SpecialityController {
    
    private final SpecialityService specialityService;
    
    @ModelAttribute("newSpeciality")
    public SpecialityDto newSpeciality() {
        return new SpecialityDto();
    }
    
    @PostMapping("/save")
    public String saveSpeciality(@ModelAttribute("newSpeciality") SpecialityDto specialityDto) {
        specialityService.save(specialityDto);
        return "redirect:/admin/entities/specialities";
    }
    
    @PostMapping("/update")
    public String updateSpeciality(@ModelAttribute("updateSpeciality") Speciality speciality, @RequestParam Long id) {
        speciality.setId(id);
        specialityService.update(speciality);
        return "redirect:/admin/entities/specialities";
    }
    
    @PostMapping("/delete")
    public String deleteSpeciality(@RequestParam Long id) {
        specialityService.deleteById(id);
        return "redirect:/admin/entities/specialities";
    }
    
}
