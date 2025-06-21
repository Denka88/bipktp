package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.ChapterDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Chapter;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.repo.ChapterRepo;
import com.denka88.bipktp.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    
    private final ChapterRepo chapterRepo;
    private final CTPRepo ctpRepo;
    
    @Override
    public List<Chapter> findAll() {
        return chapterRepo.findAll();
    }

    @Override
    public Optional<Chapter> findById(Long id) {
        return chapterRepo.findById(id);
    }

    @Override
    public Chapter save(ChapterDto chapterDto) {
        Chapter chapter = new Chapter();
        chapter.setTitle(chapterDto.getTitle());

        CTP ctp = ctpRepo.findById(chapterDto.getCtpId())
                .orElseThrow(() -> new IllegalArgumentException("КТП не найден"));
        chapter.setCtp(ctp);

        return chapterRepo.save(chapter);
    }

    @Override
    public void deleteById(Long id) {
        chapterRepo.deleteById(id);
    }

    @Override
    public void update(Chapter chapter) {
        Chapter updatedChapter = chapterRepo.findById(chapter.getId()).orElse(null);
        if (updatedChapter == null) {
            throw new IllegalArgumentException("Раздел не найден!");
        }
        updatedChapter.setTitle(chapter.getTitle());
        chapterRepo.save(updatedChapter);
        
    }
}
