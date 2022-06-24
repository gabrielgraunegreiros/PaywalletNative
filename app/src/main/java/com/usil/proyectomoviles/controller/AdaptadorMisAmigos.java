package com.usil.proyectomoviles.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;

import java.util.ArrayList;

public class AdaptadorMisAmigos extends BaseAdapter {
    Context context;
    ArrayList<Usuario> listaMisAmigos;

    TextView txtNombreUsuario, txtNombreCompleto;
    public AdaptadorMisAmigos(Context context, ArrayList<Usuario> listaMisAmigos) {
        this.context = context;
        this.listaMisAmigos = listaMisAmigos;
    }

    @Override
    public int getCount() {
        return listaMisAmigos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaMisAmigos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_lista_mis_amigos,null);
        final Usuario u=listaMisAmigos.get(i);
        txtNombreUsuario=view.findViewById(R.id.txtAdapMisAmigos_NombreUsuario);
        txtNombreCompleto=view.findViewById(R.id.txtAdapMisAmigos_NombreCompleto);

        txtNombreUsuario.setText(u.getUsuario()+"");
        txtNombreCompleto.setText(u.getNombre()+" "+u.getApellidos());
        return view;
    }
}
