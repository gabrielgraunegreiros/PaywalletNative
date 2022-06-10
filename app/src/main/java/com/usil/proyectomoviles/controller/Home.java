package com.usil.proyectomoviles.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.entity.iUsuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class Home extends AppCompatActivity{
    BottomNavigationView navegacionInferior;
    Usuario user;
    FragmentPerfiles fragmentPerfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_home);
        asignarReferencias();
        mostrarFragments();

    }
    private void asignarReferencias() {
    }

    private void mostrarFragments() {
        mostrarFragmentSeleccionado(new FragmentGrupo());
        navegacionInferior = (BottomNavigationView) findViewById(R.id.menuNavegacion);
        navegacionInferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()== R.id.menu_group){
                    mostrarFragmentSeleccionado(new FragmentGrupo());
                }
                if (item.getItemId() == R.id.menu_friends){
                    mostrarFragmentSeleccionado(new FragmentAmigos());
                }
                if (item.getItemId() == R.id.menu_activity){
                    mostrarFragmentSeleccionado(new FragmentActividad());
                }else if(item.getItemId()==R.id.menu_profile){
                    mostrarFragmentSeleccionado(new FragmentPerfiles());
                }
                return true;
            }
        });
    }

    private void mostrarFragmentSeleccionado(Fragment fragment){
        Bundle bundle=getIntent().getExtras();
        user= (Usuario)bundle.getSerializable("user");
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }
}