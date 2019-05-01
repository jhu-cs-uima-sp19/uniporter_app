package com.example.uniporter_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import java.util.Arrays;
import java.util.Comparator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Messenger extends AppCompatActivity {
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger);
        myDatabase = FirebaseDatabase.getInstance().getReference("pls");
        final TextView myTexts = findViewById(R.id.messageview);

       myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() == 0) {
                    myTexts.setText("");
                    return;
                }
                String[] Messages = dataSnapshot.getValue().toString().split(",");
                myTexts.setText("");
                String[][] finalMsgs = new String[Messages.length][2];
                for(int i = 0; i < Messages.length; i++) {
                    String[] finalMsg = Messages[i].split("=");
                    finalMsgs[i][0] = finalMsg[0];
                    finalMsgs[i][1] = finalMsg[1];
                }
               Arrays.sort(finalMsgs, new Comparator<String[]>() {
                    @Override
                    //arguments to this method represent the arrays to be sorted
                    public int compare(String[] o1, String[] o2) {
                        //get the item ids which are at index 0 of the array
                        String itemIdOne = o1[0];
                        String itemIdTwo = o2[0];
                        // sort on item id
                        return itemIdOne.compareTo(itemIdTwo);
                    }
                });
                for(int i = 0; i < Messages.length; i++) {
                    myTexts.append(finalMsgs[i][1] + "\n");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                myTexts.setText("CANCELLED");
            }
        });
    }
    public void sendMessage(View view) {
        final EditText myEdits = findViewById(R.id.messagetext);
        String pending_message = myEdits.getText().toString();
        if (myEdits.getText().toString().trim().equals("")) {
            return;
        }
        myDatabase.child(Long.toString(System.currentTimeMillis())).setValue(pending_message);
        myEdits.setText("");

    }

}
