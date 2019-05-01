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
import android.widget.Spinner;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

public class Luggage extends Fragment implements View.OnClickListener {

    int large_lugg_value;
    int small_lugg_value;
    int special_lugg_value;

    View myView;
    ImageButton front4;
    ImageButton back4;
    ImageButton x4;

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.activity_luggage, container, false);
        front4 = myView.findViewById(R.id.front4);
        back4 = myView.findViewById(R.id.back4);
        x4 = myView.findViewById(R.id.x4);

        front4.setOnClickListener(this);
        back4.setOnClickListener(this);
        x4.setOnClickListener(this);

        spinner1 = myView.findViewById(R.id.large_luggage_spinner);
        spinner2 = myView.findViewById(R.id.large_luggage_spinner);
        spinner3 = myView.findViewById(R.id.special_luggage_spinner);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.front4:
                large_lugg_value = Integer.parseInt(spinner1.getSelectedItem().toString());
                small_lugg_value = Integer.parseInt(spinner2.getSelectedItem().toString());
                special_lugg_value = Integer.parseInt(spinner3.getSelectedItem().toString());
                int total_lugguge_weight = compute_lugugge_weight(large_lugg_value, small_lugg_value, special_lugg_value);
                SharedPreferenceManager.getInstance(getContext())
                        .saveLuggage(total_lugguge_weight);
                Fragment fragment1 = new Early();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ft1.replace(R.id.screen_area, fragment1, "Luggage");
                ft1.commit();
                break;
            case R.id.back4:
                Fragment fragment2 = new Blocks();
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.screen_area, fragment2, "Address");
                ft2.commit();
                break;
            case R.id.x4:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }

    public int compute_lugugge_weight(int large_lugg_value, int small_lugg_value, int special_lugg_value) {
        return large_lugg_value * 2 + small_lugg_value + special_lugg_value;
    }
}
