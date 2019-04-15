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

public class Early extends Fragment implements View.OnClickListener {
    String early_value;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_early, container, false);
        ImageButton front5 = myView.findViewById(R.id.front5);
        ImageButton back5 = myView.findViewById(R.id.back5);
        ImageButton x5 = myView.findViewById(R.id.back5);
        front5.setOnClickListener(this);
        back5.setOnClickListener(this);
        x5.setOnClickListener(this);
        Spinner spinner = (Spinner) myView.findViewById(R.id.early_spinner);
        early_value = spinner.getSelectedItem().toString();
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
                Fragment fragment1 = new Confirmation();
                FragmentTransaction ft1 = getFragmentManager().beginTransaction();
                ft1.replace(R.id.screen_area, fragment1, "Confirmation");
                ft1.commit();
                break;
            case R.id.back5:
                Fragment fragment2 = new Luggage();
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.screen_area, fragment2, "Luggage");
                ft2.commit();
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
