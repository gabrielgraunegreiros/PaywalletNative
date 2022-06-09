package com.usil.proyectomoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edtUsuario, edtContra;
    TextView txtRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        asignarReferencias();
        registrarUsuario();
    }

    private void asignarReferencias() {
        edtUsuario = findViewById(R.id.edtUsuario);
        edtContra = findViewById(R.id.edtContra);
        txtRegistro = findViewById(R.id.txtRegistro);
    }

    private void registrarUsuario(){
        txtRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Registro.class);
                startActivity(intent);
            }
        });
    }


}