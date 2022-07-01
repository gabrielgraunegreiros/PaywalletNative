package com.usil.proyectomoviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;
import java.util.List;

public class ControllerRegistro extends AppCompatActivity {
    EditText edtNom, edtApe, edtUsu, edtCorreo, edtContraseña, edtConfirmar;
    ListView lstUsu;
    DAOUsuario daoUsuario = new DAOUsuario(this);
    String nombre, apellidos, usuario, correo, contraseña;
    ArrayList<Usuario> listaUsu = new ArrayList<>();
    Usuario u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro);
        asignarReferencias();
        daoUsuario.openDB();
    }

    private void asignarReferencias() {
        edtNom = findViewById(R.id.edtNom);
        edtApe = findViewById(R.id.edtApe);
        edtUsu = findViewById(R.id.edtUsu);
        edtCorreo = findViewById(R.id.edtCorreo);
        edtContraseña = findViewById(R.id.edtContraseña);
        edtConfirmar = findViewById(R.id.edtConfirmar);
    }

    public void registrarUsuario(View view){
        String confirmar;
        nombre = edtNom.getText().toString();
        apellidos = edtApe.getText().toString();
        usuario = edtUsu.getText().toString();
        correo = edtCorreo.getText().toString();
        contraseña = edtContraseña.getText().toString();
        confirmar = edtConfirmar.getText().toString();
        if(confirmar.equals(contraseña)){
            if(daoUsuario.existeUsuario(usuario)){
                Toast.makeText(this, "Nombre de usuario ya existente", Toast.LENGTH_SHORT).show();
            }else{
                u = new Usuario(nombre,apellidos,correo,usuario,contraseña);
                daoUsuario.registrarUsuario(u);
                Toast.makeText(ControllerRegistro.this,"Registrado correctamente",Toast.LENGTH_SHORT).show();
                limpiar();
            }
        } else {
            Toast.makeText(ControllerRegistro.this,"Confirme la contraseña correctamente",Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar() {
        edtNom.setText("");
        edtApe.setText("");
        edtUsu.setText("");
        edtCorreo.setText("");
        edtContraseña.setText("");
        edtConfirmar.setText("");
    }

    public void menuPrincipal(View view){
        Intent intent = new Intent(ControllerRegistro.this, MainActivity.class);
        startActivity(intent);
    }
}