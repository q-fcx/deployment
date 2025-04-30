package com.khaled.bank_management_system.repository;

import com.khaled.bank_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);

    User findUserByUsername(String username);

}
