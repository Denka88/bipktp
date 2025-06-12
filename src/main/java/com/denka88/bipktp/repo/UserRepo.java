package com.denka88.bipktp.repo;

import com.denka88.bipktp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    
    Optional<User> findByLogin(String login);
    
    boolean existsByLogin(String login);
}
