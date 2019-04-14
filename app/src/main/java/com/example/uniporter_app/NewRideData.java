package com.example.uniporter_app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRideData extends AppCompatActivity{

    public ArrayList<NewRideInformation> getRideData() {

        final List<String> type = new ArrayList<>();
        final List<String> airline = new ArrayList<>();
        final List<String> flight_no = new ArrayList<>();
        final List<String> date = new ArrayList<>();
        final List<String> flight_time = new ArrayList<>();

        ArrayList<NewRideInformation> data = new ArrayList<>();

        Call<List<RideResponse>> call = RetrofitClientRides
                .getInstance()
                .getAPI()
                .getRides("token " + SharedPreferenceManager.getInstance(this).getUserToken());

        call.enqueue(new Callback<List<RideResponse>>() {
            @Override
            public void onResponse(Call<List<RideResponse>> call, Response<List<RideResponse>> response) {
                for (RideResponse ride: response.body()) {
                    Log.w("response", ride.getType());
                    type.add(ride.getType());
                    Log.w("length", Integer.toString(type.size()));
                    airline.add(ride.getAirline());
                    flight_no.add(ride.getFlight_no());
                    date.add(ride.getFlight_time());
                }
            }

            @Override
            public void onFailure(Call<List<RideResponse>> call, Throwable t) {

            }
        });

        int[] location = {
                R.drawable.uniporter_background,
                R.drawable.uniporter_background
        };

        String[] dates = {"fuck, we can't finish this", "gotta code like crazy for the next two days"};

        Log.w("reponse", Integer.toString(type.size()));
        for (int i = 0; i < location.length; i++) {

            NewRideInformation current = new NewRideInformation();
            // tmp
            current.dates = dates[i];
            current.location = location[i];

            data.add(current);
        }

        return data;
    }
}
