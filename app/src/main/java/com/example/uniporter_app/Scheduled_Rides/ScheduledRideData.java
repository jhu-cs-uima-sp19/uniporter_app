package com.example.uniporter_app.Scheduled_Rides;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.New_Pending_Rides.NewRideInformation;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduledRideData extends AppCompatActivity {
    final List<String> meeting_loc = new ArrayList<>();
    final List<List<String>> members = new ArrayList<>();
    final List<String> time = new ArrayList<>();
    final List<String> weight = new ArrayList<>();

    public void callRideAPI() {
        Call<List<RideResponse>> call = RetrofitClientRides
                .getInstance()
                .getAPI()
                .getRides("token " + SharedPreferenceManager.getInstance(this).getUserToken());

        call.enqueue(new Callback<List<RideResponse>>() {
            @Override
            public void onResponse(Call<List<RideResponse>> call, Response<List<RideResponse>> response) {
                for (RideResponse ride: response.body()) {
                }
            }

            @Override
            public void onFailure(Call<List<RideResponse>> call, Throwable t) {

            }
        });
    }

    public ArrayList<ScheduledRideInformation> getRideData() {

        ArrayList<ScheduledRideInformation> data = new ArrayList<>();

        int[] location = {
                R.drawable.uniporter_background,
                R.drawable.uniporter_background
        };

        String[] dates = {"fuck, we can't finish this", "gotta code like crazy for the next two days"};

        Log.w("response", Integer.toString(meeting_loc.size()));
        for (int i = 0; i < meeting_loc.size(); i++) {

            ScheduledRideInformation current = new ScheduledRideInformation();

            data.add(current);
        }

        return data;
    }
}
