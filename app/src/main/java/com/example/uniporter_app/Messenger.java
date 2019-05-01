package com.example.uniporter_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Messenger extends AppCompatActivity {
    DatabaseReference myDatabase;
    FirebaseListAdapter adapter;
    String value;
    ListView myTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        value = extras.getString("chatid");
        myDatabase = FirebaseDatabase.getInstance().getReference(value);
        myTexts = findViewById(R.id.list_of_messages);


        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    return;
                }
                getMessage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getMessage() {
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message, myDatabase) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                Log.w("message", "level1");
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Get references to the views of message.xml
                String message_text = model.getMessageText();
                String message_user = model.getMessageUser();
                long message_time = model.getMessageTime();
                // Set their text
                if (message_text != null) {
                    Log.w("message", "level2");
                    messageText.setText(message_text);
                    messageUser.setText(message_user);
                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            message_time));
                }
            }
        };
        myTexts.setAdapter(adapter);
    }

    public void sendMessage(View view) {
        Log.w("message", "sent");
        final EditText myEdits = findViewById(R.id.input);
        String pending_message = myEdits.getText().toString();
        if (myEdits.getText().toString().trim().equals("")) {
            Log.w("message", "empty");
            return;
        }
        Log.w("message", "store");
        myDatabase.child(Long.toString(System.currentTimeMillis())).setValue(new ChatMessage(pending_message, "name"));
        myEdits.setText("");

    }
}

