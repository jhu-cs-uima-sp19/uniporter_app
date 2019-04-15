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
import android.widget.Spinner;

public class Blocks extends Fragment implements View.OnClickListener {
    String blocks_value;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_blocks, container, false);
        ImageButton front3 = myView.findViewById(R.id.front3);
        ImageButton back3 = myView.findViewById(R.id.back3);
        ImageButton x3 = myView.findViewById(R.id.x3);
        front3.setOnClickListener(this);
        back3.setOnClickListener(this);
        x3.setOnClickListener(this);
        Spinner spinner = (Spinner) myView.findViewById(R.id.blocks_spinner);
        blocks_value = spinner.getSelectedItem().toString();
        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.front3:
                Fragment fragment1 = new Luggage();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ft1.replace(R.id.screen_area, fragment1, "Luggage");
                ft1.commit();
                break;
            case R.id.back3:
                Fragment fragment2 = new Address();
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.screen_area, fragment2, "Address");
                ft2.commit();
                break;
            case R.id.x3:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }
}
