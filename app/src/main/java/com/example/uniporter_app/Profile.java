package com.example.uniporter_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.uniporter_app.Storage.SharedPreferenceManager;


public class Profile extends AppCompatActivity {
    String user_email;
    String user_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView name_text = (TextView) findViewById(R.id.name);
        user_name = SharedPreferenceManager.getInstance(Profile.this).getName();
        name_text.setText(user_name);

        TextView email_text = (TextView) findViewById(R.id.email);
        user_email = SharedPreferenceManager.getInstance(Profile.this).getUserEmal();
        email_text.setText(user_email);

    }
}
