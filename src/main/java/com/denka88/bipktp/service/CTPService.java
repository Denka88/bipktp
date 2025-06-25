package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.CTPDto;
import com.denka88.bipktp.model.CTP;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CTPService {
    
    List<CTP> findAll();
    Optional<CTP> findById(Long id);
    CTP save(CTPDto ctpDto);
    void deleteById(Long id);
    void update(CTP ctp);
    
    Page<CTP> findPaginated(Pageable pageable);
    Page<CTP> findPaginatedByUserId(Pageable pageable, Long userId);
}
