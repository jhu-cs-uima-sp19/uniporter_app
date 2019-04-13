package com.example.uniporter_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewRide extends AppCompatActivity {

    Button _front1;
    Button _front2;
    Button _front3;
    Button _front4;
    Button _front5;

    Button _back2;
    Button _back3;
    Button _back4;
    Button _back5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);

        // bind view elements
        _front1 = findViewById(R.id.front1);
        _front2 = findViewById(R.id.front2);
        _front3 = findViewById(R.id.front3);
        _front4 = findViewById(R.id.front4);
        _front5 = findViewById(R.id.front5);

        _back2 = findViewById(R.id.back2);
        _back3 = findViewById(R.id.back3);
        _back4 = findViewById(R.id.back4);
        _back5 = findViewById(R.id.back5);


        _front1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_address);
            }
        });

    }
}
