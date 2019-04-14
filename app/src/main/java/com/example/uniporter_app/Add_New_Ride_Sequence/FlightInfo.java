package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import android.widget.EditText;
import android.widget.Spinner;
public class FlightInfo extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_flight_info, container, false);
        ImageButton front1 = myView.findViewById(R.id.front1);
        EditText flightnum = (EditText) myView.findViewById(R.id.flightnum);
        final String flight_value = flightnum.getText().toString();
        Spinner spinner = (Spinner) myView.findViewById(R.id.airline_spinner);
        String airline_value = spinner.getSelectedItem().toString();
        front1.setOnClickListener(this);
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
