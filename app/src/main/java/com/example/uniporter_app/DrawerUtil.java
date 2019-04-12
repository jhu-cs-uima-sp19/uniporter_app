package com.example.uniporter_app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class DrawerUtil {

    public static void getDrawer(final Activity activity, Toolbar toolbar) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem drawerEmptyItem= new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        PrimaryDrawerItem drawerItemManagePlayers = new PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.new_ride).withIcon(R.drawable.ic__car_24dp);
        PrimaryDrawerItem drawerItemManagePlayersTournaments = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.scheduled_rides).withIcon(R.drawable.ic_schedule_24dp);

        //create the drawer and remember the `Drawer` result object
        DrawerBuilder drawerBuilder = new DrawerBuilder();
        drawerBuilder.withActivity(activity);
        drawerBuilder.withToolbar(toolbar);
        drawerBuilder.withActionBarDrawerToggle(true);
        drawerBuilder.withActionBarDrawerToggleAnimated(true);
        drawerBuilder.withCloseOnClick(true);
        drawerBuilder.withSelectedItem(-1);
        drawerBuilder.addDrawerItems(
                drawerEmptyItem, drawerEmptyItem, drawerEmptyItem,
                drawerItemManagePlayers,
                drawerItemManagePlayersTournaments,
                new DividerDrawerItem()
        );

        drawerBuilder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                if (drawerItem.getIdentifier() == 2 && !(activity instanceof MainActivity)) {
                    // load screen
                    Intent intent = new Intent(activity, MainActivity.class);
                    view.getContext().startActivity(intent);
                }
                return true;
            }
        });
        Drawer result = drawerBuilder.build();
    }
}