package com.wgplaner.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.wgplaner.R;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.model.Benutzer;

import java.util.List;

public class AufgabeAnzeigenActivity extends AppCompatActivity {
    // Saving this activity to a member variable
    Activity mActivity = this;

    // Components shown in the layout file as member variables
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    ActionBarDrawerToggle mToggle;
    Spinner mDropdownFirst;

    // benutzerDao object to fetch all Benutzer in a particular WG
    BenutzerDao benutzerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);

        // Initialize all components in the view
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.aufgabeAnzeigenToolbar);
        mDropdownFirst = findViewById(R.id.aufgabeZuordnung);

        // Initialize benutzerDao object
        benutzerDao = new BenutzerDao();

        // Toggle the drawer in this activity and set Listener to the layout to identify if toggled
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        // Set Listener to the Items present in the Drawer and start the activity accordingly
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                int itemID = item.getItemId();
                if (itemID == R.id.aufgabeAnzeigenMenu) {
                    closeDrawer(mNavigationView.getRootView());
                } else if (itemID == R.id.wgVerwaltenMenu) {
                    closeDrawer(mNavigationView.getRootView());
                    intent = new Intent(AufgabeAnzeigenActivity.this, WGErstellenAndVerwaltenActivity.class);
                    startActivity(intent);
                } else if (itemID == R.id.userverwaltugMenu) {
                    closeDrawer(mNavigationView.getRootView());
                    intent = new Intent(AufgabeAnzeigenActivity.this, MitgliedHinzufuegenActivity.class);
                    startActivity(intent);
                } else if (itemID == R.id.abmeldenMenu) {
                    DashboardActivity.abmelden(mActivity);
                } else {
                    intent = new Intent(AufgabeAnzeigenActivity.this, NichtImplementiertActivity.class);
                    startActivity(intent);
                    closeDrawer(mNavigationView.getRootView());
                }
                return true;
            }
        });
        showMietbewohnerDropdown();
    }

    // Update the Benutzer list in the dropdown menu on each resume of this activity
    @Override
    public void onResume() {
        super.onResume();
        showMietbewohnerDropdown();
    }

    // Method for showing all Benutzer from the Database in the dropdown
    public void showMietbewohnerDropdown() {
        //Liste von Mietbewohner-Namen im Dropdown Menu.
        List<Benutzer> alleMietbewohner = benutzerDao.allBenutzer();
        ArrayAdapter<Benutzer> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleMietbewohner);
        mDropdownFirst.setAdapter(adapterFirst);
    }

    // Method to close the drawer layout
    public void closeDrawer(View view) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    // Launch AufgabeErstellenActivity
    public void aufgabeErstellenAnzeigen(View view) {
        Intent intent = new Intent(this, AufgabenErstellenActivity.class);
        startActivity(intent);
    }

    // Launch AlleAufgabenActivity and pass some value using key which is to be retrieved in the upcoming activity
    public void filterErgebnis(View view) {
        Benutzer mietbewohner = (Benutzer) mDropdownFirst.getSelectedItem();
        Intent in = new Intent(this, AlleAufgabenActivity.class);
        in.putExtra("ausgewaehlt", mietbewohner.getEmailAdresse());
        startActivity(in);
    }

    // Launch AlleAufgabeActivity where value is an empty string
    public void alleAufgabenAnzeigen(View view) {
        Intent in = new Intent(this, AlleAufgabenActivity.class);
        in.putExtra("ausgewaehlt", "");
        startActivity(in);
    }
}