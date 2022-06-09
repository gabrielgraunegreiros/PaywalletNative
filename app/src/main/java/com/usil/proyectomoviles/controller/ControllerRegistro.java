package com.usil.proyectomoviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class ControllerRegistro extends AppCompatActivity {
    EditText edtNom, edtApe, edtUsu, edtCorreo, edtContraseña, edtConfirmar;
    DAOUsuario daoUsuario = new DAOUsuario(this);
    String nombre, apellido, usuario, correo, contraseña;
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
        apellido = edtApe.getText().toString();
        usuario = edtUsu.getText().toString();
        correo = edtCorreo.getText().toString();
        contraseña = edtContraseña.getText().toString();
        confirmar = edtConfirmar.getText().toString();
        if(confirmar == contraseña){
            u = new Usuario(nombre,apellido,correo,usuario,contraseña);
            daoUsuario.registrarUsuario(u);
            Toast.makeText(ControllerRegistro.this,"Registrado correctamente",Toast.LENGTH_SHORT).show();
            limpiar();
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
}