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
    String zumBearbeiten;

    EditText bezeichnungBearbeiten;
    EditText karmapunkteBearbeiten;
    CheckBox erledigtCheckbox;
    Spinner dropdownBearbeiten;
    Button baestaeigenBearbein;

    BenutzerDao benutzerDao;
    AufgabeDao aufgabeDao;
    ErledigteAufgabeDao erledigteAufgabeDao;

    Aufgabe aufgabe;
    ErledigteAufgabe erledigteAufgabe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgabe_bearbeiten);

        Bundle bundle = getIntent().getExtras();
        zumBearbeiten = bundle.getString("bearbeiten");

        benutzerDao = new BenutzerDao();
        aufgabeDao = new AufgabeDao();
        erledigteAufgabeDao = new ErledigteAufgabeDao();

        aufgabe = aufgabeDao.findAufgabe(zumBearbeiten);

        bezeichnungBearbeiten = findViewById(R.id.bezeichnungFieldBearbeiten);
        karmapunkteBearbeiten = findViewById(R.id.karmapunkteFieldBearbeiten);
        erledigtCheckbox = findViewById(R.id.erledigtCheckbox);
        dropdownBearbeiten = findViewById(R.id.dropDownBenutzerBearbeiten);
        baestaeigenBearbein = findViewById(R.id.aufgabeBearbeitenBestaetigenButton);


        //Liste von Mietbewohner-Namen im Dropdown Menu.
        List<Benutzer> alleMietbewohner = benutzerDao.allBenutzer();
        ArrayAdapter<Benutzer> adapterList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alleMietbewohner);
        dropdownBearbeiten.setAdapter(adapterList);

        bezeichnungBearbeiten.setText(aufgabe.getBezeichnung());
        karmapunkteBearbeiten.setText(getString(R.string.platzhalter, "", aufgabe.getKarmapunkte()));
        erledigtCheckbox.setChecked(aufgabe.getErledigteAufgabe());
        int index = adapterList.getPosition(aufgabe.getBenutzer());
        dropdownBearbeiten.setSelection(index);

        baestaeigenBearbein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aufgabeBearbeiten();
            }
        });

    }

    public void zurueckAlleAufgabenAnzeigen(View view) {
        super.onBackPressed();
    }

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
}