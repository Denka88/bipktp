package com.denka88.bipktp.impl;

import com.denka88.bipktp.dto.UserDto;
import com.denka88.bipktp.model.Discipline;
import com.denka88.bipktp.model.User;
import com.denka88.bipktp.model.Role;
import com.denka88.bipktp.repo.UserRepo;
import com.denka88.bipktp.service.DisciplineService;
import com.denka88.bipktp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final DisciplineService disciplineService;
    
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public boolean isLoginAvailable(String login) {
        return !userRepo.existsByLogin(login);
    }

    @Override
    public List<User> findAllWithDisciplines() {
        return userRepo.findAllWithDisciplines();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User save(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setPatronymic(userDto.getPatronymic());
        
        if(userDto.isAdmin()) {
            user.setRole(Set.of(Role.ADMIN, Role.TEACHER));
        }else {
            user.setRole(Collections.singleton(Role.TEACHER));
        }

        List<Discipline> disciplines = disciplineService.findAllById(userDto.getDisciplineIds());
     
        user.setDisciplines(disciplines);
        userRepo.save(user);

        
        return user;
    }

    @Override
    public void deleteById(Long id) {
        
        
        userRepo.deleteById(id);
    }

    @Override
    public void update(User user, boolean isAdmin, List<Long> disciplineIds) {
        User updatedUser = userRepo.findById(user.getId()).orElse(null);
        if (updatedUser == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }
        updatedUser.setLogin(user.getLogin());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            updatedUser.setPassword(encoder.encode(user.getPassword()));
        }
        updatedUser.setSurname(user.getSurname());
        updatedUser.setName(user.getName());
        updatedUser.setPatronymic(user.getPatronymic());
        
        System.out.println("UPDATE SERVICE: ");
        System.out.println(disciplineIds);
        updatedUser.setDisciplines(disciplineService.findAllById(disciplineIds));

        System.out.println(updatedUser.getDisciplines());
        
        
        
        if(isAdmin) {
            updatedUser.setRole(new HashSet<>(Set.of(Role.ADMIN, Role.TEACHER)));
        }else {
            updatedUser.setRole(new HashSet<>(Collections.singleton(Role.TEACHER)));
        }
        
        userRepo.save(updatedUser);
    }
}
