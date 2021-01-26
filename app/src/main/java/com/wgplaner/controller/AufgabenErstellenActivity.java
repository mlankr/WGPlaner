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

    // Components shown in the layout file as member variables
    Spinner mdropdownBenutzer;
    EditText bezeichnungInput;
    EditText karmapunkteInput;
    Button aufgabeErstellenBestaetigenButton;

    // Dao objects as member variables
    BenutzerDao benutzerDao;
    AufgabeDao aufgabeDao;
    WGDao wgDao;

    // Details of Aufgabe as member variables
    String bezeichnung;
    int karmapunkte;
    Benutzer ausgewaehlteMietbewohner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgaben_erstellen);

        // Initialize all components in the view
        mdropdownBenutzer = findViewById(R.id.dropDownBenutzer);
        bezeichnungInput = findViewById(R.id.bezeichnungField);
        karmapunkteInput = findViewById(R.id.karmapunkteField);
        aufgabeErstellenBestaetigenButton = findViewById(R.id.aufgabeErstellenBestaetigenButton);

        // Initialize Dao objects
        benutzerDao = new BenutzerDao();
        aufgabeDao = new AufgabeDao();
        wgDao = new WGDao();

        // Show all Benutzer of a particular WG in dropdown
        List<Benutzer> alleMietbewohner = benutzerDao.allBenutzer();
        ArrayAdapter<Benutzer> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleMietbewohner);
        mdropdownBenutzer.setAdapter(adapterFirst);

        // Set onClickListener for the Button and get all the user inputs
        aufgabeErstellenBestaetigenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bezeichnung = bezeichnungInput.getText().toString().trim();
                karmapunkte = karmapunkteInput.getText().toString().isEmpty() ? 0 : Integer.parseInt(karmapunkteInput.getText().toString());
                ausgewaehlteMietbewohner = (Benutzer) mdropdownBenutzer.getSelectedItem();

                // Create Aufgabe with the details provided
                aufgabeErstellen(bezeichnung, karmapunkte, ausgewaehlteMietbewohner);
            }
        });

    }

    // Validate and create new Aufgabe to be saved in the database
    private void aufgabeErstellen(String bezeichnung, int punkte, Benutzer benutzer) {
        String message = "";
        if (bezeichnung.isEmpty()) {
            message = "Bezeichnung kann nicht leer sein!";
            bezeichnungInput.setError("Pflichtfeld");
        } else {
            Aufgabe aufgabe = new Aufgabe(bezeichnung, punkte, benutzer, wgDao.get());
            aufgabeDao.create(aufgabe);
            message = "Aufgabe erfolgreich erstellt";
            karmapunkteInput.clearFocus();
            bezeichnungInput.clearFocus();
            bezeichnungInput.setText("");
            karmapunkteInput.setText("");
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Method to redirect on previous page
    public void zurueckAufgabeAnzeigen(View view) {
        super.onBackPressed();
    }

}