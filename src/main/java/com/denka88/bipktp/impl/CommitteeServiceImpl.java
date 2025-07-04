package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.CommitteeDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Committee;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.repo.CommitteeRepo;
import com.denka88.bipktp.service.CommitteeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommitteeServiceImpl implements CommitteeService {
    
    private final CommitteeRepo committeeRepo;
    private final CTPRepo ctpRepo;
    
    @Override
    public List<Committee> findAll() {
        return committeeRepo.findAll();
    }

    @Override
    public Optional<Committee> findById(Long id) {
        return committeeRepo.findById(id);
    }

    @Override
    public Committee save(CommitteeDto committeeDto) {
        Committee committee = new Committee();
        committee.setName(committeeDto.getName());
        committeeRepo.save(committee);
        
        return committee;
    }

    @Override
    public void deleteById(Long id) {
        
        Committee committee = committeeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Комиссия не найдена"));
        
        List<CTP> ctps = new ArrayList<>(committee.getCtps());
        
        ctps.forEach(ctp -> {
            ctp.setCommittee(null);
            ctpRepo.save(ctp);
        });
        
        committee.getCtps().clear();
        
        committeeRepo.delete(committee);
    }

    @Override
    public void update(Committee committee) {
        Committee updatedCommittee = committeeRepo.findById(committee.getId()).orElse(null);
        if (updatedCommittee == null) {
            throw new IllegalArgumentException("Комиссия не найдена");
        }
        updatedCommittee.setName(committee.getName());
        committeeRepo.save(updatedCommittee);
    }
}
