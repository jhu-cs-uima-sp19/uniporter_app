package com.example.uniporter_app.API_models;

import java.util.List;

public class RideResponse {
    private int id;
    private int user;
    private String user_email;
    private String type;
    private String airline;
    private String flight_no;
    private String date;
    private String flight_time;
    private List<Integer> preferences;
    private List<Integer> tags;

    public RideResponse(int id, int user, String user_email, String type, String airline, String flight_no, String date, String flight_time, List<Integer> preferences, List<Integer> tags) {
        this.id = id;
        this.user = user;
        this.user_email = user_email;
        this.type = type;
        this.airline = airline;
        this.flight_no = flight_no;
        this.date = date;
        this.flight_time = flight_time;
        this.preferences = preferences;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public int getUser() {
        return user;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getType() {
        return type;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlight_no() {
        return flight_no;
    }

    public String getDate() {
        return date;
    }

    public String getFlight_time() {
        return flight_time;
    }

    public List<Integer> getPreferences() {
        return preferences;
    }

    public List<Integer> getTags() {
        return tags;
    }
}
