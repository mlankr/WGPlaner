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
import com.wgplaner.dao.BenutzerDao;

import java.util.List;

public class AufgabeActivity extends AppCompatActivity {

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    ActionBarDrawerToggle mToggle;
//    ImageView mCloseDrawer;

    TextInputLayout mSearchView;
    Spinner mDropdownFirst;

    BenutzerDao benutzerDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.aufgabeAnzeigenToolbar);
        mDropdownFirst = findViewById(R.id.aufgabeZuordnung);

        benutzerDao = new BenutzerDao();

//        mCloseDrawer = findViewById(R.id.closeDrawer);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //Liste von Mietbewohner-Namen im Dropdown Menu.
        List<String> alleMietbewohner = benutzerDao.allBenutzerName();
        Object[] name = alleMietbewohner.toArray();
        ArrayAdapter<Object> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, name);
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
        String mietbewohner = mDropdownFirst.getSelectedItem().toString();
        Intent in = new Intent(this, ErgebnisActivity.class);
        in.putExtra("ausgewaehlt", mietbewohner);
        startActivity(in);
    }

    public void alleAufgabenAnzeigen(View view) {
        Intent in = new Intent(this, AlleAufgabenActivity.class);
        startActivity(in);
    }

}