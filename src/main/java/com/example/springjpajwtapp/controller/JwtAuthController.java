package com.example.springjpajwtapp.controller;

import com.example.springjpajwtapp.config.JwtTokenUtil;
import com.example.springjpajwtapp.model.JwtRequset;
import com.example.springjpajwtapp.model.JwtResponse;
import com.example.springjpajwtapp.model.User;
import com.example.springjpajwtapp.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthController {

    @Autowired
     private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequset authRequst) throws Exception{
        authenticate(authRequst.getUsername(), authRequst.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequst.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));


    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) throws Exception{
        return ResponseEntity.ok(jwtUserDetailsService.save(user));


    }

    // method to authenticate user by username and password
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
