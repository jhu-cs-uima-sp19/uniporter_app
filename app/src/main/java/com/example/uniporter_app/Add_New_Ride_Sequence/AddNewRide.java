package com.example.uniporter_app.Add_New_Ride_Sequence;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.uniporter_app.R;

public class AddNewRide extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_seq);

        Fragment fragment1 = new FlightInfo();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.screen_area, fragment1, "Flight Information");
        ft1.commit();
    }
}