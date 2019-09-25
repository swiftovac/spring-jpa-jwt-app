package com.example.springjpajwtapp.repository;

import com.example.springjpajwtapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);

}
