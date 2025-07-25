package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.DisciplineDto;
import com.denka88.bipktp.model.CTP;
import com.denka88.bipktp.model.Discipline;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.repo.CTPRepo;
import com.denka88.bipktp.repo.DisciplineRepo;
import com.denka88.bipktp.repo.UserRepo;
import com.denka88.bipktp.service.DisciplineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DisciplineServiceImpl implements DisciplineService {
    
    private final DisciplineRepo disciplineRepo;
    private final UserRepo userRepo;
    private final CTPRepo ctpRepo;

    @Override
    public List<Discipline> findAll() {
        return disciplineRepo.findAll();
    }

    @Override
    public Optional<Discipline> findById(Long id) {
        return disciplineRepo.findById(id);
    }

    @Override
    public Discipline save(DisciplineDto disciplineDto) {
        Discipline discipline = new Discipline();
        discipline.setName(disciplineDto.getName());
        disciplineRepo.save(discipline);
        
        return discipline;
    }

    @Override
    public void deleteById(Long id) {

        Discipline discipline = disciplineRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Дисциплина не найдена"));

        Set<User> users = new HashSet<>(discipline.getUsers());
        
        List<CTP> ctps = new ArrayList<>(discipline.getCtps());
        
        ctps.forEach(ctp -> {
            ctp.setDiscipline(null);
            ctpRepo.save(ctp);
        });
        
        users.forEach(user -> {
            user.getDisciplines().remove(discipline);
            userRepo.save(user);
        });
        
        discipline.getUsers().clear();
        discipline.getCtps().clear();
        
        disciplineRepo.delete(discipline);
    }

    @Override
    public void update(Discipline discipline) {
        Discipline updatedDiscipline = disciplineRepo.findById(discipline.getId()).orElse(null);
        if (updatedDiscipline == null) {
            throw new IllegalArgumentException("Дисциплина не найдена");
        }
        updatedDiscipline.setName(discipline.getName());
        disciplineRepo.save(updatedDiscipline);
    }

    @Override
    public List<Discipline> findAllById(List<Long> ids) {
        return disciplineRepo.findAllById(ids);
    }
}
