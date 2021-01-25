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
import com.wgplaner.model.Benutzer;

import java.util.List;

public class AlleAufgabenActivity extends AppCompatActivity {
    LinearLayout parentInAlleAufgaben;

    TextView aufgabeListeTitel;
    TextView punktestand;
    ImageView zurueckInAlleAufgaben;

    boolean isFilterView;
    String filterByEmail;

    WGDao wgDao = new WGDao();
    BenutzerDao benutzerDao = new BenutzerDao();
    AufgabeDao aufgabeDao = new AufgabeDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alle_aufgaben);

        aufgabeListeTitel = findViewById(R.id.aufgabeListeTitel);
        punktestand = findViewById(R.id.punktestand);
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
        Benutzer benutzer = benutzerDao.getByEmail(filterByEmail);
        List<Aufgabe> alleAufgabeListe = !isFilterView ? aufgabeDao.allAufgabe() : aufgabeDao.findByBenutzer(benutzer);
        if (isFilterView) {
            aufgabeListeTitel.setText(Html.fromHtml("<i>Alle Aufgaben von</i> <font color='black'>' <b>" + benutzer.getVorname() + " </b>'</font>"));
            punktestand.setText(Html.fromHtml("Punktestand : " + benutzer.getKarmapunkte()));
        } else {
            aufgabeListeTitel.setText(Html.fromHtml("<i>Alle Aufgaben Ihrer WG</i> <br><font color='black'>'<b> " + wgDao.get().getName() + " </b>'</font>"));
            punktestand.setVisibility(View.GONE);
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
                Aufgabe aufgabe = alleAufgabeListe.get(i);

                LinearLayout linearLayoutVertical = new LinearLayout(this);
                linearLayoutVertical.setOrientation(LinearLayout.VERTICAL);

                LinearLayout linearLayoutHorizontal = new LinearLayout(this);
                linearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams linearParamsVertical = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayoutVertical.setLayoutParams(linearParamsVertical);
                linearParamsVertical.topMargin = 20;
                linearParamsVertical.bottomMargin = 100;

                LinearLayout.LayoutParams headingParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                headingParams.weight = 0.9f;
                TextView headingTextView = new TextView(this);
                headingTextView.setText(Html.fromHtml("<b><font color='teal'>Aufgabe " + (i + 1) + "</font></b>"));
                headingTextView.setTextColor(Color.BLACK);
                headingTextView.setTextSize(20);

                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(alleAufgabeListe.get(i).getErledigteAufgabe() ? R.drawable.ic_done : R.drawable.ic_edit);
                imageParams.gravity = Gravity.END;
                imageParams.weight = 0.1f;

                TextView textView = new TextView(this);
                textView.setText(Html.fromHtml("Bezeichnung&emsp;:&ensp;" + aufgabe.getBezeichnung()
                        + "<br>Karmapunkte &ensp:&ensp;" + aufgabe.getKarmapunkte() + "</br><br>Erledigt &emsp;&emsp;&emsp;:&ensp;" + aufgabe.getErledigteAufgabe() +
                        "</br><br>Zugeordnet zu :&ensp;" + aufgabe.getBenutzer().getVorname() + " " + aufgabe.getBenutzer().getNachname()));
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);

                linearLayoutHorizontal.setWeightSum(1f);
                linearLayoutHorizontal.addView(headingTextView, headingParams);
                linearLayoutHorizontal.addView(imageView, imageParams);
                linearLayoutVertical.addView(linearLayoutHorizontal);
                linearLayoutVertical.addView(textView);
                parentInAlleAufgaben.addView(linearLayoutVertical, linearParamsVertical);

                if (!alleAufgabeListe.get(i).getErledigteAufgabe()) {
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            aufgabeBearbeiten(aufgabe.getBezeichnung());
                        }
                    });
                }
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