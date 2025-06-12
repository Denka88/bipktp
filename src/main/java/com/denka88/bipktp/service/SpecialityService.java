package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.SpecialityDto;
import com.denka88.bipktp.model.Speciality;

import java.util.List;
import java.util.Optional;

public interface SpecialityService {
    
    List<Speciality> findAll();
    Optional<Speciality> findById(Long id);
    Speciality save(SpecialityDto specialityDto);
    void deleteById(Long id);
    void update(Speciality speciality);
    
}
