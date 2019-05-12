package com.example.uniporter_app.Scheduled_Rides;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.example.uniporter_app.Messenger;
import com.example.uniporter_app.AnimationUtil;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class ScheduledRideAdapter extends RecyclerView.Adapter<ScheduledRideAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<ScheduledRideInformation> data;

    private LayoutInflater inflater;

    private int previousPosition = 0;

    ScheduledRideAdapter(Context context, ArrayList<ScheduledRideInformation> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ScheduledRideAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.scheduled_rides_list_item_row, parent, false);

        return new MyViewHolder(view);
    }

    private String chatRoomIdHash(int position, String meeting_loc) {
        List<String> group = data.get(position).member;
        StringBuilder src = new StringBuilder();
        for (int i = 0; i < group.size(); i++) {
            String[] name = (group.get(i)).split("@");
            src.append(name[0]);
        }
        src.append(meeting_loc);
        return src.toString();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int position) {

        myViewHolder.schedule_date.setText(data.get(position).schedule_date);
        myViewHolder.meeting_loc.setText(data.get(position).meeting_loc);
        myViewHolder.time.setText( data.get(position).time);
        myViewHolder.weight.setText(Integer.toString(data.get(position).weight * 10) + " kg");

        List<String> group = data.get(position).member;
        StringBuilder concatenatedNames = new StringBuilder();
        for (int i = 0; i < group.size(); i++) {
            concatenatedNames.append(group.get(i)).append("\n");
        }

        myViewHolder.members.setText(concatenatedNames.toString());

        myViewHolder.enter_chatroom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String chatroom_id = chatRoomIdHash(position, data.get(position).meeting_loc);
                Intent intent = new Intent(context, Messenger.class);
                intent.putExtra("chatid", chatroom_id);
                String username = SharedPreferenceManager.getInstance(context)
                        .getName();
                intent.putExtra("name", username);
                context.startActivity(intent);
            }
        });

        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(myViewHolder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(myViewHolder, false);
        }

        previousPosition = position;


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView meeting_loc;
        TextView time;
        TextView weight;
        TextView schedule_date;
        TextView members;
        ImageView enter_chatroom;

        MyViewHolder(View itemView) {
            super(itemView);
            meeting_loc = itemView.findViewById(R.id.meeting_loc_row);
            time = itemView.findViewById(R.id.time_row);
            weight = itemView.findViewById(R.id.weight_row);
            schedule_date = itemView.findViewById(R.id.scheduled_date_row);
            members = itemView.findViewById(R.id.members_row);
            enter_chatroom = itemView.findViewById(R.id.enter_chat_room);
        }
    }

}
