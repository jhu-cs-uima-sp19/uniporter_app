package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.Calendar;
import java.util.Objects;

public class FlightInfo extends Fragment implements View.OnClickListener {

    String flight_value;
    String airline_value;
    String departure_time;
    String depature_date;

    private Spinner airline_spinner;
    private EditText flight_no;
    private Button get_departure_date;
    private Button get_depature_time;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.activity_flight_info, container, false);

        ImageButton front1 = myView.findViewById(R.id.front1);
        front1.setOnClickListener(this);

        ImageButton x1 = myView.findViewById(R.id.x1);
        x1.setOnClickListener(this);

        flight_no = myView.findViewById(R.id.flightnum);
        airline_spinner = myView.findViewById(R.id.airline_spinner);

        get_departure_date = myView.findViewById(R.id.set_departure_date);
        get_departure_date.setOnClickListener(this);
        get_depature_time = myView.findViewById(R.id.set_departure_time);
        get_depature_time.setOnClickListener(this);

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String month_str = ((month % 13) + 1 < 10) ? "0" + Integer.toString((month % 13) + 1) : Integer.toString((month % 13) + 1);
                String day_str = (day < 10) ? "0" + Integer.toString(day) : Integer.toString(day);
                String year_str = Integer.toString(year).substring(2);
                depature_date = month_str + "/" + day_str + "/" + year_str;
            }
        };

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                departure_time = hour + ":" + minute + ":00";
            }
        };

        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Log.w("date picker", Integer.toString(day));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setTime(View myView) {
        get_depature_time = myView.findViewById(R.id.set_departure_time);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog time_dialog = new TimePickerDialog(
                myView.getContext(),
                mTimeSetListener,
                hour, minute, true);
        Objects.requireNonNull(time_dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        time_dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.front1:
                flight_value = flight_no.getText().toString();
                airline_value = airline_spinner.getSelectedItem().toString();
                if (flight_value != null) {
                    SharedPreferenceManager.getInstance(getContext())
                            .saveFlightNo(flight_value);
                }
                SharedPreferenceManager.getInstance(getContext())
                        .saveAirline(airline_value);
                SharedPreferenceManager.getInstance(getContext())
                        .saveFlightDate(depature_date);
                SharedPreferenceManager.getInstance(getContext())
                        .saveFlightTime(departure_time);
                Fragment fragment1 = new Address();
                FragmentTransaction ft1 = null;
                if (getFragmentManager() != null) {
                    ft1 = getFragmentManager().beginTransaction();
                }
                if (ft1 != null) {
                    ft1.replace(R.id.screen_area, fragment1, "Address");
                }
                if (ft1 != null) {
                    ft1.commit();
                }
                break;
            case R.id.x1:
                Log.w("QUIT", "found");
                Intent intent = new Intent(getActivity(), MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            case R.id.set_departure_date:
                Log.w("set_depart_date", "found");
                setDate(v);
                break;
            case R.id.set_departure_time:
                setTime(v);
                break;
            default:
                break;
        }
    }

}
