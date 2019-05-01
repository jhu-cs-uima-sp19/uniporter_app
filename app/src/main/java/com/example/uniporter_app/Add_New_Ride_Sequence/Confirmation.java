package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniporter_app.API.RetrofitClientPreferences;
import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API_models.PreferenceResponse;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirmation extends Fragment implements View.OnClickListener {
    ImageButton x_confirm;
    Button done;
    Button back;

    String residence;
    TextView residence_confirm;
    String flight_no;
    String airline;
    String user_email;

    int user_id;
    String user_token;
    int luggage;
    TextView luggage_confirm;
    int blocks;
    TextView blocks_confirm;
    int early;
    TextView early_confirm;
    String departure_date;
    TextView departure_date_confirm;
    String departure_time;
    TextView departure_time_confirm;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_confirmation, container, false);

        x_confirm = myView.findViewById(R.id.x_confirm);
        x_confirm.setOnClickListener(this);

        done = myView.findViewById(R.id.done);
        done.setOnClickListener(this);

        back = myView.findViewById(R.id.confirm_back);
        back.setOnClickListener(this);

        user_id = SharedPreferenceManager.getInstance(getContext())
                .getUserID();
        user_email = SharedPreferenceManager.getInstance(getContext())
                .getUserEmal();
        residence = SharedPreferenceManager.getInstance(getContext())
                .getResidence();
        residence_confirm = myView.findViewById(R.id.confirm_residence);
        residence_confirm.setText(residence);
        flight_no = SharedPreferenceManager.getInstance(getContext())
                .getFlightNo();
        airline = SharedPreferenceManager.getInstance(getContext())
                .getAirline();
        user_token = SharedPreferenceManager.getInstance(getContext())
                .getUserToken();
        luggage = SharedPreferenceManager.getInstance(getContext())
                .getLuggage();
        luggage_confirm = myView.findViewById(R.id.confirm_luggage);
        luggage_confirm.setText(Integer.toString((luggage / 2) * 20) + " kg");
        blocks = SharedPreferenceManager.getInstance(getContext())
                .getBlocks();
        blocks_confirm = myView.findViewById(R.id.confirm_max_blocks);
        blocks_confirm.setText(Integer.toString(blocks) + " blocks");
        early = SharedPreferenceManager.getInstance(getContext())
                .getWaitTime();
        early_confirm = myView.findViewById(R.id.confirm_max_early);
        early_confirm.setText(Integer.toString(early) + " hrs");
        departure_date = SharedPreferenceManager.getInstance(getContext())
                .getFlightDate();
        departure_date_confirm = myView.findViewById(R.id.confirm_depart_date);
        departure_date_confirm.setText(departure_date);
        departure_time = SharedPreferenceManager.getInstance(getContext())
                .getFlightTime();
        departure_time_confirm = myView.findViewById(R.id.confirm_depart_time);
        departure_time_confirm.setText(departure_time);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(final View v) {
        final ArrayList<Integer> preferences = new ArrayList<>();
        final ArrayList<Integer> tags = new ArrayList<>();
        tags.add(1);
        switch (v.getId()) {
            case R.id.x_confirm:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                v.getContext().startActivity(intent);
                break;
            case R.id.confirm_back:
                Fragment fragment = new Early();
                FragmentTransaction ft = null;
                if (getFragmentManager() != null) {
                    ft = getFragmentManager().beginTransaction();
                }
                if (ft != null) {
                    ft.replace(R.id.screen_area, fragment, "Early");
                }
                if (ft != null) {
                    ft.commit();
                }
                break;
            case R.id.done:
                final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Sending your information...");
                progressDialog.show();
                Call<PreferenceResponse> call_pref = RetrofitClientPreferences
                        .getInstance()
                        .getAPI()
                        .addPreference(user_id + user_email, luggage, early, residence, "token " + user_token);

                call_pref.enqueue(new Callback<PreferenceResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PreferenceResponse> call, @NonNull Response<PreferenceResponse> response) {
                        if (response.code() == 201) {
                            PreferenceResponse rep = response.body();
                            if (rep != null) {
                                Toast.makeText(getContext(), rep.getResidence(), Toast.LENGTH_LONG).show();
                            }
                            if (rep != null) {
                                preferences.add(rep.getId());
                            }
                        } else if (response.code() == 400){
                            Toast.makeText(getContext(), "Bad Request", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 500){
                            Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Response Code:" + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<PreferenceResponse> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_LONG).show();
                    }
                });

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Call<RideResponse> call_ride = RetrofitClientRides
                                        .getInstance()
                                        .getAPI()
                                        .addRide(user_id , user_email, "to_airport", airline, flight_no, departure_date, departure_time,
                                                preferences, tags , "token " + user_token);

                                call_ride.enqueue(new Callback<RideResponse>() {

                                    @Override
                                    public void onResponse(@NonNull Call<RideResponse> call, @NonNull Response<RideResponse> response) {
                                        if (response.code() == 201) {
                                            RideResponse rep = response.body();
                                            if (rep != null) {
                                                Toast.makeText(getContext(), rep.getUser_email(), Toast.LENGTH_LONG).show();
                                            }
                                        } else if (response.code() == 400){
                                            Toast.makeText(getContext(), "Bad Request", Toast.LENGTH_LONG).show();
                                        } else if (response.code() == 500){
                                            Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getContext(), "Response Code:" + response.code(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<RideResponse> call, @NonNull Throwable t) {
                                        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                                progressDialog.dismiss();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                v.getContext().startActivity(intent);
                            }
                        }, 3000);
                break;
            default:
                break;
        }
    }
}

