package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.DisciplineDto;
import com.denka88.bipktp.model.Discipline;

import java.util.List;
import java.util.Optional;

public interface DisciplineService {

    List<Discipline> findAll();
    Optional<Discipline> findById(Long id);
    Discipline save(DisciplineDto disciplineDto);
    void deleteById(Long id);
    void update(Discipline discipline);
}
