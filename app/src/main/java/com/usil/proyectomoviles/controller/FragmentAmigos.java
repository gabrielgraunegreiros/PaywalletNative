package com.usil.proyectomoviles.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class FragmentAmigos extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;

    Button btnAddAmigo, btnMisamigos, btnSolicitudes;
    EditText edtNuevoAmigo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_amigos,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();

        btnAddAmigo=getView().findViewById(R.id.btnFgtAmigos_AddAmigo);
        btnMisamigos=getView().findViewById(R.id.btnFgtAmigos_MisAmigos);
        btnSolicitudes=getView().findViewById(R.id.btnFgtAmigos_SolicitudAmigo);

        btnAddAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder addAmigoDialog=new AlertDialog.Builder(getActivity());
                addAmigoDialog.setTitle("Ingrese el usuario");
                edtNuevoAmigo =new EditText(getActivity().getApplicationContext());
                edtNuevoAmigo.setInputType(InputType.TYPE_CLASS_TEXT);
                addAmigoDialog.setView(edtNuevoAmigo);
                addAmigoDialog.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(daoUsuario.existeUsuario(edtNuevoAmigo.getText().toString())){
                            daoUsuario.agregarAmigo(user.getUsuario(),edtNuevoAmigo.getText().toString(), getActivity().getApplicationContext());
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Usuario no existente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                addAmigoDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                addAmigoDialog.show();
            }
        });
        btnMisamigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Mostrar_Amigos fgtMostrarAmigos=new Fragment_Mostrar_Amigos();
                fgtMostrarAmigos.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ContainerFgtAmigos,fgtMostrarAmigos)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnSolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Mostrar_Solicitud_Amigos fgtMostrarSolicitudesAmigos=new Fragment_Mostrar_Solicitud_Amigos();
                fgtMostrarSolicitudesAmigos.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ContainerFgtAmigos,fgtMostrarSolicitudesAmigos)
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
