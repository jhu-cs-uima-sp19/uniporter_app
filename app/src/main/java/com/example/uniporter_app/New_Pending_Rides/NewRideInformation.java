package com.example.uniporter_app.New_Pending_Rides;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewRideInformation implements Comparable< NewRideInformation >{
    public Integer id;
    public String type;
    public String airline;
    public String flight_no;
    public String date;
    public String flight_time;

    public int getViewType() {
        return 6500;
    }

    @Override
    public int compareTo(NewRideInformation newRideInformation) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        Date this_date = null;
        Date other_date = null;
        try {
            this_date = sdf.parse(this.date);
            other_date = sdf.parse(newRideInformation.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this_date.compareTo(other_date);
    }
}
