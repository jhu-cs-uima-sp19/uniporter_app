package com.example.uniporter_app.New_Pending_Rides;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRideData extends AppCompatActivity{

    final List<String> type = new ArrayList<>();
    final List<String> airline = new ArrayList<>();
    final List<String> flight_no = new ArrayList<>();
    final List<String> date = new ArrayList<>();
    final List<String> flight_time = new ArrayList<>();

    public void callRideAPI() {
        Call<List<RideResponse>> call = RetrofitClientRides
                .getInstance()
                .getAPI()
                .getRides("token " + SharedPreferenceManager.getInstance(this).getUserToken());

        call.enqueue(new Callback<List<RideResponse>>() {
            @Override
            public void onResponse(Call<List<RideResponse>> call, Response<List<RideResponse>> response) {
                for (RideResponse ride: response.body()) {
                    type.add(ride.getType());
                    airline.add(ride.getAirline());
                    flight_no.add(ride.getFlight_no());
                    date.add(ride.getDate());
                    flight_time.add(ride.getFlight_time());
                }
            }

            @Override
            public void onFailure(Call<List<RideResponse>> call, Throwable t) {

            }
        });
    }

    public ArrayList<NewRideInformation> getRideData() {

        ArrayList<NewRideInformation> data = new ArrayList<>();

        int[] location = {
                R.drawable.uniporter_background,
                R.drawable.uniporter_background
        };

        Log.w("response", Integer.toString(type.size()));
        for (int i = 0; i < type.size(); i++) {

            NewRideInformation current = new NewRideInformation();

            current.location = location[i];
            current.type = type.get(i);
            current.airline = airline.get(i);
            current.flight_no  =flight_no.get(i);
            current.date = date.get(i);
            current.flight_time = flight_time.get(i);

            data.add(current);
        }

        return data;
    }
}
