package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wgplaner.R;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.WG;

public class WGErstellenActivity extends AppCompatActivity {
    String name;

    EditText wgVerwaltenInput;
    Button erstellenButton;

    WGDao wgDao;
    WG wg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wg_erstellen);

        wgVerwaltenInput = findViewById(R.id.wgErstellenTextField);
        erstellenButton = findViewById(R.id.wgErstellenBestaetigenButton);

        wgDao = new WGDao();
        wg = wgDao.get();
        erstellenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                name = wgVerwaltenInput.getText().toString();
                wgErstellen(name);
            }
        });

        onResume();
    }

    private void wgErstellen(String name) {
        String message = "";
        if (name.isEmpty()) {
            message = "WG Name kann nicht leer sein!";
        } else if (wgDao.get() != null) {
            wgDao.update(name);
            message = "WG aktualisiert";
            wgVerwaltenInput.setText("");
        } else {
            WG wg = new WG(name);
            int success = wgDao.erstellen(wg);
            if (success > 0) {
                message = "WG ' " + wg.getName() + " ' ist erfolgreich erstellt";
                wgVerwaltenInput.setText("");
                erstellenButton.setText(R.string.aktualisieren);
            } else if (success == -1) {
                message = "WG ' " + wg.getName() + " ' bereits vorhanden! Bitte mit neuem Name versuchen";
            } else {
                message = "WG erstellen nicht erfolgreich! Bitte erneut versuchen";
            }
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            wgVerwaltenInput.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (wg != null) {
            erstellenButton.setText(R.string.aktualisieren);
        }
    }
}