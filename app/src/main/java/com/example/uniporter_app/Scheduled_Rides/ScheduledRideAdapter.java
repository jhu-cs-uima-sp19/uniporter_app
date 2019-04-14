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

        View view = inflater.inflate(R.layout.new_ride_list_item_row, parent, false);

       ScheduledRideAdapter.MyViewHolder holder = new ScheduledRideAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

        myViewHolder.imageView.setImageResource(data.get(position).location);

        myViewHolder.meeting_loc.setText(data.get(position).meeting_loc);
        myViewHolder.time.setText(data.get(position).time);
        myViewHolder.weight.setText(data.get(position).weight);

        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(myViewHolder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(myViewHolder, false);
        }

        previousPosition = position;


        final int currentPosition = position;
        final ScheduledRideInformation infoData = data.get(position);

        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "OnClick Called at position " + position, Toast.LENGTH_SHORT).show();
                addItem(currentPosition, infoData);
            }
        });

        myViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(context, "OnLongClick Called at position " + position, Toast.LENGTH_SHORT).show();

                removeItem(infoData);

                return true;
            }


        });


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

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img_row);

            meeting_loc = (TextView) itemView.findViewById(R.id.type_row);
            time = (TextView) itemView.findViewById(R.id.time);
            weight = (TextView) itemView.findViewById(R.id.weight);

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
