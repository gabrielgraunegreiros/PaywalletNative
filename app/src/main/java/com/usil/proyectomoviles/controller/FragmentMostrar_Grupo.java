package com.usil.proyectomoviles.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.util.ArrayList;

public class FragmentMostrar_Grupo extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;
    TextView txtNombreGrupo;
    Button btnEliminarGrupo, btnCambiarNombre, btnAñadirParticipante;
    EditText edtNuevoNombre;
    ListView lstMostrarParticipantes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_mostrar_grupo,null);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();

        int idGrupo =  bundle.getInt("idGrupo");

        txtNombreGrupo=getView().findViewById(R.id.txtFgtMostrarGrupo_NombreGrupo);
        btnEliminarGrupo=getView().findViewById(R.id.btnFgtMostrarGrupo_EliminarGrupo);
        btnCambiarNombre=getView().findViewById(R.id.btnFgtMostrarGrupo_CambiarNombre);
        btnAñadirParticipante=getView().findViewById(R.id.btnFgtMostrarGrupo_AñadirParticipante);
        lstMostrarParticipantes=getView().findViewById(R.id.lstFgtMostrarGrupo_MostrarParticipantes);

        ArrayList<String> listaUsuariod_De_Grupo=new ArrayList<>();
        for (int i = 0; i < daoUsuario.getUsuarios_de_Grupo(idGrupo).size(); i++) {
            listaUsuariod_De_Grupo.add(daoUsuario.getUsuarios_de_Grupo(idGrupo).get(i).getUsuario());
        }
        ArrayAdapter adapter=new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1,listaUsuariod_De_Grupo);
        lstMostrarParticipantes.setAdapter(adapter);


        btnAñadirParticipante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder añadirParticipanteDialog=new AlertDialog.Builder(getActivity());
                añadirParticipanteDialog.setTitle("Nuevo Participante");
                EditText edtNuevoParticipante=new EditText(getActivity().getApplicationContext());
                edtNuevoParticipante.setInputType(InputType.TYPE_CLASS_TEXT);
                añadirParticipanteDialog.setView(edtNuevoParticipante);
                añadirParticipanteDialog.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(daoUsuario.existeUsuario(edtNuevoParticipante.getText().toString())){
                            daoUsuario.añadirParticipanteGrupo(idGrupo,edtNuevoParticipante.getText().toString());
                            Toast.makeText(view.getContext(), "Usuario agregado al grupo", Toast.LENGTH_SHORT).show();
                            listaUsuariod_De_Grupo.clear();
                            for (int m = 0; m < daoUsuario.getUsuarios_de_Grupo(idGrupo).size(); m++) {
                                listaUsuariod_De_Grupo.add(daoUsuario.getUsuarios_de_Grupo(idGrupo).get(m).getUsuario());
                            }
                            ArrayAdapter newAdapter=new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1,listaUsuariod_De_Grupo);
                            lstMostrarParticipantes.setAdapter(newAdapter);
                        }else{
                            Toast.makeText(view.getContext(), "Usuario no existente", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                añadirParticipanteDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                añadirParticipanteDialog.show();
            }
        });
        txtNombreGrupo.setText(daoUsuario.getGrupo(idGrupo).getNombreGrupo());
        btnEliminarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentGrupo fgtGrupo=new FragmentGrupo();
                fgtGrupo.setArguments(bundle);
                daoUsuario.eliminarGrupo(idGrupo,user);
                Toast.makeText(getActivity().getApplicationContext(), "Se elimino el grupo", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,fgtGrupo)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnCambiarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder cambiarNombreDialog=new AlertDialog.Builder(getActivity());
                cambiarNombreDialog.setTitle("Nuevo nombre del grupo");
                edtNuevoNombre=new EditText(getActivity().getApplicationContext());
                edtNuevoNombre.setInputType(InputType.TYPE_CLASS_TEXT);
                cambiarNombreDialog.setView(edtNuevoNombre);
                cambiarNombreDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        daoUsuario.cambiarNombreGrupo(idGrupo,edtNuevoNombre.getText().toString());
                        txtNombreGrupo.setText(daoUsuario.getGrupo(idGrupo).getNombreGrupo());
                    }
                });
                cambiarNombreDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                cambiarNombreDialog.show();
            }
        });

    }
    private void recuperarUsuario() {
        bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }
}
