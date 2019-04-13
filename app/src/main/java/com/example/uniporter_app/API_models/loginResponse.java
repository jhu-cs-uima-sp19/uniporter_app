package com.example.uniporter_app.API_models;

public class loginResponse {
    private String token;

    public loginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
