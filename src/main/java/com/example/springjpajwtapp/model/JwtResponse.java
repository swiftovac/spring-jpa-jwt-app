package com.example.springjpajwtapp.model;


// This class is required for creating a response containing the JWT to be returned to the user.
public class JwtResponse {

    private final String jwttoken;


    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getJwttoken() {
        return jwttoken;
    }
}
