package com.usil.proyectomoviles.controller;

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
import androidx.fragment.app.FragmentTransaction;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Grupo;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class FragmentRegistrar_Grupo extends Fragment {
    DAOUsuario daoUsuario;
    EditText edtNombreGrupo;
    Button btnCrearGrupo;
    String nombreGrupo;
    Usuario user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_registrar_grupo,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();
        edtNombreGrupo=getView().findViewById(R.id.edtFgtGrupo_RegistrarNombreGrupo);
        btnCrearGrupo=getView().findViewById(R.id.btnFgtRegistrarGrupo_Crear);
        btnCrearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreGrupo=edtNombreGrupo.getText().toString();
                if(nombreGrupo.isEmpty()){
                    Toast.makeText(view.getContext(), "Rellene los espacios en blanco", Toast.LENGTH_SHORT).show();
                }else{
                    Grupo grupo=new Grupo(nombreGrupo);
                    daoUsuario.registrarGrupo(grupo,user);
                    FragmentGrupo fgtGrupo=new FragmentGrupo();
                    Bundle bundle=getActivity().getIntent().getExtras();
                    fgtGrupo.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,fgtGrupo)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                    Toast.makeText(getActivity().getApplicationContext(), "Grupo creado con éxito", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void recuperarUsuario() {
        Bundle bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }
}
