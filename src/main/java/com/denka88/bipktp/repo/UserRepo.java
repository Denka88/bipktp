package com.denka88.bipktp.repo;

import com.denka88.bipktp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
