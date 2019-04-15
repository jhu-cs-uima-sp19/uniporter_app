package com.example.uniporter_app.API_models;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    private String id;
    private String email;
    private String name;

    public RegisterResponse(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}

