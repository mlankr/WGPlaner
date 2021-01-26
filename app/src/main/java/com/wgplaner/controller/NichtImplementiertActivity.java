package com.wgplaner.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.wgplaner.R;

/**
 * This Activity is for all unimplemented functionalities or pages
 */
public class NichtImplementiertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicht_implementiert);
    }

    // Method to redirect on previous page
    public void zurueckNichtImplementiert(View view) {
        super.onBackPressed();
    }
}