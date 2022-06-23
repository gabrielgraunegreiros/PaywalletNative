package com.usil.proyectomoviles.controller;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class Fragment_Mostrar_Solicitud_Amigos extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;

    ListView lstSolicitudes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_mostrar_solicitud_amigos,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();
        lstSolicitudes=getView().findViewById(R.id.lstFgtMostrarSolicitudAmigos_MisSolicitudesAmigos);
        ArrayList<String> listaNombreUsuario=new ArrayList<>();
        ArrayList<Usuario> listaSolicitudes= daoUsuario.getSolicitudesAmistad(user.getUsuario());
        for (Usuario u: listaSolicitudes) {
            listaNombreUsuario.add(u.getUsuario());
        }
        if(listaNombreUsuario.isEmpty()){
            listaNombreUsuario.add("No tiene ninguna Solicitud");
        }
        AdaptadorListaSolicitudes adaptadorListaSolicitudes=new AdaptadorListaSolicitudes(getActivity().getApplicationContext(), listaSolicitudes);
        lstSolicitudes.setAdapter(adaptadorListaSolicitudes);
    }
    private void recuperarUsuario() {
        bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }
}