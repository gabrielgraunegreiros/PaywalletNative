package com.usil.proyectomoviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.usil.proyectomoviles.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_home);
        recuperarData();
    }

    private void recuperarData() {

    }
}