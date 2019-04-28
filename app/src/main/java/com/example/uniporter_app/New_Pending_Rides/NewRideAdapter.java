package com.example.uniporter_app.New_Pending_Rides;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.AnimationUtil;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        String dest = (data.get(position).type != "to_airport") ? "BWI International Airport" : "Johns Hopkins University";

        myViewHolder.id.setText("delete");
        myViewHolder.type.setText(dest);
        myViewHolder.airline.setText(data.get(position).date);
        myViewHolder.flight_no.setText(data.get(position).flight_no);
        myViewHolder.date.setText(data.get(position).date);
        myViewHolder.flight_time.setText(data.get(position).flight_time);

        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(myViewHolder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(myViewHolder, false);
        }

        previousPosition = position;


        final int currentPosition = position;
        final NewRideInformation infoData = data.get(position);

        myViewHolder.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_token = SharedPreferenceManager.getInstance(context)
                        .getUserToken();
                Call<ResponseBody> call = RetrofitClientRides
                        .getInstance()
                        .getAPI()
                        .deleteItem(data.get(position).id, "token " + user_token);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Deleted Ride " + Integer.toString(data.get(position).id), Toast.LENGTH_SHORT).show();
                        removeItem(data.get(position));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        /*
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
        */


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView type;
        TextView airline;
        TextView flight_no;
        TextView date;
        TextView flight_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.ride_id);
            type = (TextView) itemView.findViewById(R.id.type_row);
            airline = (TextView) itemView.findViewById(R.id.airline_row);
            flight_no = (TextView) itemView.findViewById(R.id.flight_no_row);
            date = (TextView) itemView.findViewById(R.id.date_row);
            flight_time = (TextView) itemView.findViewById(R.id.flight_time_row);

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

    @Override
    public final int getItemViewType(int position) {
        return data.get(position).getViewType();
    }
}
