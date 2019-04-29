package com.example.uniporter_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Messenger extends AppCompatActivity {
    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger);
        /*myDatabase = FirebaseDatabase.getInstance().getReference("Message");
        final TextView myTexts = findViewById(R.id.messageview);

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myTexts.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                myTexts.setText("CANCELLED");
            }
        });*/
    }
    public void sendMessage(View view) {
        final EditText myEdits = findViewById(R.id.messagetext);
        myDatabase.child("name").setValue(myEdits.getText().toString());
        myEdits.setText("");
    }

}
