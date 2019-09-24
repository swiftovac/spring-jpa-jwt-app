package com.example.springjpajwtapp.model;


// we need this class to store username and password from client
public class JwtRequset {

    private String username;
    private String password;

    public JwtRequset() {
    }

    public JwtRequset(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
