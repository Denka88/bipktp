package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.CommitteeDto;
import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.service.CommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/entities/committees")
@RequiredArgsConstructor
public class CommitteeController {

    private final CommitteeService committeeService;

    @ModelAttribute("newCommittee")
    public CommitteeDto newCommittee() {
        return new CommitteeDto();
    }

    @PostMapping("/save")
    public String committeesSave(@ModelAttribute("newCommittee") CommitteeDto committeeDto) {
        committeeService.save(committeeDto);
        return "redirect:/admin/entities/committees";
    }

    @PostMapping("/update")
    public String updateCommittee(@ModelAttribute("updateCommittee") Committee committee, @RequestParam Long id) {
        committee.setId(id);
        committeeService.update(committee);
        return "redirect:/admin/entities/committees";
    }

    @PostMapping("/delete")
    public String deleteCommittee(@RequestParam Long id) {
        committeeService.deleteById(id);
        return "redirect:/admin/entities/committees";
    }

}
