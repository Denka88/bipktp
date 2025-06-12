package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.TeachMethodDto;
import com.denka88.bipktp.model.TeachMethod;
import com.denka88.bipktp.repo.TeachMethodRepo;
import com.denka88.bipktp.service.TeachMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeachMethodServiceImpl implements TeachMethodService {
    
    private final TeachMethodRepo teachMethodRepo;
    
    @Override
    public List<TeachMethod> findAll() {
        return teachMethodRepo.findAll();
    }

    @Override
    public Optional<TeachMethod> findById(Long id) {
        return teachMethodRepo.findById(id);
    }

    @Override
    public TeachMethod save(TeachMethodDto teachMethodDto) {
        TeachMethod teachMethod = new TeachMethod();
        teachMethod.setName(teachMethodDto.getName());
        teachMethodRepo.save(teachMethod);
        
        return teachMethod;
    }

    @Override
    public void deleteById(Long id) {
        teachMethodRepo.deleteById(id);
    }

    @Override
    public void update(TeachMethod teachMethod) {
        TeachMethod updatedTeachMethod = teachMethodRepo.findById(teachMethod.getId()).orElse(null);
        if (updatedTeachMethod == null) {
            throw new IllegalArgumentException("Метод обучения не найден");
        }
        updatedTeachMethod.setName(teachMethod.getName());
        teachMethodRepo.save(updatedTeachMethod);
    }
}
