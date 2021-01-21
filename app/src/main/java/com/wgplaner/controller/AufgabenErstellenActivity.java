package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wgplaner.R;

public class AufgabenErstellenActivity extends AppCompatActivity {
    Spinner mdropdownBenutzer;
    LinearLayout merledigtCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aufgaben_erstellen);

        mdropdownBenutzer = findViewById(R.id.dropDownBenutzer);
        merledigtCheckBox = findViewById(R.id.erledigtLayout);

        merledigtCheckBox.setVisibility(View.GONE);

        //create a list of items for the spinner.
        String[] users = new String[]{"Max", "Peter", "Sara"};
        ArrayAdapter<String> adapterFirst = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
        mdropdownBenutzer.setAdapter(adapterFirst)
        ;
    }

}