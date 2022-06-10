package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class FragmentPerfiles extends Fragment {
    TextView txtUsuarioPerfil, txtNombrePerfil,txtApellidoPerfil;
    EditText edtEmailPerfil;
    Button btnModificarPerfil;
    Usuario user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_perfil,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtUsuarioPerfil=getView().findViewById(R.id.txtUsuarioPerfil);
        txtNombrePerfil=getView().findViewById(R.id.txtNombrePerfil);
        txtApellidoPerfil=getView().findViewById(R.id.txtApellidoPerfil);
        edtEmailPerfil=getView().findViewById(R.id.edtEmailPerfil);
        btnModificarPerfil=getView().findViewById(R.id.btnModificarPerfil);
        Bundle bundle=getArguments();
        user=(Usuario) bundle.getSerializable("user");
        txtUsuarioPerfil.setText(user.getUsuario());
        txtNombrePerfil.setText(user.getNombre());
        txtApellidoPerfil.setText(user.getApellidos());
        edtEmailPerfil.setText(user.getCorreo());
    }
}
