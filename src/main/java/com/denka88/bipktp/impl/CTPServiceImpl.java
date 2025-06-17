package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.CTPDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.service.CTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CTPServiceImpl implements CTPService {
    
    private final CTPRepo ctpRepo;
    
    @Override
    public List<CTP> findAll() {
        return ctpRepo.findAll();
    }

    @Override
    public Optional<CTP> findById(Long id) {
        return ctpRepo.findById(id);
    }

    @Override
    public CTP save(CTPDto ctpDto) {
        CTP ctp = new CTP();
        System.out.println("Создал");
        ctp.setName(ctpDto.getName());
        System.out.println("имя" + ctp.getName());
        System.out.println("имя" + ctpDto.getName());
        ctp.setCommittee(ctpDto.getCommittee());
        System.out.println("Комиссия" + ctp.getCommittee());
        System.out.println("Комиссия" + ctpDto.getCommittee());
        ctp.setPeriod(ctpDto.getPeriod());
        System.out.println("ПЕриод" + ctp.getPeriod());
        System.out.println("ПЕриод" + ctpDto.getPeriod());
        ctp.setSpeciality(ctpDto.getSpeciality());
        System.out.println("Cпец" + ctp.getSpeciality());
        System.out.println("Cпец" + ctpDto.getSpeciality());
        ctp.setDiscipline(ctpDto.getDiscipline());
        System.out.println("дисц" + ctp.getDiscipline());
        System.out.println("дисц" + ctpDto.getDiscipline());
        
        ctpRepo.save(ctp);
        
        return ctp;
    }

    @Override
    public void deleteById(Long id) {
        ctpRepo.deleteById(id);
    }

    @Override
    public void update(CTP ctp) {
        CTP updatedCtp = ctpRepo.findById(ctp.getId()).orElse(null);
        if (updatedCtp == null) {
            throw new IllegalArgumentException("КТП не найден");
        }
        updatedCtp.setName(ctp.getName());
    }
}
