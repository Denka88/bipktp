package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.LessonTypeDto;
import com.denka88.bipktp.model.LessonType;

import java.util.List;
import java.util.Optional;

public interface LessonTypeService {
    
    List<LessonType> findAll();
    Optional<LessonType> findById(Long id);
    LessonType save(LessonTypeDto lessonTypeDto);
    void deleteById(Long id);
    void update(LessonType lessonType);
    
}
