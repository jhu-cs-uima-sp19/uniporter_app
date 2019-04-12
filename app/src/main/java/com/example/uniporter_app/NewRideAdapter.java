package com.example.uniporter_app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NewRideAdapter extends RecyclerView.Adapter<NewRideAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<NewRideInformation> data;

    private LayoutInflater inflater;

    private int previousPosition = 0;

    public NewRideAdapter(Context context, ArrayList<NewRideInformation> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.new_ride_list_item_row, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {

        myViewHolder.textview.setText(data.get(position).date);
        myViewHolder.imageView.setImageResource(data.get(position).location);

        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(myViewHolder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(myViewHolder, false);


        }

        previousPosition = position;


        final int currentPosition = position;
        final NewRideInformation infoData = data.get(position);

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

        TextView textview;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textview = (TextView) itemView.findViewById(R.id.txv_row);
            imageView = (ImageView) itemView.findViewById(R.id.img_row);

        }
    }

    // This removes the data from our Dataset and Updates the Recycler View.
    private void removeItem(NewRideInformation infoData) {

        int currPosition = data.indexOf(infoData);
        data.remove(currPosition);
        notifyItemRemoved(currPosition);
    }

    // This method adds(duplicates) a Object (item ) to our Data set as well as Recycler View.
    private void addItem(int position, NewRideInformation infoData) {

        data.add(position, infoData);
        notifyItemInserted(position);
    }
}
