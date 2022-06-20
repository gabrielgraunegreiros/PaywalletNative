package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    Bundle bundle;
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
        lstGrupos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentMostrar_Grupo fgtMostrar_Grupo=new FragmentMostrar_Grupo();
                bundle.putInt("idGrupo", daoUsuario.getGrupos(user).get(i).getId());
                fgtMostrar_Grupo.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,fgtMostrar_Grupo)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnNuevoGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentRegistrar_Grupo fgtRegistrar_Grupo=new FragmentRegistrar_Grupo();

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
        bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }
}
