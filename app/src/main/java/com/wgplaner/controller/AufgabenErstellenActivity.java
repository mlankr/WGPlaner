package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgplaner.R;
import com.wgplaner.dao.AufgabeDao;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.Aufgabe;
import com.wgplaner.model.Benutzer;

import java.util.List;

public class AufgabenErstellenActivity extends AppCompatActivity {
    Spinner mdropdownBenutzer;

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
        bezeichnungInput = findViewById(R.id.bezeichnungField);
        karmapunkteInput = findViewById(R.id.karmapunkteField);
        aufgabeErstellenBestaetigenButton = findViewById(R.id.aufgabeErstellenBestaetigenButton);

        benutzerDao = new BenutzerDao();
        aufgabeDao = new AufgabeDao();
        wgDao = new WGDao();

        //Liste von Mietbewohner-Namen im Dropdown Menu.
        List<Benutzer> alleMietbewohner = benutzerDao.allBenutzer();
        ArrayAdapter<Benutzer> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleMietbewohner);
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
        if (bezeichnung.isEmpty()) {
            message = "Bezeichnung kann nicht leer sein!";
            bezeichnungInput.setError("Pflichtfeld");
        } else {
            Aufgabe aufgabe = new Aufgabe(bezeichnung, punkte, benutzerDao.findBenutzer(mietbewohnerName), wgDao.get());
            aufgabeDao.create(aufgabe);
            message = "Aufgabe erfolgreich erstellt";
            karmapunkteInput.clearFocus();
            bezeichnungInput.clearFocus();
            bezeichnungInput.setText("");
            karmapunkteInput.setText("");
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void zurueckAufgabeAnzeigen(View view) {
        super.onBackPressed();
    }

}