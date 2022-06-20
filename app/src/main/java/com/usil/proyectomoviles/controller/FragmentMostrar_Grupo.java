package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class FragmentMostrar_Grupo extends Fragment {
    DAOUsuario daoUsuario;

    TextView txtNombreGrupo;
    Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_mostrar_grupo,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        txtNombreGrupo=getView().findViewById(R.id.txtFgtMostrarGrupo_NombreGrupo);
        bundle = getArguments();
        int idGrupo =  bundle.getInt("idGrupo");
        txtNombreGrupo.setText(daoUsuario.getGrupo(idGrupo).getNombreGrupo());

    }
}
