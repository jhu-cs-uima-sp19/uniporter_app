package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Blocks extends Fragment implements View.OnClickListener {

    int blocks_value;
    View myView;
    ImageButton front3;
    ImageButton back3;
    ImageButton x3;
    Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = Objects.requireNonNull(inflater).inflate(R.layout.activity_blocks, container, false);

        front3 = myView.findViewById(R.id.front3);
        back3 = myView.findViewById(R.id.back3);
        x3 = myView.findViewById(R.id.x3);

        front3.setOnClickListener(this);
        back3.setOnClickListener(this);
        x3.setOnClickListener(this);
        spinner = myView.findViewById(R.id.blocks_spinner);
        spinner.setSelection(SharedPreferenceManager
                .getInstance(getContext()).getBlocksSpinner());
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
                blocks_value = Integer.parseInt(spinner.getSelectedItem().toString());
                if (blocks_value > 6) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());
                    builder.setTitle("Long Walking Distance Reminder!");
                    builder.setMessage("This is a little longer than 1km! ");
                    builder.setIcon(R.drawable.android_warning_icon);
                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                SharedPreferenceManager.getInstance(getContext())
                        .saveBlocks(blocks_value);
                SharedPreferenceManager.getInstance(getContext())
                        .saveBlocksSpinner(spinner.getSelectedItemPosition());
                Fragment fragment1 = new Luggage();
                FragmentTransaction ft1 = null;
                if (getFragmentManager() != null) {
                    ft1 = getFragmentManager().beginTransaction();
                }
                if (ft1 != null) {
                    ft1.replace(R.id.screen_area, fragment1, "Luggage");
                }
                if (ft1 != null) {
                    ft1.commit();
                }
                break;
            case R.id.back3:
                blocks_value = Integer.parseInt(spinner.getSelectedItem().toString());
                SharedPreferenceManager.getInstance(getContext())
                        .saveBlocks(blocks_value);
                SharedPreferenceManager.getInstance(getContext())
                        .saveBlocksSpinner(spinner.getSelectedItemPosition());
                Fragment fragment2 = new Address();
                FragmentTransaction ft2 = null;
                if (getFragmentManager() != null) {
                    ft2 = getFragmentManager().beginTransaction();
                }
                if (ft2 != null) {
                    ft2.replace(R.id.screen_area, fragment2, "Address");
                }
                if (ft2 != null) {
                    ft2.commit();
                }
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
