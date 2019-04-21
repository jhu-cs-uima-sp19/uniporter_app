package com.example.uniporter_app.Add_New_Ride_Sequence;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v4.app.Fragment;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

public class AddNewRide extends AppCompatActivity implements View.OnClickListener {

    //Buttons buttons
    ImageButton _front1;
    ImageButton _x1;

    // Inputs
    Spinner airline_spinner;
    EditText flight_no_input;

    //Values
    String airline;
    String flight_no;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);

        _front1 = findViewById(R.id.front1);
        _x1 = findViewById(R.id.x1);
        airline_spinner = findViewById(R.id.airline_spinner);
        flight_no_input = findViewById(R.id.flightnum);

        _front1.setOnClickListener(this);
        _x1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.front1:
                airline = airline_spinner.getSelectedItem().toString();
                flight_no = flight_no_input.getText().toString();
                SharedPreferenceManager.getInstance(this)
                        .saveAirline(airline);
                SharedPreferenceManager.getInstance(this)
                        .saveFlightNo(flight_no);
                Fragment fragment = new Address();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.screen_area, fragment, "Address");
                ft.commit();
                break;
            case R.id.x1:
                Intent intent = new Intent(this, MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }

    }
}