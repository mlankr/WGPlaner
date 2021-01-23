package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wgplaner.R;
import com.wgplaner.dao.AufgabeDao;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.Aufgabe;

import java.util.List;

public class AlleAufgabenActivity extends AppCompatActivity {
    LinearLayout parentInAlleAufgaben;
    WGDao wgDao = new WGDao();
    BenutzerDao benutzerDao = new BenutzerDao();
    AufgabeDao aufgabeDao = new AufgabeDao();

    TextView wgNameInAlleAufgaben;
    ImageView zurueckInAlleAufgaben;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alle_aufgaben);

        wgNameInAlleAufgaben = findViewById(R.id.wgNameViewAlleAufgaben);
        parentInAlleAufgaben = findViewById(R.id.parentLayoutAlleAufgaben);
        zurueckInAlleAufgaben = findViewById(R.id.backAlleAufgaben);

        alleAufgaben();
    }

    private void alleAufgaben() {
        List<Aufgabe> alleAufgabeListe = aufgabeDao.allAufgabe();

        if (alleAufgabeListe.isEmpty()) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView firstTextView = new TextView(this);
            firstTextView.setText(Html.fromHtml("Keine Aufgabe gefunden!"));
            firstTextView.setGravity(R.integer.gravity_center);
            firstTextView.setTextColor(Color.GRAY);
        } else {
            wgNameInAlleAufgaben.setText(Html.fromHtml("<b>" + wgDao.get().getName() + "</b>" + " WG "));
            for (int i = 0; i < alleAufgabeListe.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView textView = new TextView(this);
                parentInAlleAufgaben.addView(linearLayout, linearParams);
                Aufgabe aufgabe = alleAufgabeListe.get(i);
                textView.setText(Html.fromHtml("<br><h4><b><font color='teal' size=5>Aufgabe " + (i + 1) + "</font></b></h4></br>" + "<br><br>Bezeichnung&emsp;:&ensp;" + aufgabe.getBezeichnung()
                        + "</br><br>Karmapunkte &ensp:&ensp;" + aufgabe.getKarmapunkte() + "</br><br>Erledigt &emsp;&emsp;&emsp;:&ensp;" + aufgabe.getErledigteAufgabe() +
                        "</br><br>Zugeordnet zu :&ensp;" + aufgabe.getBenutzer().getVorname() + " " + aufgabe.getBenutzer().getNachname()));
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getApplicationContext(), "Aufgabe " + aufgabe.getBezeichnung(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                linearLayout.addView(textView);
            }


        }

    }

    public void zurueckAlleAufgaben(View view) {
        Intent intent = new Intent(this, AufgabeActivity.class);
        startActivity(intent);
    }
}