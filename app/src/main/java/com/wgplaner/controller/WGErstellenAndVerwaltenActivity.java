package com.wgplaner.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wgplaner.R;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.WG;

public class WGErstellenAndVerwaltenActivity extends AppCompatActivity {

    // Components shown in the layout file as member variables
    EditText wgVerwaltenInput;
    Button erstellenButton;

    // Dao objects as member variables
    WGDao wgDao;
    WG wg;

    // WG name as member variable
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wg_erstellen_verwalten);

        // Initialize all components in the view
        wgVerwaltenInput = findViewById(R.id.wgErstellenTextField);
        erstellenButton = findViewById(R.id.wgErstellenBestaetigenButton);

        // Initialize Dao objects
        wgDao = new WGDao();

        // Fetch the current WG present in database
        wg = wgDao.get();

        // Set onClickListener for the Button and get the user input
        erstellenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = wgVerwaltenInput.getText().toString();

                // Create WG with the name provided
                wgErstellen(name);
            }
        });

        // Set WG name in the input field as such
        if (wg != null) {
            wgVerwaltenInput.setText(wg.getName());
        }
    }

    // Change the 'Erstellen' button to 'Aktualisieren' on resuming this activity
    @Override
    public void onResume() {
        super.onResume();
        if (wg != null) {
            erstellenButton.setText(R.string.aktualisieren);
        }
    }

    // Validate and create new WG to be saved in the database or update the WG already present in the database
    private void wgErstellen(String name) {
        String message = "";
        int status = 0;
        name = name.trim();
        if (name.isEmpty()) {
            message = "WG Name kann nicht leer sein!";
            wgVerwaltenInput.setError("Pflichtfeld");
        } else if (wgDao.get() != null) {
            if (wgDao.get().getName().equals(name)) {
                message = "Es wurde gleicher Name eingegeben! Bitte mit neuer Name versuchen";
            } else {
                status = wgDao.update(name);
                message = "WG aktualisiert";
                wgVerwaltenInput.setText("");
            }
        } else {
            WG wg = new WG(name);
            status = wgDao.create(wg);
            if (status > 0) {
                message = "WG ' " + wg.getName() + " ' ist erfolgreich erstellt";
            } else if (status == -1) {
                message = "WG ' " + wg.getName() + " ' bereits vorhanden! Bitte mit neuem Name versuchen";
            } else {
                message = "WG erstellen nicht erfolgreich! Bitte erneut versuchen";
            }
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        if (status == 1) {
            finish();
        }
    }

    // Method to redirect on previous page
    public void zurueckWgVerwalten(View view) {
        super.onBackPressed();
    }
}