package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.ChapterDto;
import com.denka88.bipktp.model.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterService {
    
    List<Chapter> findAll();
    Optional<Chapter> findById(Long id);
    Chapter save(ChapterDto chapterDto);
    void deleteById(Long id);
    void update(Chapter chapter);
    
}
