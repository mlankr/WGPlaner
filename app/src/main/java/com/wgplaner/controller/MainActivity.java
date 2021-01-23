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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    WGDao wgDao;
    BenutzerDao benutzerDao;
    WG wg;
    Benutzer benutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.initDBService(getApplicationContext());

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);

        wgDao = new WGDao();
        benutzerDao = new BenutzerDao();
        onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.closeDBService();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button5) {
            Intent intent = new Intent(this, WGErstellenActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button8) {
            finish();
        } else {
            if (wg != null) {
                if (v.getId() == R.id.button1) {
                    if (benutzerDao.allBenutzer().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Bitte unter Userverwaltung nun Mitglied an Ihrer WG hinzufügen", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent aufgabeActivity = new Intent(this, AufgabeActivity.class);
                        startActivity(aufgabeActivity);
                    }
                } else if (v.getId() == R.id.button7) {
                    Intent mitgliedHinzufuegenActivity = new Intent(this, MitgliedHinzufuegenActivity.class);
                    startActivity(mitgliedHinzufuegenActivity);
                } else {
                    Intent mitgliedHinzufuegenActivity = new Intent(this, NichtImplementiertActivity.class);
                    startActivity(mitgliedHinzufuegenActivity);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bitte erst WG erstellen", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onResume() {
        wg = wgDao.get();
        super.onResume();
        if (wg != null) {
            Button wgErstellenButton = findViewById(R.id.button5);
            wgErstellenButton.setText(R.string.wgVerwalten);

        }

    }

}
