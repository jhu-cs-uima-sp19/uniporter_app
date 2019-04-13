package com.example.uniporter_app;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class AddNewRide extends AppCompatActivity {

    ImageButton _front1;
    ImageButton _front2;
    ImageButton _front3;
    ImageButton _front4;
    ImageButton _front5;

    ImageButton _back2;
    ImageButton _back3;
    ImageButton _back4;
    ImageButton _back5;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);

        // bind view elements
        _front1 = (ImageButton) findViewById(R.id.front1);
        _front2 = (ImageButton) findViewById(R.id.front2);
        _front3 = (ImageButton) findViewById(R.id.front3);
        _front4 = (ImageButton) findViewById(R.id.front4);
        _front5 = (ImageButton) findViewById(R.id.front5);

        _back2 = (ImageButton) findViewById(R.id.back2);
        _back3 = (ImageButton) findViewById(R.id.back3);
        _back4 = (ImageButton) findViewById(R.id.back4);
        _back5 = (ImageButton) findViewById(R.id.back5);


        //front buttons
        _front1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_address);
            }
        });
        _front2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_address);
            }
        });
        _front3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_blocks);
            }
        });
        _front4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_luggage);
            }
        });
        _front5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_early);
            }
        });

        //back buttons
        _back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_flight_info);
            }
        });
        _back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_address);
            }
        });
        _back4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_blocks);
            }
        });
        _back5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_luggage);
            }
        });
    }
}
