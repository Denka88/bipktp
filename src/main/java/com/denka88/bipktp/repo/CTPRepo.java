package com.denka88.bipktp.repo;

import com.denka88.bipktp.model.CTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CTPRepo extends JpaRepository<CTP, Long> {
    
    List<CTP> findAllByUserId(Long userId);
    
}
