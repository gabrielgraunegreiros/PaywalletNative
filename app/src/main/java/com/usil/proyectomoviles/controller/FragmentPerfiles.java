package com.usil.proyectomoviles.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.io.Serializable;
import java.util.ArrayList;

public class FragmentPerfiles extends Fragment{
    TextView txtUsuarioPerfil, txtNombrePerfil,txtApellidoPerfil;
    EditText edtEmailPerfil;
    Button btnModificarPerfil, btnEliminarPerfil;
    Usuario u;
    DAOUsuario daoUsuario;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_perfil,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario=new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        txtUsuarioPerfil=getView().findViewById(R.id.txtUsuarioPerfil);
        txtNombrePerfil=getView().findViewById(R.id.txtNombrePerfil);
        txtApellidoPerfil=getView().findViewById(R.id.txtApellidoPerfil);
        edtEmailPerfil=getView().findViewById(R.id.edtEmailPerfil);
        btnModificarPerfil=getView().findViewById(R.id.btnModificarPerfil);
        btnEliminarPerfil=getView().findViewById(R.id.btnEliminarPerfil);
        Bundle bundle=getArguments();
        u=(Usuario) bundle.getSerializable("user");
        Usuario user=daoUsuario.getUser(u.getUsuario());
        txtUsuarioPerfil.setText(user.getUsuario());
        txtNombrePerfil.setText(user.getNombre());
        txtApellidoPerfil.setText(user.getApellidos());
        edtEmailPerfil.setText(user.getCorreo());
        btnModificarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edtEmailPerfil.getText().toString();
                user.setCorreo(email);
                daoUsuario.modificarDatos(user);
                Toast.makeText(getActivity().getApplicationContext(), "Correo Modificado", Toast.LENGTH_SHORT).show();
            }
        });
        btnEliminarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoUsuario.eliminarUsuario(user.getUsuario());
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(),"Se eliminó tu cuenta",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
