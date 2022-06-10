package com.usil.proyectomoviles.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.entity.iUsuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class Home extends AppCompatActivity{
    Usuario user;
    FragmentPerfiles fragmentPerfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_principal);
        recuperarData();
        asignarReferencias();
    }

    private void asignarReferencias() {
        Bundle bundle=getIntent().getExtras();
        user= (Usuario)bundle.getSerializable("user");
        fragmentPerfiles=(FragmentPerfiles) getSupportFragmentManager().findFragmentById(R.id.fgtPerfilesUsuario);
        fragmentPerfiles.setArguments(bundle);

    }

    private void recuperarData() {

    }




}