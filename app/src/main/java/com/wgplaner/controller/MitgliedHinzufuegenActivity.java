package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wgplaner.R;

public class MitgliedHinzufuegenActivity extends AppCompatActivity {
    String vorname;
    String nachname;
    String email;

    EditText vornameInput;
    EditText nachnameInput;
    EditText emailInput;

    Button mitgliedBestaetigenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitglied_hinzufuegen);

        vornameInput = findViewById(R.id.vornameField);
        nachnameInput = findViewById(R.id.nachnameField);
        emailInput = findViewById(R.id.emailField);

        mitgliedBestaetigenButton = findViewById(R.id.mitgliedBestaetigenButton);

        mitgliedBestaetigenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmail();
            }
        });

    }

    private void checkEmail() {

    }
}