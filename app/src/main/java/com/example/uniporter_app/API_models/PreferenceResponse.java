package com.example.uniporter_app.API_models;

public class PreferenceResponse {

    private int id;
    private String name;
    private int weight;
    private int wait_time;
    private String residence;

    public PreferenceResponse(int id, String name, int weight, int wait_time, String residence) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.wait_time = wait_time;
        this.residence = residence;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getWait_time() {
        return wait_time;
    }

    public String getResidence() {
        return residence;
    }
}
