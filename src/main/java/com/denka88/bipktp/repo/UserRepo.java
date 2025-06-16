package com.denka88.bipktp.repo;

import com.denka88.bipktp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    
    Optional<User> findByLogin(String login);
    
    boolean existsByLogin(String login);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.disciplines")
    List<User> findAllWithDisciplines();
}
