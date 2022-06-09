package com.usil.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class Registro extends AppCompatActivity {
    EditText edtNom, edtApe, edtUsu, edtCorreo, edtContraseña, edtConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro);
        asignarReferencias();
    }

    private void asignarReferencias() {
        edtNom = findViewById(R.id.edtNom);
        edtApe = findViewById(R.id.edtApe);
        edtUsu = findViewById(R.id.edtUsu);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContraseña);
        edtConfirmar = findViewById(R.id.edtConfirmar);
    }
}