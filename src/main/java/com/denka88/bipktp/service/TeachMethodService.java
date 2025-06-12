package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.TeachMethodDto;
import com.denka88.bipktp.model.TeachMethod;

import java.util.List;
import java.util.Optional;

public interface TeachMethodService {
    
    List<TeachMethod> findAll();
    Optional<TeachMethod> findById(Long id);
    TeachMethod save(TeachMethodDto teachMethodDto);
    void deleteById(Long id);
    void update(TeachMethod teachMethod);
    
}
