package com.usil.proyectomoviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class MainActivity extends AppCompatActivity {
    EditText edtUsuario, edtContra;
    TextView txtRegistro;
    DAOUsuario daoUsuario = new DAOUsuario(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        daoUsuario.openDB();
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
                Intent intent = new Intent(MainActivity.this, ControllerRegistro.class);
                startActivity(intent);
            }
        });
    }
    public void identificarUsario(View view){
        String us=edtUsuario.getText().toString();
        String contra=edtContra.getText().toString();
        Usuario user=daoUsuario.validarUsuario(us,contra);
        if(user!=null){
            Intent intent=new Intent(MainActivity.this,Home.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }


}