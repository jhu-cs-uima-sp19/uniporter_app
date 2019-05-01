package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;
import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import android.widget.Spinner;

import java.util.Objects;

public class Early extends Fragment implements View.OnClickListener {

    int early_value;
    View myView;
    ImageButton front5;
    ImageButton back5;
    ImageButton x5;
    Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = Objects.requireNonNull(inflater).inflate(R.layout.activity_early, container, false);
        front5 = myView.findViewById(R.id.front5);
        back5 = myView.findViewById(R.id.back5);
        x5 = myView.findViewById(R.id.x5);
        front5.setOnClickListener(this);
        back5.setOnClickListener(this);
        x5.setOnClickListener(this);
        spinner = myView.findViewById(R.id.early_spinner);
        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.front5:
                early_value = Integer.parseInt(spinner.getSelectedItem().toString());
                SharedPreferenceManager.getInstance(getContext())
                        .saveWaitTime(early_value);
                Fragment fragment1 = new Confirmation();
                FragmentTransaction ft1 = null;
                if (getFragmentManager() != null) {
                    ft1 = getFragmentManager().beginTransaction();
                }
                if (ft1 != null) {
                    ft1.replace(R.id.screen_area, fragment1, "Confirmation");
                }
                if (ft1 != null) {
                    ft1.commit();
                }
                break;
            case R.id.back5:
                Fragment fragment2 = new Luggage();
                FragmentTransaction ft2 = null;
                if (getFragmentManager() != null) {
                    ft2 = getFragmentManager().beginTransaction();
                }
                if (ft2 != null) {
                    ft2.replace(R.id.screen_area, fragment2, "Luggage");
                }
                if (ft2 != null) {
                    ft2.commit();
                }
                break;
            case R.id.x5:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            default:
                break;
        }
    }

}
