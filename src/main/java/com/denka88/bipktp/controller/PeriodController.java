package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.PeriodDto;
import com.denka88.bipktp.model.Period;
import com.denka88.bipktp.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/entities/periods")
@RequiredArgsConstructor
public class PeriodController {
    
    private final PeriodService periodService;
    
    @ModelAttribute("newPeriod")
    public PeriodDto newPeriod() {
        return new PeriodDto();
    }
    
    @PostMapping("/save")    
    public String save(@ModelAttribute("newPeriod") PeriodDto periodDto) {
        periodService.save(periodDto);
        return "redirect:/admin/entities/periods";
    }
    
    @PostMapping("/update")
    public String update(@ModelAttribute("updatePeriod") Period period, @RequestParam Long id) {
        period.setId(id);
        periodService.update(period);
        return "redirect:/admin/entities/periods";
    }
    
    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {
        periodService.deleteById(id);
        return "redirect:/admin/entities/periods";
    }
    
}

