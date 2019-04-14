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
import android.widget.Button;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;

public class Luggage extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_luggage, container, false);
        ImageButton front4 = myView.findViewById(R.id.front4);
        ImageButton back4 = myView.findViewById(R.id.back4);
        Button x4 = myView.findViewById(R.id.x4);
        front4.setOnClickListener(this);
        back4.setOnClickListener(this);
        x4.setOnClickListener(this);
        Spinner spinner1 = (Spinner) myView.findViewById(R.id.large_luggage_spinner);
        String large_lugg_value = spinner1.getSelectedItem().toString();
        Spinner spinner2 = (Spinner) myView.findViewById(R.id.large_luggage_spinner);
        String small_lugg_value = spinner2.getSelectedItem().toString();
        Spinner spinner3 = (Spinner) myView.findViewById(R.id.special_luggage_spinner);
        String special_lugg_value = spinner3.getSelectedItem().toString();
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
}
