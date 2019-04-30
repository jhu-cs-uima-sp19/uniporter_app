package com.example.uniporter_app.Add_New_Ride_Sequence;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v4.app.Fragment;
import android.widget.Spinner;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.Calendar;

public class AddNewRide extends AppCompatActivity implements View.OnClickListener {

    //Buttons buttons
    private ImageButton _front1;
    private ImageButton _x1;
    private Button get_departure_date;
    private Button get_depature_time;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    // Inputs
    Spinner airline_spinner;
    EditText flight_no_input;

    //Values
    String airline;
    String flight_no;
    String departure_time;
    String depature_date;

    private void setDate(View myView) {
        get_departure_date = myView.findViewById(R.id.set_departure_date);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);

        DatePickerDialog dialog = new DatePickerDialog(
                myView.getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Log.w("date picker", Integer.toString(day));

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String month_str = ((month % 13) + 1 < 10) ? "0" + Integer.toString((month % 13) + 1) : Integer.toString((month % 13) + 1);
                String day_str = (day < 10) ? "0" + Integer.toString(day) : Integer.toString(day);
                String year_str = Integer.toString(year).substring(2);
                String date = month_str + "/" + day_str + "/" + year_str;
                depature_date = date;
            }
        };
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_seq);

        Fragment fragment1 = new FlightInfo();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.screen_area, fragment1, "Flight Information");
        ft1.commit();
/*
        _front1 = findViewById(R.id.front1);
        _x1 = findViewById(R.id.x1);
        get_departure_date = findViewById(R.id.set_departure_date);

        airline_spinner = findViewById(R.id.airline_spinner);
        flight_no_input = findViewById(R.id.flightnum);

        _front1.setOnClickListener(this);
        _x1.setOnClickListener(this);
        get_departure_date.setOnClickListener(this);
        */
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.front1:
                Log.w("airline", airline);
                Log.w("flight_no", flight_no);
                if(flight_no != null && airline != null && depature_date != null) {
                    SharedPreferenceManager.getInstance(this)
                            .saveFlightNo(flight_no);
                    SharedPreferenceManager.getInstance(this)
                            .saveAirline(airline);
                    SharedPreferenceManager.getInstance(this)
                            .saveFlightDate(depature_date);
                }
                Fragment fragment1 = new Address();
                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.screen_area, fragment1, "Address");
                ft1.commit();
                break;
            case R.id.x1:
                Log.w("QUIT", "found");
                Intent intent = new Intent(this, MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            case R.id.set_departure_date:
                Log.w("set_depart_date", "found");
                setDate(v);
                break;
            default:
                break;
        }
    }
}