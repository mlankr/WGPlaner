package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wgplaner.R;
import com.wgplaner.dao.AufgabeDao;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.ErledigteAufgabeDao;
import com.wgplaner.model.Aufgabe;
import com.wgplaner.model.Benutzer;
import com.wgplaner.model.ErledigteAufgabe;

import java.util.List;

public class AufgabeBearbeitenActivity extends AppCompatActivity {

    // Components shown in the layout file as member variables
    EditText bezeichnungBearbeiten;
    EditText karmapunkteBearbeiten;
    CheckBox erledigtCheckbox;
    Spinner dropdownBearbeiten;
    Button baestaeigenBearbeiten;

    // Dao objects as member variables
    BenutzerDao benutzerDao;
    AufgabeDao aufgabeDao;
    ErledigteAufgabeDao erledigteAufgabeDao;

    // Aufgabe and ErledigteAufgabe objects as member variables
    Aufgabe aufgabe;
    ErledigteAufgabe erledigteAufgabe;

    // Aufgabe to be edited or updated
    int zumBearbeitenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_bearbeiten);

        // Initialize all components in the view
        bezeichnungBearbeiten = findViewById(R.id.bezeichnungFieldBearbeiten);
        karmapunkteBearbeiten = findViewById(R.id.karmapunkteFieldBearbeiten);
        erledigtCheckbox = findViewById(R.id.erledigtCheckbox);
        dropdownBearbeiten = findViewById(R.id.dropDownBenutzerBearbeiten);
        baestaeigenBearbeiten = findViewById(R.id.aufgabeBearbeitenBestaetigenButton);

        // Retrieve the information passed from intent of previous activity when Edit icon was pressed
        Bundle bundle = getIntent().getExtras();
        zumBearbeitenID = bundle.getInt("bearbeiten");

        // Initialize Dao objects
        benutzerDao = new BenutzerDao();
        aufgabeDao = new AufgabeDao();
        erledigteAufgabeDao = new ErledigteAufgabeDao();

        // Find the particular Aufgabe by its ID
        aufgabe = aufgabeDao.getAufgabe(zumBearbeitenID);


        // Show all Benutzer of a particular WG in dropdown
        List<Benutzer> alleMietbewohner = benutzerDao.allBenutzer();
        ArrayAdapter<Benutzer> adapterList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleMietbewohner);
        dropdownBearbeiten.setAdapter(adapterList);

        // Set all the Aufgabe details to the input fields accordingly before edit
        bezeichnungBearbeiten.setText(aufgabe.getBezeichnung());
        karmapunkteBearbeiten.setText(getString(R.string.platzhalter, "", aufgabe.getKarmapunkte()));
        erledigtCheckbox.setChecked(aufgabe.getErledigteAufgabe());
        int index = adapterList.getPosition(aufgabe.getBenutzer());
        dropdownBearbeiten.setSelection(index);

        // Set onClickListener for the Edit Button
        baestaeigenBearbeiten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aufgabeBearbeiten();
            }
        });

    }

    // Get all the user inputs, validate the edit in the Aufgabe and put new erledigte Aufgabe in the database
    private void aufgabeBearbeiten() {
        String aufgabeBezeichnung = bezeichnungBearbeiten.getText().toString();
        int aufgabekarmapunkte = karmapunkteBearbeiten.getText().toString().isEmpty() ? 0 : Integer.parseInt(karmapunkteBearbeiten.getText().toString());
        boolean aufgabeErledigt = erledigtCheckbox.isChecked();
        Benutzer aufgabeBenutzer = (Benutzer) dropdownBearbeiten.getSelectedItem();

        String message = "Etwas schief gelaufen! Bitte erneut versuchen";
        int updated = 0;
        if (aufgabeBezeichnung.isEmpty()) {
            message = "Bezeichnung kann nicht leer sein!";
            bezeichnungBearbeiten.setError("Pflichtfeld");
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        } else {
            aufgabe.setBezeichnung(aufgabeBezeichnung);
            aufgabe.setKarmapunkte(aufgabekarmapunkte);
            aufgabe.setErledigteAufgabe(aufgabeErledigt);
            aufgabe.setBenutzer(aufgabeBenutzer);

            updated = aufgabeDao.update(aufgabe);
            if (updated == 1) {
                if (aufgabeErledigt) {
                    Benutzer benutzer = aufgabe.getBenutzer();
                    erledigteAufgabe = new ErledigteAufgabe(aufgabe.getBezeichnung(), aufgabe.getKarmapunkte(), benutzer);
                    erledigteAufgabeDao.create(erledigteAufgabe);
                    benutzer.setKarmapunkte(benutzer.getKarmapunkte() + aufgabe.getKarmapunkte());
                    benutzerDao.update(benutzer);
                    message = "Prima! Diese Aufgabe ist nun erledigt";
                } else {
                    message = "Aufgabe erfolgreich bearbeitet!";
                }
            }
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        if (updated == 1) finish();
    }

    // Method to redirect on previous page
    public void zurueckAlleAufgabenAnzeigen(View view) {
        super.onBackPressed();
    }
}