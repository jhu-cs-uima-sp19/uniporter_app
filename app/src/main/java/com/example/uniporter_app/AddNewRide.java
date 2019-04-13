package com.example.uniporter_app;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.support.v4.app.Fragment;
public class AddNewRide extends AppCompatActivity {

    //front buttons
    ImageButton _front1;
    ImageButton _front2;
    ImageButton _front3;
    ImageButton _front4;
    ImageButton _front5;

    //back buttons
    ImageButton _back2;
    ImageButton _back3;
    ImageButton _back4;
    ImageButton _back5;


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


        //front buttons
        _front1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Fragment fragment = new FlightInfo();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.screen_area, fragment);
            ft.commit();

            }
        });

    }
}
