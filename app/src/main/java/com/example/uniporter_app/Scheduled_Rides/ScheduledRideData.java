package com.example.uniporter_app.Scheduled_Rides;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.API.RetrofitClientSharerides;
import com.example.uniporter_app.API_models.RideResponse;
import com.example.uniporter_app.API_models.SharerideResponse;
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
    final List<Integer> weight = new ArrayList<>();

    public void callShareRideAPI() {
        Call<List<SharerideResponse>> call = RetrofitClientSharerides
                .getInstance()
                .getAPI()
                .getShareRides("04/09/19");

        call.enqueue(new Callback<List<SharerideResponse>>() {
            @Override
            public void onResponse(Call<List<SharerideResponse>> call, Response<List<SharerideResponse>> response) {
                for (SharerideResponse ride: response.body()) {
                    meeting_loc.add(ride.getMeeting_loc());
                    Log.w("meeting", ride.getMeeting_loc());
                    members.add(ride.getMemeber());
                    time.add(ride.getTime());
                    weight.add(ride.getWeight());
                    Log.w("response_in", Integer.toString(meeting_loc.size()));
                }
            }

            @Override
            public void onFailure(Call<List<SharerideResponse>> call, Throwable t) {

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

        Log.w("response_out", Integer.toString(meeting_loc.size()));
        for (int i = 0; i < meeting_loc.size(); i++) {

            ScheduledRideInformation current = new ScheduledRideInformation();

            current.location = location[i];
            current.meeting_loc = meeting_loc.get(i);
            current.member = members.get(i);
            current.time = time.get(i);
            current.weight = weight.get(i);

            data.add(current);
        }

        return data;
    }
}
