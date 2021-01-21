package com.wgplaner.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wgplaner.R;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.db.DBHelper;
import com.wgplaner.model.Benutzer;
import com.wgplaner.model.WG;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.closeDBService();
    }
}
