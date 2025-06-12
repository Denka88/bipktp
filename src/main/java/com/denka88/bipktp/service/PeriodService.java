package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.PeriodDto;
import com.denka88.bipktp.model.Period;

import java.util.List;
import java.util.Optional;

public interface PeriodService {
    
    List<Period> findAll();
    Optional<Period> findById(Long id);
    Period save(PeriodDto periodDto);
    void deleteById(Long id);
    void update(Period period);
    
}
