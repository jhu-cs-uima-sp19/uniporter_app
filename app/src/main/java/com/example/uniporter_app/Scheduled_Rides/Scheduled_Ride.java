package com.example.uniporter_app.Scheduled_Rides;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.uniporter_app.DrawerUtil;
import com.example.uniporter_app.R;

import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Scheduled_Ride extends AppCompatActivity {
    // Recycler View
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ScheduledRideAdapter adapter;

    //Buttons
    private Button today;
    private Button tomorrow;

    //NavDrawer
    private Toolbar toolBar;

    // Get date
    private TextView shareride_date;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_rides);

        final ProgressDialog progressDialog = new ProgressDialog(Scheduled_Ride.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading Your Data...");
        progressDialog.show();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        String formattedDate = df.format(currentTime);
        grabSharerideDate(progressDialog, formattedDate);

        shareride_date = findViewById(R.id.shareride_date);
        shareride_date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                        Scheduled_Ride.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String month_str = ((month % 13) + 1 < 10) ? "0" + Integer.toString((month % 13) + 1) : Integer.toString((month % 13) + 1);
                String day_str = (day < 10) ? "0" + Integer.toString(day) : Integer.toString(day);
                String year_str = Integer.toString(year).substring(2);
                String date = month_str + "/" + day_str + "/" + year_str;
                shareride_date.setText(date);
            }
        };

        today = findViewById(R.id.today);
        today.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog.setMessage("Loading Your Data...");
                progressDialog.show();
                today.setBackgroundResource(R.color.primary_dark);
                today.setTextColor(getResources().getColor(R.color.white));
                tomorrow.setBackgroundResource(R.color.md_grey_300);
                tomorrow.setTextColor(getResources().getColor(R.color.black));
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                String formattedDate = df.format(currentTime);
                grabSharerideDate(progressDialog, formattedDate);
            }
        });

        tomorrow = findViewById(R.id.tomorrow);
        tomorrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog.setMessage("Loading Your Data...");
                progressDialog.show();
                tomorrow.setBackgroundResource(R.color.primary_dark);
                tomorrow.setTextColor(getResources().getColor(R.color.white));
                today.setBackgroundResource(R.color.md_grey_300);
                today.setTextColor(getResources().getColor(R.color.black));
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_YEAR, 1);
                Date currentTime = c.getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                String formattedDate = df.format(currentTime);
                grabSharerideDate(progressDialog, formattedDate);
            }
        });

        Button get_date = findViewById(R.id.select_date);
        get_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Scheduled_Ride.this);
                builder.setTitle("Reminder");
                builder.setMessage("If you selected a future date after tomorrow. " +
                        "Your shareride is not finalized and may be subject to changes. " +
                        "If you selected a past date, your shareride will not be found if you deleted your ride history on that date.");
                builder.setIcon(R.drawable.android_warning_icon);
                builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        progressDialog.setMessage("Loading Your Data...");
                        progressDialog.show();
                        shareride_date = findViewById(R.id.shareride_date);
                        String date = shareride_date.getText().toString();
                        grabSharerideDate(progressDialog, date);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void grabSharerideDate(final ProgressDialog progressDialog, final String target_date) {
        recyclerView = findViewById(R.id.recycleView2);
        emptyView = findViewById(R.id.empty_view2);
        final ScheduledRideData newShareride = new ScheduledRideData();
        newShareride.callShareRideAPI(target_date);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        adapter = new ScheduledRideAdapter(Scheduled_Ride.this, newShareride.getRideData());
                        if (adapter.getItemCount() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Scheduled_Ride.this)); // Vertical Orientation By Default
                        toolBar = findViewById(R.id.toolbar);
                        toolBar.setTitle("Your Scheduled Sharerides");
                        setSupportActionBar(toolBar);

                        DrawerUtil.getDrawer(Scheduled_Ride.this, toolBar);
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
}
