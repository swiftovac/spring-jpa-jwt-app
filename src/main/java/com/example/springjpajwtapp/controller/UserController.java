package com.example.springjpajwtapp.controller;


import com.example.springjpajwtapp.model.User;
import com.example.springjpajwtapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserRepository userRepository;


    @RequestMapping("/all")
    public List<User> getAlluser() {
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping("/delete/{id}")
    public String deleteUserbyId(@PathVariable("id") int id){
        User userToDelete = userRepository.getOne(id);
        userRepository.delete(userToDelete);
        return "User deleted!";
    }



}
