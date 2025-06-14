package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.LessonTypeDto;
import com.denka88.bipktp.model.LessonType;
import com.denka88.bipktp.service.LessonTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/entities/lessonTypes")
@RequiredArgsConstructor
public class LessonTypeController {
    
    private final LessonTypeService lessonTypeService;
    
    @ModelAttribute("newLessonType")
    public LessonTypeDto newLessonType() {
        return new LessonTypeDto();
    }
    
    @PostMapping("/save")
    public String lessonTypeSave(@ModelAttribute("newLessonType") LessonTypeDto lessonTypeDto) {
        lessonTypeService.save(lessonTypeDto);
        return "redirect:/admin/entities/lessonTypes";
    }
    
    @PostMapping("/update")
    public String updateLessonType(@ModelAttribute("updateLessonType") LessonType lessonType, @RequestParam Long id) {
        lessonType.setId(id);
        lessonTypeService.update(lessonType);
        return "redirect:/admin/entities/lessonTypes";
    }
    
    @PostMapping("/delete")
    public String deleteLessonType(@RequestParam Long id) {
        lessonTypeService.deleteById(id);
        return "redirect:/admin/entities/lessonTypes";
    }
}
