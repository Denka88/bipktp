package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.CommitteeDto;
import com.denka88.bipktp.model.Committee;

import java.util.List;
import java.util.Optional;

public interface CommitteeService {
    
    List<Committee> findAll();
    Optional<Committee> findById(Long id);
    Committee save(CommitteeDto committeeDto);
    void deleteById(Long id);
    void update(Committee committee);
    
}
