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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uniporter_app.Authentication.Login;
import com.example.uniporter_app.DrawerUtil;
import com.example.uniporter_app.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewRide extends AppCompatActivity {

    // Recycler View
    RecyclerView recyclerView;
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

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        final NewRideData newRide = new NewRideData();
        newRide.callRideAPI();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // view adapter
                        adapter = new NewRideAdapter(NewRide.this, newRide.getRideData());
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
                        sections.add(new SectionedRideAdapter.Section(final_pending_rides,"Past Rides"));

                        //Add your adapter to the sectionAdapter
                        SectionedRideAdapter.Section[] dummy = new SectionedRideAdapter.Section[sections.size()];
                        SectionedRideAdapter sectionedRideAdapter = new
                                SectionedRideAdapter(NewRide.this, R.layout.section, R.id.section_text, adapter);
                        sectionedRideAdapter.setSections(sections.toArray(dummy));

                        recyclerView.setAdapter(sectionedRideAdapter);
                        toolBar = findViewById(R.id.toolbar);
                        toolBar.setTitle("Your Pending Rides");
                        setSupportActionBar(toolBar);

                        DrawerUtil.getDrawer(NewRide.this, toolBar);
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