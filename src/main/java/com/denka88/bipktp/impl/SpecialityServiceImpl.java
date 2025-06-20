package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.SpecialityDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Speciality;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.repo.SpecialityRepo;
import com.denka88.bipktp.service.SpecialityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialityServiceImpl implements SpecialityService {
    
    private final SpecialityRepo specialityRepo;
    private final CTPRepo ctpRepo;
    
    @Override
    public List<Speciality> findAll() {
        return specialityRepo.findAll();
    }

    @Override
    public Optional<Speciality> findById(Long id) {
        return specialityRepo.findById(id);
    }

    @Override
    public Speciality save(SpecialityDto specialityDto) {
        Speciality speciality = new Speciality();
        speciality.setName(specialityDto.getName());
        speciality.setQualification(specialityDto.getQualification());
        
        specialityRepo.save(speciality);
        
        return speciality;
    }

    @Override
    public void deleteById(Long id) {
        Speciality speciality = specialityRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Специальность не найдена"));
        
        List<CTP> ctps = new ArrayList<>(speciality.getCtps());
        
        ctps.forEach(ctp -> {
            ctp.setSpeciality(null);
            ctpRepo.save(ctp);
        });
        
        speciality.getCtps().clear();
        
        specialityRepo.delete(speciality);
    }

    @Override
    public void update(Speciality speciality) {
        Speciality updatedSpeciality = specialityRepo.findById(speciality.getId()).orElse(null);
        if (updatedSpeciality == null) {
            throw new IllegalArgumentException("Специальность не найдена");
        }
        updatedSpeciality.setName(speciality.getName());
        updatedSpeciality.setQualification(speciality.getQualification());
        
        specialityRepo.save(updatedSpeciality);
    }
}
