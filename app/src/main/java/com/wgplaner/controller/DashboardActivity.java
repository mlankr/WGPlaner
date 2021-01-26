package com.wgplaner.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wgplaner.R;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.db.DBHelper;
import com.wgplaner.model.Benutzer;
import com.wgplaner.model.WG;

/**
 * This Activity implements the OnClickListener for the whole view
 */
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    // Member variables to be initialized in order to create Admin user by default
    WGDao wgDao;
    BenutzerDao benutzerDao;
    WG wg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize DB service
        DBHelper.initDBService(getApplicationContext());

        // Assign all the buttons their respective IDs and set onClickListener to them
        findViewById(R.id.aufgabeAnzeigenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.offeneAufgabenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.karmastatistikButtonDashboard).setOnClickListener(this);
        findViewById(R.id.einkauflisteAnzeigenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.wgErstellenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.kontoLoeschenButtonDashboard).setOnClickListener(this);
        findViewById(R.id.userverwaltugButtonDashboard).setOnClickListener(this);
        findViewById(R.id.abmeldenButtonDashboard).setOnClickListener(this);

        //Initialization of the Dao objects
        wgDao = new WGDao();
        benutzerDao = new BenutzerDao();
    }

    // Create Admin User by Default after WG is created and change the 'WG erstellen' button to 'WG verwalten' on resuming this activity
    @Override
    public void onResume() {
        super.onResume();
        wg = wgDao.get();
        if (wg != null) {
            Button wgErstellenButton = findViewById(R.id.wgErstellenButtonDashboard);
            wgErstellenButton.setText(R.string.wgVerwalten);

            if (benutzerDao.getByEmail("admin@wg.com") == null) {
                benutzerDao.create(new Benutzer("admin", "user", "admin@wg.com", "*****", true, wgDao.get(), 0));
            }
        }
    }

    // OnClick Method to detect the button pressed and start the new Activity accordingly
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wgErstellenButtonDashboard) {
            Intent intent = new Intent(this, WGErstellenAndVerwaltenActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.abmeldenButtonDashboard) {
            abmelden(this);
        } else {
            if (wg != null) {
                if (v.getId() == R.id.aufgabeAnzeigenButtonDashboard) {
                    Intent aufgabeActivity = new Intent(this, AufgabeAnzeigenActivity.class);
                    startActivity(aufgabeActivity);
                } else if (v.getId() == R.id.userverwaltugButtonDashboard) {
                    Intent userverwalten = new Intent(this, MitgliedHinzufuegenActivity.class);
                    startActivity(userverwalten);
                } else {
                    Intent nichtImplementiert = new Intent(this, NichtImplementiertActivity.class);
                    startActivity(nichtImplementiert);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bitte erst WG erstellen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Show alert dialog box to confirm logout and close DB services if logging out is confirmed
    public static void abmelden(Activity activity) {
        new AlertDialog.Builder(activity).setIcon(R.drawable.ic_logout).setTitle("Abmelden").setMessage("Möchten Sie wirklich abmelden?").setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int flag) {
                DBHelper.closeDBService();
                activity.finishAffinity();
            }
        }).setNegativeButton("Nein", null).show();
    }
}


