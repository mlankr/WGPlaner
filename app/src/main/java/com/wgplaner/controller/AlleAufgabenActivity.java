package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    TextView aufgabeListeTitelWgName;
    TextView aufgabeListeTitel;
    ImageView zurueckInAlleAufgaben;

    boolean isFilterView;

    String filterByEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alle_aufgaben);

        aufgabeListeTitel = findViewById(R.id.aufgabeListeTitel);
//        aufgabeListeTitelWgName = findViewById(R.id.aufgabeListeTitelWgName);
        parentInAlleAufgaben = findViewById(R.id.parentLayoutAlleAufgaben);
        zurueckInAlleAufgaben = findViewById(R.id.backAlleAufgaben);

        Bundle bundle = getIntent().getExtras();
        filterByEmail = bundle.getString("ausgewaehlt");
        isFilterView = !filterByEmail.isEmpty();
        aufgabenListe();
    }

    @Override
    public void onResume() {
        super.onResume();
        aufgabenListe();
    }

    private void aufgabenListe() {
        List<Aufgabe> alleAufgabeListe = !isFilterView ? aufgabeDao.allAufgabe() : aufgabeDao.findByBenutzer(benutzerDao.getByEmail(filterByEmail));
        if (isFilterView) {
            aufgabeListeTitel.setText(Html.fromHtml("<i>Alle Aufgaben von</i> <font color='black'>' <b>" + benutzerDao.getByEmail(filterByEmail).getVorname() + " </b>'</font>"));
        } else {
            aufgabeListeTitel.setText(Html.fromHtml("<i>Alle Aufgaben Ihrer WG</i> <br><font color='black'>'<b> " + wgDao.get().getName() + " </b>'</font>"));
        }
        aufgabeListeTitel.setGravity(Gravity.CENTER_HORIZONTAL);
        parentInAlleAufgaben.removeAllViews();
        if (alleAufgabeListe.isEmpty()) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearParams.topMargin = 250;

            linearLayout.setLayoutParams(linearParams);

            TextView firstTextView = new TextView(this);
            firstTextView.setText(Html.fromHtml("Es ist noch keine Aufgabe in dieser WG erstellet!"));
            firstTextView.setTextColor(Color.GRAY);
            firstTextView.setGravity(R.integer.gravity_center);
            firstTextView.setTextSize(25);
            firstTextView.setTextColor(Color.GRAY);

            linearLayout.addView(firstTextView);
            parentInAlleAufgaben.addView(linearLayout, linearParams);
        } else {
            for (int i = 0; i < alleAufgabeListe.size(); i++) {
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(linearParams);
                linearParams.topMargin = 20;
                linearParams.bottomMargin = 100;

                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageParams.gravity = Gravity.END;
                imageParams.rightMargin = 15;
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.ic_edit);


                TextView textView = new TextView(this);
                Aufgabe aufgabe = alleAufgabeListe.get(i);
                textView.setText(Html.fromHtml("<b><font color='teal'>Aufgabe " + (i + 1) + "</font></b>" + "<br>Bezeichnung&emsp;:&ensp;" + aufgabe.getBezeichnung()
                        + "</br><br>Karmapunkte &ensp:&ensp;" + aufgabe.getKarmapunkte() + "</br><br>Erledigt &emsp;&emsp;&emsp;:&ensp;" + aufgabe.getErledigteAufgabe() +
                        "</br><br>Zugeordnet zu :&ensp;" + aufgabe.getBenutzer().getVorname() + " " + aufgabe.getBenutzer().getNachname()));
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(22);

                linearLayout.addView(textView);
                linearLayout.addView(imageView, imageParams);
                parentInAlleAufgaben.addView(linearLayout, linearParams);


                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aufgabeBearbeiten(aufgabe.getBezeichnung());
                    }
                });
            }
        }
    }

    public void zurueckAlleAufgaben(View view) {
        super.onBackPressed();
    }

    public void aufgabeBearbeiten(String aufgabeName) {
        Intent in = new Intent(this, AufgabeBearbeitenActivity.class);
        in.putExtra("bearbeiten", aufgabeName);
        startActivity(in);
    }

}