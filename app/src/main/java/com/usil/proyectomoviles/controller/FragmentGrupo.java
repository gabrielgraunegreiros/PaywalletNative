package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Grupo;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class FragmentGrupo extends Fragment {
    DAOUsuario daoUsuario;
    Usuario user;

    Button btnNuevoGrupo;
    ListView lstGrupos;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_grupo,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();
        btnNuevoGrupo=getView().findViewById(R.id.btnFgtGrupo_NuevoGrupo);
        lstGrupos=getView().findViewById(R.id.lstFgtGrupo_Grupos);
        AdaptadorListaGrupos adaptadorListaGrupos=new AdaptadorListaGrupos(getActivity().getApplicationContext(), daoUsuario.getGrupos(user));
        lstGrupos.setAdapter(adaptadorListaGrupos);
        btnNuevoGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentRegistrar_Grupo fgtRegistrar_Grupo=new FragmentRegistrar_Grupo();
                Bundle bundle=getActivity().getIntent().getExtras();
                fgtRegistrar_Grupo.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,fgtRegistrar_Grupo)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void recuperarUsuario() {
        Bundle bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }
}
