package com.denka88.bipktp.controller;

import com.denka88.bipktp.dto.CTPDto;
import com.denka88.bipktp.dto.ChapterDto;
import com.denka88.bipktp.dto.RecordDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/ctps")
@RequiredArgsConstructor
public class CTPController {
    
    private final CTPService ctpService;
    private final CommitteeService committeeService;
    private final DisciplineService disciplineService;
    private final SpecialityService specialityService;
    private final PeriodService periodService;
    private final ChapterService chapterService;
    private final LessonTypeService lessonTypeService;
    private final TeachMethodService teachMethodService;
    
    @ModelAttribute("newCTP")
    public CTPDto newCTP() {
        return new CTPDto();
    }
    
    @ModelAttribute("newRecord")
    public RecordDto newRecord() {
        return new RecordDto();
    }
    
    @ModelAttribute("newChapter")
    public ChapterDto newChapter() {
        return new ChapterDto();
    }

    @GetMapping
    public String ctps(Model model,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {

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
        return "admin/entities/ctps/ctps";
    }
    
    @GetMapping("/addCtp")
    public String ctps(Model model) {
        model.addAttribute("periods", periodService.findAll());
        model.addAttribute("disciplines", disciplineService.findAll());
        model.addAttribute("committees", committeeService.findAll());
        model.addAttribute("specialities", specialityService.findAll());
        return "/admin/entities/ctps/addCtp";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute("newCTP") CTPDto ctpDto) {
        ctpService.save(ctpDto);
        return "redirect:/ctps";
    }

    @GetMapping("/ctp/{id}")
    public String viewCTP(@PathVariable Long id, Model model) {
        CTP ctp = ctpService.findById(id).orElseThrow(() -> new IllegalArgumentException("КТП не найден"));
        model.addAttribute("ctp", ctp);
        model.addAttribute("lessonTypes", lessonTypeService.findAll());
        model.addAttribute("teachMethods", teachMethodService.findAll());
        
        return "admin/entities/ctps/ctp";
    }
    
    
    
}
