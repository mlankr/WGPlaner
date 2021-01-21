package com.wgplaner.controller;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.wgplaner.R;

public class AufgabeActivity extends AppCompatActivity {

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    ActionBarDrawerToggle mToggle;
//    ImageView mCloseDrawer;

    TextInputLayout mSearchView;
    Spinner mDropdownFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.aufgabeAnzeigenToolbar);
        mDropdownFirst = findViewById(R.id.aufgabeZuordnung);

//        mCloseDrawer = findViewById(R.id.closeDrawer);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //create a list of items for the spinner.
        String[] items = new String[]{"Max", "Peter", "Sara"};
        ArrayAdapter<String> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mDropdownFirst.setAdapter(adapterFirst);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void aufgabeErstellenAnzeigen(View view) {
        Intent intent = new Intent(this, AufgabenErstellenActivity.class);
        startActivity(intent);
    }

    public void ergebnisAnzeigen(View view) {
        Intent intent = new Intent(this, ErgebnisActivity.class);
        startActivity(intent);
    }

}