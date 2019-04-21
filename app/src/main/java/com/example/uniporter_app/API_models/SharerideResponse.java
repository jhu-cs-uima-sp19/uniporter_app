package com.example.uniporter_app.API_models;

import java.util.List;

public class SharerideResponse {

    private String date;
    private int distance_score;
    private String meeting_loc;
    private List<String> memeber;
    private String time;
    private int weight;

    public SharerideResponse(String date, int distance_score, String meeting_loc, List<String> memeber, String time, int weight) {
        this.date = date;
        this.distance_score = distance_score;
        this.meeting_loc = meeting_loc;
        this.memeber = memeber;
        this.time = time;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public int getDistance_score() {
        return distance_score;
    }

    public String getMeeting_loc() {
        return meeting_loc;
    }

    public List<String> getMemeber() {
        return memeber;
    }

    public String getTime() {
        return time;
    }

    public int getWeight() {
        return weight;
    }
}
