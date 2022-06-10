package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;

public class FragmentPerfil extends Fragment {
    TextView txvUsu, txvNom, txvApe;
    EditText edtEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lyt_fgt_perfil,container,false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txvUsu = getView().findViewById(R.id.txvUsu);
        txvNom = getView().findViewById(R.id.txvNom);
        txvApe = getView().findViewById(R.id.txvApe);
        edtEmail = getView().findViewById(R.id.edtEmail);
    }

    public void mostrar(Usuario u){
        txvUsu.setText(u.getUsuario());
        txvNom.setText(u.getNombre());
        txvApe.setText(u.getApellidos());
        edtEmail.setText(u.getCorreo());
    }
}
