package com.example.uniporter_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;

public class Luggage extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_luggage, container, false);
        ImageButton front4 = myView.findViewById(R.id.front4);
        front4.setOnClickListener(this);
        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        Fragment fragment = new Early();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.screen_area, fragment, "Luggage");
        ft.commit();
    }
}
