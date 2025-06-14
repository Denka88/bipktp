package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.TeachMethodDto;
import com.denka88.bipktp.model.TeachMethod;
import com.denka88.bipktp.service.TeachMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/entities/teachMethods")
@RequiredArgsConstructor
public class TeachMethodController {
    
    private final TeachMethodService teachMethodService;
    
    @ModelAttribute("newTeachMethod")
    public TeachMethodDto newTeachMethod() {
        return new TeachMethodDto();
    }
    
    @PostMapping("/save")
    public String teachMethodSave(@ModelAttribute("newTeachMethod") TeachMethodDto teachMethodDto) {
        teachMethodService.save(teachMethodDto);
        return "redirect:/admin/entities/teachMethods";
    }
    
    @PostMapping("/update")
    public String updateTeachMethod(@ModelAttribute("updateTeachMethod") TeachMethod teachMethod, @RequestParam Long id) {
        teachMethod.setId(id);
        teachMethodService.update(teachMethod);
        return "redirect:/admin/entities/teachMethods";
    }
    
    @PostMapping("/delete")
    public String deleteTeachMethod(@RequestParam Long id) {
        teachMethodService.deleteById(id);
        return "redirect:/admin/entities/teachMethods";
    }
    
}
