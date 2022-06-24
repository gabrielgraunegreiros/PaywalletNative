package com.usil.proyectomoviles.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.Group;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class AdaptadorListaSolicitudes extends BaseAdapter {
    ArrayList<Usuario> listaSolicitudes;
    Context context;
    DAOUsuario daoUsuario;
    String user;
    TextView txtNombreUsuario, txtNombreCompleto;
    Group gpAceptarSoli,gpRechazarSoli;
    public AdaptadorListaSolicitudes(Context context, ArrayList<Usuario> listaSolicitudes) {
        this.listaSolicitudes = listaSolicitudes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaSolicitudes.size();
    }

    @Override
    public Object getItem(int i) {
        return listaSolicitudes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_lista_solicitudes_amigos,null);
        daoUsuario = new DAOUsuario(view.getContext());
        daoUsuario.openDB();
        final Usuario u=listaSolicitudes.get(i);

        txtNombreUsuario=view.findViewById(R.id.txtAdapListSoli_NombreUsuario);
        txtNombreCompleto=view.findViewById(R.id.txtAdapListSoli_NombreCompleto);
        gpAceptarSoli=view.findViewById(R.id.groupAceptar);
        gpRechazarSoli=view.findViewById(R.id.groupRechazar);

        txtNombreUsuario.setText(u.getUsuario()+"");
        txtNombreCompleto.setText(u.getNombre()+" "+u.getApellidos());
        int refIds[]= gpAceptarSoli.getReferencedIds();
        for (int id:refIds){
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    daoUsuario.aceptarAmigo(u.getUsuario(),user);
                    setNuevosDatos(daoUsuario.getSolicitudesAmistad(user));
                    Toast.makeText(context, "Se agrego al usuario "+u.getUsuario(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }
    public void enviarUser(String u){
        //u: representa el usuario logeado
        user=u;
    }
    public void setNuevosDatos(ArrayList<Usuario> newListaSolicitudes){
        this.listaSolicitudes=newListaSolicitudes;
        notifyDataSetChanged();
    }
}
