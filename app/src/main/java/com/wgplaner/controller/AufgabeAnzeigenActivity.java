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
import com.wgplaner.R;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.model.Benutzer;

import java.util.List;

public class AufgabeAnzeigenActivity extends AppCompatActivity {

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;
    ActionBarDrawerToggle mToggle;
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

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        showMietbewohnerDropdown();
    }

    @Override
    public void onResume() {
        super.onResume();
        showMietbewohnerDropdown();
    }

    public void showMietbewohnerDropdown() {
        //Liste von Mietbewohner-Namen im Dropdown Menu.
        List<Benutzer> alleMietbewohner = benutzerDao.allBenutzer();
        ArrayAdapter<Benutzer> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleMietbewohner);
        mDropdownFirst.setAdapter(adapterFirst);
    }

    public void closeDrawer(View view) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void aufgabeErstellenAnzeigen(View view) {
        Intent intent = new Intent(this, AufgabenErstellenActivity.class);
        startActivity(intent);
    }

    public void ergebnisAnzeigen(View view) {
        Benutzer mietbewohner = (Benutzer) mDropdownFirst.getSelectedItem();
        Intent in = new Intent(this, AlleAufgabenActivity.class);
        in.putExtra("ausgewaehlt", mietbewohner.getEmailAdresse());
        startActivity(in);
    }

    public void alleAufgabenAnzeigen(View view) {
        Intent in = new Intent(this, AlleAufgabenActivity.class);
        in.putExtra("ausgewaehlt", "");
        startActivity(in);
    }

}