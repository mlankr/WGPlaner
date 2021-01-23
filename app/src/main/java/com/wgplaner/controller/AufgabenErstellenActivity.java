package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgplaner.R;
import com.wgplaner.dao.AufgabeDao;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.Aufgabe;

import java.util.List;

public class AufgabenErstellenActivity extends AppCompatActivity {
    Spinner mdropdownBenutzer;
    LinearLayout merledigtCheckBox;

    BenutzerDao benutzerDao;
    AufgabeDao aufgabeDao;
    WGDao wgDao;

    String bezeichnung;
    int karmapunkte;
    String ausgewaehlteMietbewohner;

    EditText bezeichnungInput;
    EditText karmapunkteInput;
    Button aufgabeErstellenBestaetigenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgaben_erstellen);

        mdropdownBenutzer = findViewById(R.id.dropDownBenutzer);
        merledigtCheckBox = findViewById(R.id.erledigtLayout);
        bezeichnungInput = findViewById(R.id.bezeichnungField);
        karmapunkteInput = findViewById(R.id.karmapunkteField);
        aufgabeErstellenBestaetigenButton = findViewById(R.id.aufgabeErstellenBestaetigenButton);

        benutzerDao = new BenutzerDao();
        aufgabeDao = new AufgabeDao();
        wgDao = new WGDao();

        merledigtCheckBox.setVisibility(View.GONE);

        //Liste von Mietbewohner-Namen im Dropdown Menu.
        List<String> alleMietbewohner = benutzerDao.allBenutzerName();
        Object[] mietbewohner = alleMietbewohner.toArray();
        ArrayAdapter<Object> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mietbewohner);
        mdropdownBenutzer.setAdapter(adapterFirst);


        aufgabeErstellenBestaetigenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezeichnung = bezeichnungInput.getText().toString().trim();
                if (!karmapunkteInput.getText().toString().trim().equals("")) {
                    karmapunkte = Integer.parseInt(karmapunkteInput.getText().toString());
                } else {
                    karmapunkte = 0;
                }
                ausgewaehlteMietbewohner = mdropdownBenutzer.getSelectedItem().toString();
                aufgabeErstellen(bezeichnung, karmapunkte, ausgewaehlteMietbewohner);
            }
        });

    }

    private void aufgabeErstellen(String bezeichnung, int punkte, String mietbewohnerName) {
        String message = "";
        if(bezeichnung.isEmpty()) {
            message = "Bezeichnung kann nicht leer sein!";
            bezeichnungInput.setError("Pflichtfeld");
        } else {
            if (aufgabeDao.findAufgabe(bezeichnung) == null) {
                Aufgabe aufgabe = new Aufgabe(bezeichnung, punkte, benutzerDao.findBenutzer(mietbewohnerName), wgDao.get());
                aufgabeDao.create(aufgabe);
                message = "Aufgabe erfolgreich erstellt";
                karmapunkteInput.clearFocus();
                bezeichnungInput.clearFocus();
                bezeichnungInput.setText("");
                karmapunkteInput.setText("");
            } else {
                message = "Aufgabe schon vorhanden! Bitte erneut versuchen";
            }
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}