package com.wgplaner.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wgplaner.R;
import com.wgplaner.db.DBHelper;


import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        DBHelper.initDBService(getApplicationContext());

//        WG wg = new WG("Studentenwohnheim");
//        WGDao wgDao = new WGDao();
//        wgDao.erstellen(wg);

//        WG wg = wgDao.getByName("Studentenwohnheim");
//        Benutzer benutzer = new Benutzer("Max", "Muster", "max@test.com", "xyz", true, wg, 5 );
//        BenutzerDao benutzerDao = new BenutzerDao();
//        benutzerDao.create(benutzer);

//        System.out.println(BenutzerDao.get(1));

//        List<Benutzer> list = benutzerDao.alleBenutzer();
//        System.out.println("TEST: " + list);

//        System.out.println("TEST: " + wgDao.getByName("Studentenwohnheim").getWgMitglieder());
    }

    public void aufgabeAnzeigen(View view) {
        Intent intent = new Intent(this, AufgabeActivity.class);
        startActivity(intent);
    }

    public void wgErstellen(View view) {
        Intent intent = new Intent(this, WGErstellenActivity.class);
        startActivity(intent);
    }

    public void userVerwaltung(View view) {
        Intent intent = new Intent(this, MitgliedHinzufuegenActivity.class);
        startActivity(intent);
    }

    public void abmelden(View view) {
        System.exit(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.closeDBService();
    }
}
