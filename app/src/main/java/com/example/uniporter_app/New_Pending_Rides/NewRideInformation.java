package com.example.uniporter_app.New_Pending_Rides;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRideInformation implements Comparable< NewRideInformation >{
    public Integer id;
    public String type;
    String airline;
    String flight_no;
    public String date;
    String flight_time;

    int getViewType() {
        return 6500;
    }

    @Override
    public int compareTo(NewRideInformation newRideInformation) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        Date this_date = null;
        Date other_date = null;
        try {
            this_date = sdf.parse(this.date);
            other_date = sdf.parse(newRideInformation.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert this_date != null;
        return this_date.compareTo(other_date);
    }
}
