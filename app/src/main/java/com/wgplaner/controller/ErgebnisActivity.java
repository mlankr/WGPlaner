package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wgplaner.R;
import com.wgplaner.dao.AufgabeDao;
import com.wgplaner.dao.BenutzerDao;
import com.wgplaner.dao.WGDao;
import com.wgplaner.model.Aufgabe;
import com.wgplaner.model.Benutzer;

import java.util.LinkedList;
import java.util.List;


public class ErgebnisActivity extends AppCompatActivity {
    LinearLayout parent;
    WGDao wgDao = new WGDao();
    BenutzerDao benutzerDao = new BenutzerDao();
    AufgabeDao aufgabeDao = new AufgabeDao();

    TextView wgName;
    String selectedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);

        wgName = findViewById(R.id.wgNameView);

        parent = findViewById(R.id.parentLayout);

        Bundle bundle = getIntent().getExtras();
        selectedName = bundle.getString("ausgewaehlt");

        if (aufgabeDao.allAufgabe().isEmpty()) {
            wgName.setText(Html.fromHtml("Keine Aufgabe in der WG " + "<b>" + wgDao.get().getName() + "</b>" + " gefunden!"));
            wgName.setGravity(R.integer.gravity_center);
            wgName.setTextColor(Color.GRAY);
        }
        filterAufgabe();
    }

    private void filterAufgabe() {
        List<Aufgabe> aufgabeList = new LinkedList<>();
        List<Aufgabe> alleAufgabeListe = aufgabeDao.allAufgabe();
        Benutzer benutzer = benutzerDao.findBenutzer(selectedName);

        if (!aufgabeDao.allAufgabe().isEmpty()) {
            for (Aufgabe aufgabe : alleAufgabeListe) {
                if (aufgabe.getBenutzer().getEmailAdresse().equals(benutzer.getEmailAdresse())) {
                    System.out.println("TEST :" + aufgabe);
                    aufgabeList.add(aufgabe);
                }
            }
        }

        if (aufgabeList.isEmpty()) {
            System.out.println("Found :" + aufgabeList);
            wgName.setText(Html.fromHtml("<b>" + wgDao.get().getName() + "</b>" + " WG "));
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            parent.addView(linearLayout, linearParams);
            TextView firstTextView = new TextView(this);
            firstTextView.setText(Html.fromHtml("Keine Aufgabe für diesen Mietbewohner " + "<b>" + selectedName + "</b>" + " gefunden!"));
            firstTextView.setGravity(R.integer.gravity_center);
            firstTextView.setTextColor(Color.GRAY);
            firstTextView.setTextSize(25);
            linearLayout.addView(firstTextView);
        } else {
            wgName.setText(Html.fromHtml("<b>" + wgDao.get().getName() + "</b>" + " WG "));
            for (int i = 0; i < aufgabeList.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(this);
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                parent.addView(linearLayout, linearParams);
                TextView textView = new TextView(this);
                textView.setText(Html.fromHtml("<br><b>Aufgabe " + (i + 1) + "</b></br>" + "<br><br>Bezeichnung&ensp;:&emsp;" + aufgabeList.get(i).getBezeichnung()
                        + "</br><br>Karmapunkte :&emsp;" + aufgabeList.get(i).getKarmapunkte() + "</br><br>Erledigt&emsp;&emsp;&ensp; :&emsp;" + aufgabeList.get(i).getErledigteAufgabe()));
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(22);
                linearLayout.addView(textView);
            }
        }

    }
}