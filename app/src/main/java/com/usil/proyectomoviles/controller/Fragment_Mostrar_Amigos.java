package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class Fragment_Mostrar_Amigos extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;

    ListView lstMisAmigos;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_mostrar_amigos,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();
        lstMisAmigos=getView().findViewById(R.id.lstFgtMostrarAmigos_MisAmigos);
        AdaptadorMisAmigos adaptadorMisAmigos=new AdaptadorMisAmigos(getContext(), daoUsuario.misAmigos(user.getUsuario()));
        lstMisAmigos.setAdapter(adaptadorMisAmigos);
    }
    private void recuperarUsuario() {
        bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }
}

