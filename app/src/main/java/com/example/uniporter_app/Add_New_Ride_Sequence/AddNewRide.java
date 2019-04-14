package com.example.uniporter_app.Add_New_Ride_Sequence;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.support.v4.app.Fragment;

import com.example.uniporter_app.R;

public class AddNewRide extends AppCompatActivity {
    //front buttons
    ImageButton _front1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);
        _front1 = findViewById(R.id.front1);

        //front buttons
        _front1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Address();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area, fragment, "Address");
                ft.commit();
            }
        });
    }
}