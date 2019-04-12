package com.example.uniporter_app;

import java.util.ArrayList;

public class NewRideData {

    public static ArrayList<NewRideInformation> getRideData() {

        ArrayList<NewRideInformation> data = new ArrayList<>();

        // TODO: Call our API can populate these data points

        int[] location = {
                R.drawable.ic__car_24dp,
                R.drawable.ic_schedule_24dp
        };

        String[] date = {"test1", "test2"};

        for (int i = 0; i < location.length; i++) {

            NewRideInformation current = new NewRideInformation();
            current.date = date[i];
            current.location = location[i];

            data.add(current);
        }

        return data;
    }
}
