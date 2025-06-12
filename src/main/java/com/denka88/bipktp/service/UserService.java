package com.denka88.bipktp.service;

import com.denka88.bipktp.dto.UserDto;
import com.denka88.bipktp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    List<User> findAll();
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    User save(UserDto userDto);
    void deleteById(Long id);
    void update(User user);
    
}
