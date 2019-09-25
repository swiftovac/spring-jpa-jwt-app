package com.example.springjpajwtapp.service;

import com.example.springjpajwtapp.model.User;
import com.example.springjpajwtapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException("User not found with username " + s);

        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
            new ArrayList<>());

    }

    public User save(User user) {
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        return userRepository.save(newUser);
    }

}
