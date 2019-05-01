package com.example.uniporter_app.Scheduled_Rides;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.uniporter_app.DrawerUtil;
import com.example.uniporter_app.R;

import java.util.Calendar;
import java.util.Objects;

public class Scheduled_Ride extends AppCompatActivity {
    // Recycler View
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ScheduledRideAdapter adapter;

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
        grabSharerideDate(progressDialog, "04/25/19");

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

        Button get_date = findViewById(R.id.select_date);
        get_date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog.setMessage("Loading Your Data...");
                progressDialog.show();
                shareride_date = findViewById(R.id.shareride_date);
                String date = shareride_date.getText().toString();
                grabSharerideDate(progressDialog, date);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.linearViewHorizontal:
                LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(this); // (Context context)
                mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(mLinearLayoutManagerHorizontal);
                break;

            case R.id.linearViewVertical:
                LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
                mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                break;
            case R.id.gridView:
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3); // (Context context, int spanCount)
                recyclerView.setLayoutManager(mGridLayoutManager);
                break;
            case R.id.staggeredViewHorizontal:
                StaggeredGridLayoutManager mStaggeredHorizontalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL); // (int spanCount, int orientation)
                recyclerView.setLayoutManager(mStaggeredHorizontalLayoutManager);
                break;
            case R.id.staggeredViewVertical:
                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
