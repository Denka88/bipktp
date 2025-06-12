package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.LessonTypeDto;
import com.denka88.bipktp.model.LessonType;
import com.denka88.bipktp.repo.LessonTypeRepo;
import com.denka88.bipktp.service.LessonTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonTypeServiceImpl implements LessonTypeService {
    
    private final LessonTypeRepo lessonTypeRepo;
    
    @Override
    public List<LessonType> findAll() {
        return lessonTypeRepo.findAll();
    }

    @Override
    public Optional<LessonType> findById(Long id) {
        return lessonTypeRepo.findById(id);
    }

    @Override
    public LessonType save(LessonTypeDto lessonTypeDto) {
        LessonType lessonType = new LessonType();
        lessonType.setName(lessonTypeDto.getName());
        lessonTypeRepo.save(lessonType);
        
        return lessonType;
    }

    @Override
    public void deleteById(Long id) {
        lessonTypeRepo.deleteById(id);
    }

    @Override
    public void update(LessonType lessonType) {
        LessonType updatedLessonType = lessonTypeRepo.findById(lessonType.getId()).orElse(null);
        if (updatedLessonType == null) {
            throw new IllegalArgumentException("Тип занятия не найден");
        }
        updatedLessonType.setName(lessonType.getName());
        lessonTypeRepo.save(updatedLessonType);
    }
}
