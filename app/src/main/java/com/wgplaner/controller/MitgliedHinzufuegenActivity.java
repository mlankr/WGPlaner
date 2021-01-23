package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wgplaner.R;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.Benutzer;

public class MitgliedHinzufuegenActivity extends AppCompatActivity {
    String vorname;
    String nachname;
    String email;

    EditText vornameInput;
    EditText nachnameInput;
    EditText emailInput;

    Button mitgliedBestaetigenButton;

    BenutzerDao benutzerDao;
    WGDao wgDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitglied_hinzufuegen);

        vornameInput = findViewById(R.id.vornameField);
        nachnameInput = findViewById(R.id.nachnameField);
        emailInput = findViewById(R.id.emailField);

        mitgliedBestaetigenButton = findViewById(R.id.mitgliedBestaetigenButton);

        benutzerDao = new BenutzerDao();
        wgDao = new WGDao();

        mitgliedBestaetigenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vorname = vornameInput.getText().toString().trim();
                nachname = nachnameInput.getText().toString().trim();
                email = emailInput.getText().toString().trim();
                mitgliedHinzufugen(vorname, nachname, email);
            }
        });

    }

    private void mitgliedHinzufugen(String vorname, String nachname, String email) {
        String message = "Achtung : Alle Felder sind Pflichtfelder!";
        if (vorname.trim().isEmpty()) {
            vornameInput.setError("Pflichtfeld!");
        } else if (nachname.trim().isEmpty()) {
            nachnameInput.setError("Pflichtfeld!");
        } else if (email.trim().isEmpty()) {
            emailInput.setError("Pflichtfeld!");
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                message = "E-Mail nicht gültig!";
            } else {
                if (benutzerDao.getByEmail(email) != null) {
                    message = "Benutzer mit dieser E-mail Adresse bereits registiert! Bitte erneut versuchen";
                } else {
                    Benutzer benutzer = new Benutzer(vorname, nachname, email, wgDao.get());
                    benutzerDao.create(benutzer);
                    message = "Benutzer erfolgreich hinzugefügt";
                    vornameInput.setText("");
                    nachnameInput.setText("");
                    emailInput.setText("");
                    emailInput.clearFocus();
                }
            }
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}