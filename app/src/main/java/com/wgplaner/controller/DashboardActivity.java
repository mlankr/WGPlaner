package com.wgplaner.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wgplaner.R;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.db.DBHelper;
import com.wgplaner.model.Benutzer;
import com.wgplaner.model.WG;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    WGDao wgDao;
    BenutzerDao benutzerDao;
    WG wg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        DBHelper.initDBService(getApplicationContext());

        findViewById(R.id.aufgabeAnzeigenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.offeneAufgabenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.karmastatistikButtonDashboard).setOnClickListener(this);
        findViewById(R.id.einkauflisteAnzeigenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.wgErstellenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.kontoLoeschenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.userverwaltugButtonDashboard).setOnClickListener(this);
        findViewById(R.id.abmeldenButtonDashboard).setOnClickListener(this);

        wgDao = new WGDao();
        benutzerDao = new BenutzerDao();
    }

    @Override
    public void onResume() {
        super.onResume();
        wg = wgDao.get();
        if (wg != null) {
            Button wgErstellenButton = findViewById(R.id.wgErstellenButtonDashboard);
            wgErstellenButton.setText(R.string.wgVerwalten);

            if (benutzerDao.getByEmail("admin@wg.com") == null) {
                benutzerDao.create(new Benutzer("admin", "user", "admin@wg.com", "*****", true, wgDao.get(), 0));
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wgErstellenButtonDashboard) {
            Intent intent = new Intent(this, WGErstellenAndVerwaltenActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.abmeldenButtonDashboard) {
            finish();
        } else {
            if (wg != null) {
                if (v.getId() == R.id.aufgabeAnzeigenButtonDashboard) {
                    Intent aufgabeActivity = new Intent(this, AufgabeAnzeigenActivity.class);
                    startActivity(aufgabeActivity);
                } else if (v.getId() == R.id.userverwaltugButtonDashboard) {
                    Intent userverwalten = new Intent(this, MitgliedHinzufuegenActivity.class);
                    startActivity(userverwalten);
                } else {
                    Intent nichtImplementiert = new Intent(this, NichtImplementiertActivity.class);
                    startActivity(nichtImplementiert);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bitte erst WG erstellen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.closeDBService();
    }


}
