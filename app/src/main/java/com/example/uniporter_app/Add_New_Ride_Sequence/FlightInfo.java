package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import android.widget.EditText;
import android.widget.Spinner;

public class FlightInfo extends Fragment implements View.OnClickListener {
    String flight_value;
    String airline_value;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_flight_info, container, false);
        ImageButton front1 = myView.findViewById(R.id.front1);
        ImageButton x1 = myView.findViewById(R.id.x1);
        front1.setOnClickListener(this);
        x1.setOnClickListener(this);
        EditText flightnum = (EditText) myView.findViewById(R.id.flightnum);
        Spinner spinner = (Spinner) myView.findViewById(R.id.airline_spinner);
        flight_value = flightnum.getText().toString();
        airline_value = spinner.getSelectedItem().toString();

        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.front1:
                Log.w("airline", airline_value);
                Log.w("flight_no", flight_value);
                if(flight_value != null && airline_value != null) {
                    SharedPreferenceManager.getInstance(getContext())
                            .saveFlightNo(flight_value);
                    SharedPreferenceManager.getInstance(getContext())
                            .saveAirline(airline_value);
                }
                Fragment fragment1 = new Address();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ft1.replace(R.id.screen_area, fragment1, "Address");
                ft1.commit();
                break;
            case R.id.x1:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }

}
