package com.example.uniporter_app.Scheduled_Rides;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniporter_app.AnimationUtil;
import com.example.uniporter_app.New_Pending_Rides.NewRideAdapter;
import com.example.uniporter_app.New_Pending_Rides.NewRideInformation;
import com.example.uniporter_app.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduledRideAdapter extends RecyclerView.Adapter<ScheduledRideAdapter.MyViewHolder> {
    private Context context;

    private ArrayList<ScheduledRideInformation> data;

    private LayoutInflater inflater;

    private int previousPosition = 0;

    public ScheduledRideAdapter(Context context, ArrayList<ScheduledRideInformation> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ScheduledRideAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.scheduled_rides_list_item_row, parent, false);

        ScheduledRideAdapter.MyViewHolder holder = new ScheduledRideAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

        myViewHolder.schedule_date.setText(data.get(position).schedule_date);
        myViewHolder.meeting_loc.setText(data.get(position).meeting_loc);
        myViewHolder.time.setText( data.get(position).time);
        myViewHolder.weight.setText(Integer.toString(data.get(position).weight * 10) + " kg");

        List<String> group = data.get(position).member;
        String concatenatedNames = "";
        for (int i = 0; i < group.size(); i++) {
            concatenatedNames += (group.get(i) + "\n");
        }

        myViewHolder.members.setText(concatenatedNames);

        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(myViewHolder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(myViewHolder, false);
        }

        previousPosition = position;


        final int currentPosition = position;
        final ScheduledRideInformation infoData = data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        TextView meeting_loc;
        TextView time;
        TextView weight;
        TextView schedule_date;
        TextView members;

        public MyViewHolder(View itemView) {
            super(itemView);
            meeting_loc = (TextView) itemView.findViewById(R.id.meeting_loc_row);
            time = (TextView) itemView.findViewById(R.id.time_row);
            weight = (TextView) itemView.findViewById(R.id.weight_row);
            schedule_date = (TextView) itemView.findViewById(R.id.scheduled_date_row);
            members = (TextView) itemView.findViewById(R.id.members_row);
        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(ScheduledRideInformation infoData) {

        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, ScheduledRideInformation infoData) {

        data.add(position, infoData);
        notifyItemInserted(position);
    }
}
