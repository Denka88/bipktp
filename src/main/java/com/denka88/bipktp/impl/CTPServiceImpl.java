package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.CTPDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Chapter;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.repo.ChapterRepo;
import com.denka88.bipktp.repo.RecordRepo;
import com.denka88.bipktp.service.CTPService;
import com.denka88.bipktp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CTPServiceImpl implements CTPService {
    
    private final CTPRepo ctpRepo;
    private final UserService userService;
    private final ChapterRepo chapterRepo;
    private final RecordRepo recordRepo;
    
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
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        
        String login = auth.getName();
        
        User user = userService.findByLogin(login).orElse(null);
        
        CTP ctp = new CTP();
        ctp.setName(ctpDto.getName());
        ctp.setCommittee(ctpDto.getCommittee());
        ctp.setPeriod(ctpDto.getPeriod());
        ctp.setSpeciality(ctpDto.getSpeciality());
        ctp.setDiscipline(ctpDto.getDiscipline());
        ctp.setUser(user);
        
        ctpRepo.save(ctp);
        
        return ctp;
    }

    @Override
    public void deleteById(Long id) {
        CTP ctp = ctpRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("КТП не найден"));

        if(ctp.getChapters() != null){
            for (Chapter chapter : ctp.getChapters()) {
                if (chapter.getRecords() != null) {
                    recordRepo.deleteAll(chapter.getRecords());
                }
            }
            chapterRepo.deleteAll(ctp.getChapters());
        }

        ctpRepo.deleteById(id);
    }

    @Override
    public void update(CTP ctp) {
        CTP updatedCtp = ctpRepo.findById(ctp.getId()).orElse(null);
        if (updatedCtp == null) {
            throw new IllegalArgumentException("КТП не найден");
        }
        updatedCtp.setName(ctp.getName());
        updatedCtp.setCommittee(ctp.getCommittee());
        updatedCtp.setPeriod(ctp.getPeriod());
        updatedCtp.setSpeciality(ctp.getSpeciality());
        updatedCtp.setDiscipline(ctp.getDiscipline());
        ctpRepo.save(updatedCtp);
    }

    @Override
    public Page<CTP> findPaginated(Pageable pageable) {
        
        final List<CTP> ctps = ctpRepo.findAll();
        
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CTP> list;
        
        if(ctps.size() < startItem){
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startItem + pageSize, ctps.size());
            list = ctps.subList(startItem, toIndex);
        }
        
        return new PageImpl<>(list, pageable, ctps.size());
    }

    @Override
    public Page<CTP> findPaginatedByUserId(Pageable pageable, Long userId) {
        
        final List<CTP> ctps = ctpRepo.findAllByUserId(userId);
        
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CTP> list;
        if(ctps.size() < startItem){
            list = Collections.emptyList();
        }
        else{
            int toIndex = Math.min(startItem + pageSize, ctps.size());
            list = ctps.subList(startItem, toIndex);
        }
        
        return new PageImpl<>(list, pageable, ctps.size());
    }
}
