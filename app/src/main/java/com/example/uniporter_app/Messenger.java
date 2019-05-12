package com.example.uniporter_app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    String name;
    ListView myTexts;
    LinearLayout message_toolbar;
    Button exit_chat;
    LinearLayout message_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        value = extras.getString("chatid");
        name = extras.getString("name");

        myDatabase = FirebaseDatabase.getInstance().getReference(value);
        myTexts = findViewById(R.id.list_of_messages);
        myTexts.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        myTexts.setStackFromBottom(true);

        message_toolbar = findViewById(R.id.mes_toolbar);
        message_toolbar.bringToFront();

        exit_chat = findViewById(R.id.exit_chat);
        exit_chat.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                finish();
            }
        });

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
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                message_box = v.findViewById(R.id.message_box);

                // Get references to the views of message.xml
                String message_text = model.getMessageText();
                String message_user = model.getMessageUser();
                long message_time = model.getMessageTime();

                // Set their text
                if (message_text != null) {
                    messageText.setText(message_text);
                    messageUser.setText(message_user);
                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            message_time));
                    if(message_user.equals(name.toUpperCase())) {
                        messageUser.setGravity(Gravity.RIGHT);
                        messageTime.setGravity(Gravity.RIGHT);
                        messageText.setGravity(Gravity.RIGHT);
                        messageText.setTextColor(Color.WHITE);
                        messageText.setBackgroundTintList(ContextCompat.getColorStateList(Messenger.this, R.color.colorPrimaryDark));
                        message_box.setGravity(Gravity.RIGHT);
                    }
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
        myDatabase.child(Long.toString(System.currentTimeMillis())).setValue(new ChatMessage(pending_message, name.toUpperCase()));
        myEdits.setText("");
    }
}

