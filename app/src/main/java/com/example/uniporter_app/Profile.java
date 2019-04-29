package com.example.uniporter_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.uniporter_app.Authentication.MainActivity;
import com.example.uniporter_app.Authentication.Start;
import com.example.uniporter_app.New_Pending_Rides.NewRide;
import com.example.uniporter_app.Storage.SharedPreferenceManager;


public class Profile extends AppCompatActivity {
    String user_email;
    String user_name;
    TextView email_text = (TextView) findViewById(R.id.email);
    TextView name_text = (TextView) findViewById(R.id.name);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);


        user_name = SharedPreferenceManager.getInstance(Profile.this).getName();
        name_text.setText(user_name);


        user_email = SharedPreferenceManager.getInstance(Profile.this).getUserEmail();
        email_text.setText(user_email);

        ImageButton close = findViewById(R.id.profile_x);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, NewRide.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}
