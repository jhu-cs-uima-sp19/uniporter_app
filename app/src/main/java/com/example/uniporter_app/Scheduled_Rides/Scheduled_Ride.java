package com.example.uniporter_app.Scheduled_Rides;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.uniporter_app.DrawerUtil;
import com.example.uniporter_app.New_Pending_Rides.NewRide;
import com.example.uniporter_app.Scheduled_Rides.Scheduled_Ride;
import com.example.uniporter_app.Scheduled_Rides.ScheduledRideAdapter;
import com.example.uniporter_app.Scheduled_Rides.ScheduledRideData;
import com.example.uniporter_app.R;

public class Scheduled_Ride extends AppCompatActivity {
    // Recycler View
    RecyclerView recyclerView;
    ScheduledRideAdapter adapter;

    //NavDrawer
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_rides);

        final ProgressDialog progressDialog = new ProgressDialog(Scheduled_Ride.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading Your Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleView2);
        final ScheduledRideData newShareride = new ScheduledRideData();
        newShareride.callShareRideAPI();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        adapter = new ScheduledRideAdapter(Scheduled_Ride.this, newShareride.getRideData());
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
