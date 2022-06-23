package com.usil.proyectomoviles.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Grupo;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class AdaptadorListaGrupos extends BaseAdapter {
    ArrayList<Grupo> listaGrupos;
    Context context;
    TextView txtNombreGrupo, txtCantUsuarios;
    DAOUsuario daoUsuario;

    public AdaptadorListaGrupos(Context context, ArrayList<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaGrupos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaGrupos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_lista_grupo,null);
        final Grupo g=listaGrupos.get(i);
        txtNombreGrupo=view.findViewById(R.id.txtAdapListGrupo_NombreGrupo);
        txtCantUsuarios=view.findViewById(R.id.txtAdapListGrupo_CantUsuarios);

        daoUsuario = new DAOUsuario(view.getContext());
        daoUsuario.openDB();

        txtNombreGrupo.setText(g.getNombreGrupo());
        txtCantUsuarios.setText(daoUsuario.cantidadPersonasGrupo(g.getId())+"");
        return view;
    }
}
