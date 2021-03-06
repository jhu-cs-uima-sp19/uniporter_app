package com.example.uniporter_app.New_Pending_Rides;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.uniporter_app.DrawerUtil;
import com.example.uniporter_app.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewRide extends AppCompatActivity {

    // Recycler View
    RecyclerView recyclerView;
    TextView emptyView;
    NewRideAdapter adapter;

    //NavDrawer
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        final ProgressDialog progressDialog = new ProgressDialog(NewRide.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading Your Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleView);
        emptyView = findViewById(R.id.empty_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        final NewRideData newRide = new NewRideData();
        newRide.callRideAPI();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.setMessage("Rendering View...");
                        adapter = new NewRideAdapter(NewRide.this, newRide.getRideData());
                        if (adapter.getItemCount() == 0) {
                            recyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                        }
                        List<SectionedRideAdapter.Section> sections =
                                new ArrayList<SectionedRideAdapter.Section>();
                        int pending_rides = 0;
                        try {
                            pending_rides = newRide.past_rides();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        final int final_pending_rides = pending_rides;
                        Log.w("pending ride count", Integer.toString(final_pending_rides));

                        // sections
                        sections.add(new SectionedRideAdapter.Section(0,"Pending Rides"));
                        sections.add(new SectionedRideAdapter.Section(final_pending_rides,"Past Rides History"));

                        //Add your adapter to the sectionAdapter
                        SectionedRideAdapter.Section[] dummy = new SectionedRideAdapter.Section[sections.size()];
                        SectionedRideAdapter sectionedRideAdapter = new
                                SectionedRideAdapter(NewRide.this, R.layout.section, R.id.section_text, adapter);
                        sectionedRideAdapter.setSections(sections.toArray(dummy));

                        recyclerView.setAdapter(sectionedRideAdapter);
                        toolBar = findViewById(R.id.toolbar);
                        toolBar.setTitle("Your Rides & History");
                        setSupportActionBar(toolBar);

                        DrawerUtil.getDrawer(NewRide.this, toolBar);
                        progressDialog.dismiss();
                    }
                }, 2000);
    }
}