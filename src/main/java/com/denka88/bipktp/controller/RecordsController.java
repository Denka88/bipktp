package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.ChapterDto;
import com.denka88.bipktp.dto.RecordDto;
import com.denka88.bipktp.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ctps/ctp/{id}")
@RequiredArgsConstructor
public class RecordsController {
    
    private final RecordService recordService;

    @ModelAttribute("newRecord")
    public RecordDto newRecord() {
        return new RecordDto();
    }

    @ModelAttribute("newChapter")
    public ChapterDto newChapter() {
        return new ChapterDto();
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute("newRecord") RecordDto recordDto, @PathVariable Long id) {
        recordService.save(recordDto);
        return "redirect:/ctps/ctp/" + id;
    }
    
}
