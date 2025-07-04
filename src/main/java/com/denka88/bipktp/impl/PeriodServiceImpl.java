package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.PeriodDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Period;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.repo.PeriodRepo;
import com.denka88.bipktp.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {
    
    private final PeriodRepo periodRepo;
    private final CTPRepo ctpRepo;
    
    @Override
    public List<Period> findAll() {
        return periodRepo.findAll();
    }

    @Override
    public Optional<Period> findById(Long id) {
        return periodRepo.findById(id);
    }

    @Override
    public Period save(PeriodDto periodDto) {
        Period period = new Period();
        period.setStart(periodDto.getStart());
        period.setEnd(periodDto.getEnd());
        periodRepo.save(period);
        
        return period;
    }

    @Override
    public void deleteById(Long id) {
        Period period = periodRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Период не найден"));
        
        List<CTP> ctps = new ArrayList<>(period.getCtps());
        
        ctps.forEach(ctp -> {
            ctp.setPeriod(null);
            ctpRepo.save(ctp);
        });
        
        period.getCtps().clear();
        
        periodRepo.delete(period);
    }

    @Override
    public void update(Period period) {
        Period updatedPeriod = periodRepo.findById(period.getId()).orElse(null);
        if (updatedPeriod == null) {
            throw new IllegalArgumentException("Период не найден");
        }
        updatedPeriod.setStart(period.getStart());
        updatedPeriod.setEnd(period.getEnd());
        periodRepo.save(updatedPeriod);
    }
}
