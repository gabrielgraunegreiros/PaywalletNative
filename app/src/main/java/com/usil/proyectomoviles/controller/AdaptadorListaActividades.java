package com.usil.proyectomoviles.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Actividad;
import com.usil.proyectomoviles.entity.Grupo;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class AdaptadorListaActividades extends BaseAdapter {
    ArrayList<Actividad> listaActividad;
    Context context;
    DAOUsuario daoUsuario;
    TextView txtNombreCompleto, txtUsuario,txtIdTipoActividad,txtMonto;
    public AdaptadorListaActividades(Context context, ArrayList<Actividad> listaActividad) {
        this.listaActividad = listaActividad;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaActividad.size();
    }

    @Override
    public Object getItem(int i) {
        return listaActividad.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_lista_mi_actividad,null);
        final Actividad act=listaActividad.get(i);
        daoUsuario = new DAOUsuario(view.getContext());
        daoUsuario.openDB();
        final Usuario usGasto= daoUsuario.getUser(act.getIdUsuarioGasto());
        txtNombreCompleto=view.findViewById(R.id.txtAdapListActividades_NombreCompleto);
        txtUsuario=view.findViewById(R.id.txtAdapListActividades_Usuario);
        txtIdTipoActividad=view.findViewById(R.id.txtAdapListActividades_idTipoActividad);
        txtMonto=view.findViewById(R.id.txtAdapListActividades_Monto);

        txtNombreCompleto.setText(usGasto.getNombre()+" "+usGasto.getApellidos());
        txtUsuario.setText(act.getIdUsuarioGasto()+"");
        txtMonto.setText("S/. "+act.getMonto());
        txtIdTipoActividad.setText(daoUsuario.getDescripTipoActividad(act.getIdTipoActividad()));
        if(act.getIdTipoActividad()==1){
            txtIdTipoActividad.setTextColor(Color.RED);
            txtMonto.setTextColor(Color.RED);
        }else{
            txtIdTipoActividad.setTextColor(Color.GREEN);
            txtMonto.setTextColor(Color.GREEN);
        }


        return view;
    }
}
