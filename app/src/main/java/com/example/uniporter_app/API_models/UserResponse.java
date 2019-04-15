package com.example.uniporter_app.API_models;

public class UserResponse {

    private int id;
    private String email;
    private String name;

    public UserResponse(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
