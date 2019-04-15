package com.example.uniporter_app.Add_New_Ride_Sequence;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.Authentication.MainActivity;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_confirmation, container, false);
        Button done = myView.findViewById(R.id.done);
        done.setOnClickListener(this);
        return myView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        ArrayList<Integer> preferences = new ArrayList<>();
        preferences.add(new Integer(1));
        ArrayList<Integer> tags = new ArrayList<>();
        tags.add(new Integer(1));
        switch (v.getId()) {
            case R.id.done:
                user_id = SharedPreferenceManager.getInstance(getContext())
                        .getUserID();
                user_email = SharedPreferenceManager.getInstance(getContext())
                        .getUserEmal();
                residence = SharedPreferenceManager.getInstance(getContext())
                        .getResidence();
                flight_no = SharedPreferenceManager.getInstance(getContext())
                        .getFlightNo();
                airline = SharedPreferenceManager.getInstance(getContext())
                        .getAirline();
                user_token = SharedPreferenceManager.getInstance(getContext())
                        .getUserToken();
                Log.w("madhu", user_token);
                Log.w("madhu2", Integer.toString(user_id));
                Log.w("arraylist", Integer.toString(preferences.get(0)));
                Call<RideResponse> call = RetrofitClientRides
                        .getInstance()
                        .getAPI()
                        .addRide(user_id , user_email, "to_airport", airline, flight_no, "04/09/19",
                                preferences, tags, "token " + user_token);
                call.enqueue(new Callback<RideResponse>() {
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
                break;
            default:
                break;
        }
    }
}

