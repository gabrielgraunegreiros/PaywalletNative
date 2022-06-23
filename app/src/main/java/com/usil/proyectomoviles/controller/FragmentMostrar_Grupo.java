package com.usil.proyectomoviles.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
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
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

public class FragmentMostrar_Grupo extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;
    TextView txtNombreGrupo;
    Button btnEliminarGrupo, btnCambiarNombre;
    EditText edtNuevoNombre;
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
