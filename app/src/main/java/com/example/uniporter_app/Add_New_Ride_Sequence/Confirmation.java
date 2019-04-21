package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.uniporter_app.API.RetrofitClientPreferences;
import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API_models.PreferenceResponse;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.DrawerUtil;
import com.example.uniporter_app.New_Pending_Rides.NewRide;
import com.example.uniporter_app.New_Pending_Rides.NewRideAdapter;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirmation extends Fragment implements View.OnClickListener {
    String residence;
    String flight_no;
    String airline;
    String user_email;
    int user_id;
    String user_token;
    int luggage;
    int blocks;
    int early;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_confirmation, container, false);
        ImageButton done = myView.findViewById(R.id.done);
        done.setOnClickListener(this);
        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onClick(final View v) {
        final ArrayList<Integer> preferences = new ArrayList<>();
        // preferences.add(new Integer(1));
        final ArrayList<Integer> tags = new ArrayList<>();
        tags.add(new Integer(1));
        switch (v.getId()) {
            case R.id.done:
                user_id = SharedPreferenceManager.getInstance(getContext())
                        .getUserID();
                Log.w("user_id", Integer.toString(user_id));
                user_email = SharedPreferenceManager.getInstance(getContext())
                        .getUserEmal();
                Log.w("user_email", user_email);
                residence = SharedPreferenceManager.getInstance(getContext())
                        .getResidence();
                Log.w("user_residence", residence);
                flight_no = SharedPreferenceManager.getInstance(getContext())
                        .getFlightNo();
                // Log.w("user_flight_no", flight_no);
                airline = SharedPreferenceManager.getInstance(getContext())
                        .getAirline();
                //Log.w("user_airline", airline);
                user_token = SharedPreferenceManager.getInstance(getContext())
                        .getUserToken();
                luggage = SharedPreferenceManager.getInstance(getContext())
                        .getLuggage();
                blocks = SharedPreferenceManager.getInstance(getContext())
                        .getBlocks();
                early = SharedPreferenceManager.getInstance(getContext())
                        .getWaitTime();

                Call<PreferenceResponse> call_pref = RetrofitClientPreferences
                        .getInstance()
                        .getAPI()
                        .addPreference(user_id + user_email, luggage, early, residence, "token " + user_token);

                call_pref.enqueue(new Callback<PreferenceResponse>() {
                    @Override
                    public void onResponse(Call<PreferenceResponse> call, Response<PreferenceResponse> response) {
                        if (response.code() == 201) {
                            PreferenceResponse rep = response.body();
                            Toast.makeText(getContext(), rep.getResidence(), Toast.LENGTH_LONG).show();
                            preferences.add(new Integer(rep.getId()));
                        } else if (response.code() == 400){
                            Toast.makeText(getContext(), "Bad Request", Toast.LENGTH_LONG).show();
                        } else if (response.code() == 500){
                            Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Response Code:" + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PreferenceResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_LONG).show();
                    }
                });

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Call<RideResponse> call_ride = RetrofitClientRides
                                        .getInstance()
                                        .getAPI()
                                        .addRide(user_id , user_email, "to_airport", airline, flight_no, "04/25/19",
                                                preferences, tags , "token " + user_token);

                                call_ride.enqueue(new Callback<RideResponse>() {

                                    @Override
                                    public void onResponse(Call<RideResponse> call, Response<RideResponse> response) {
                                        if (response.code() == 201) {
                                            RideResponse rep = response.body();
                                            Toast.makeText(getContext(), rep.getUser_email(), Toast.LENGTH_LONG).show();
                                        } else if (response.code() == 400){
                                            Toast.makeText(getContext(), "Bad Request", Toast.LENGTH_LONG).show();
                                        } else if (response.code() == 500){
                                            Toast.makeText(getContext(), "Internal Server Error", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getContext(), "Response Code:" + response.code(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<RideResponse> call, Throwable t) {
                                        Toast.makeText(getContext(), "Request Failed", Toast.LENGTH_LONG).show();
                                    }
                                });
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

