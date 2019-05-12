package com.example.uniporter_app.New_Pending_Rides;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uniporter_app.API.RetrofitClientRides;
import com.example.uniporter_app.AnimationUtil;
import com.example.uniporter_app.R;
import com.example.uniporter_app.Storage.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewRideAdapter extends RecyclerView.Adapter<NewRideAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NewRideInformation> data;
    private LayoutInflater inflater;
    private int previousPosition = 0;

    NewRideAdapter(Context context, ArrayList<NewRideInformation> data) {

        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        View view = inflater.inflate(R.layout.new_ride_list_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int position) {

        String dest = (!Objects.equals(data.get(position).type, "to_airport")) ? "BWI International Airport" : "Johns Hopkins University";

        myViewHolder.type.setText(dest);
        myViewHolder.airline.setText(data.get(position).airline);
        myViewHolder.flight_no.setText(data.get(position).flight_no);
        myViewHolder.date.setText(data.get(position).date);
        myViewHolder.flight_time.setText(data.get(position).flight_time);

        if(position > previousPosition){ // We are scrolling DOWN

            AnimationUtil.animate(myViewHolder, true);

        }else{ // We are scrolling UP

            AnimationUtil.animate(myViewHolder, false);
        }

        previousPosition = position;


        myViewHolder.id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ride Deletion Confirmation");
                builder.setMessage("Would you like to delete this ride?\nThis deletion in irreversible.");
                builder.setIcon(R.drawable.android_warning_icon);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        AlertDialog alert = builder.create();
                        alert.show();
                        String user_token = SharedPreferenceManager.getInstance(context)
                                .getUserToken();
                        Call<ResponseBody> call = RetrofitClientRides
                                .getInstance()
                                .getAPI()
                                .deleteItem(data.get(position).id, "token " + user_token);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                                Intent intent = new Intent(context, NewRide.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView id;
        TextView type;
        TextView airline;
        TextView flight_no;
        TextView date;
        TextView flight_time;

        MyViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.ride_id);
            type = itemView.findViewById(R.id.type_row);
            airline = itemView.findViewById(R.id.airline_row);
            flight_no = itemView.findViewById(R.id.flight_no_row);
            date = itemView.findViewById(R.id.date_row);
            flight_time = itemView.findViewById(R.id.flight_time_row);

        }
    }

    @Override
    public final int getItemViewType(int position) {
        return data.get(position).getViewType();
    }
}
