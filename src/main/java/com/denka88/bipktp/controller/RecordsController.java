package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.ChapterDto;
import com.denka88.bipktp.dto.RecordDto;
import com.denka88.bipktp.model.Record;
import com.denka88.bipktp.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    @PostMapping("/delete")
    public String delete(@PathVariable Long id, @RequestParam Long deleteId) {
        recordService.deleteById(deleteId);
        return "redirect:/ctps/ctp/" + id;
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("updateRecord") Record record,
                         @PathVariable Long id,
                         @RequestParam Long updateId,
                         @RequestParam List<Long> teachMethodsIds) {
        record.setId(updateId);
        recordService.update(record, teachMethodsIds);
        return "redirect:/ctps/ctp/" + id;
    }
    
}
