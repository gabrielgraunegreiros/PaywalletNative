package com.usil.proyectomoviles.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class FragmentActividad extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;

    ListView lstMiActividad;
    Button btnAgregarGasto;
    TextView txtMostrarDinero;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_actividad,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();

        lstMiActividad=getView().findViewById(R.id.lstFgtActividad_MiActividad);
        btnAgregarGasto=getView().findViewById(R.id.btnFgtActividad_AgregarGasto);
        txtMostrarDinero=getView().findViewById(R.id.txtFgtActividad_MostrarDinero);
        AdaptadorListaActividades adaptadorListaActividades=new AdaptadorListaActividades(view.getContext(), daoUsuario.getMiActividad(user.getUsuario()));
        lstMiActividad.setAdapter(adaptadorListaActividades);
        txtMostrarDinero.setText("Saldo actual: S/."+daoUsuario.obtenerBalanceDinero(user.getUsuario()));
        if(daoUsuario.obtenerBalanceDinero(user.getUsuario())<=0){
            txtMostrarDinero.setTextColor(Color.RED);
        }else if(daoUsuario.obtenerBalanceDinero(user.getUsuario())>0 && daoUsuario.obtenerBalanceDinero(user.getUsuario())<=150){
            txtMostrarDinero.setTextColor(Color.YELLOW);
            advertenciaDinero();
        } else {
            txtMostrarDinero.setTextColor(Color.GREEN);
        }
        btnAgregarGasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Agregar_Gasto fgtAgregarGasto=new Fragment_Agregar_Gasto();
                fgtAgregarGasto.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,fgtAgregarGasto)
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

    private void advertenciaDinero(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Advertencia");
        builder.setMessage("Su dinero se está agotando, considere disminuir sus gastos.");
        builder.setPositiveButton("OK",null);
        builder.show();
    }
}
