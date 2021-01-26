package com.wgplaner.controller;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.wgplaner.R;

/**
 * This Activity is planned as a common Drawer Layout for all the Activities
 * TODO: Work in progress
 */
public class DrawerActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    public NavigationView navigationView;

    protected void onCreateDrawer() {
        // R.id.drawer_layout should be in every activity with exactly the same id.
        drawerLayout = findViewById(R.id.drawerLayoutDrawer);
        navigationView = findViewById(R.id.nav_viewDrawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}