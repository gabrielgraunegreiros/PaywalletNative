package com.usil.proyectomoviles.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.usil.proyectomoviles.R;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.modelo.DAOUsuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_Agregar_Gasto extends Fragment {
    DAOUsuario daoUsuario;
    Bundle bundle;
    Usuario user;

    EditText edtMonto;
    Button btnGuardar;
    Spinner sprTipoActividad, sprUsuarioGasto;
    ArrayList<String> listaTipoActidad;
    ArrayList<String> listaUsuariosAmigos;
    ArrayList<Usuario> listaAmigos;

    String idUsuarioGasto, fecha;
    double monto;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.lyt_fgt_actividad_add_gasto,null);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoUsuario = new DAOUsuario(getActivity().getApplicationContext());
        daoUsuario.openDB();
        recuperarUsuario();

        btnGuardar=getView().findViewById(R.id.btnFgtAgregarGasto_Guardar);
        sprUsuarioGasto=getView().findViewById(R.id.sprFgtAgregarGasto_UsuarioGasto);
        edtMonto=getView().findViewById(R.id.edtFgtAgregarGasto_Monto);
        sprTipoActividad=getView().findViewById(R.id.sprFgtAgregarGasto_TipoActividad);

        listaTipoActidad= daoUsuario.getTipoActividad();
        ArrayAdapter tiposActividad=new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, listaTipoActidad);
        sprTipoActividad.setAdapter(tiposActividad);


        ArrayAdapter amigoGasto=new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, getUsuariosAmigos());
        sprUsuarioGasto.setAdapter(amigoGasto);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //obtener dia/mes/año actual
                long now = System.currentTimeMillis();
                Date dia = new Date(now);
                DateFormat df = new SimpleDateFormat("dd/MM/yy");
                fecha = df.format(dia);
                //-------
                String tipAct=sprTipoActividad.getSelectedItem().toString();
                idUsuarioGasto=sprUsuarioGasto.getSelectedItem().toString();
                if(idUsuarioGasto.isEmpty() || edtMonto.getText().toString().isEmpty()){
                    Toast.makeText(view.getContext(), "Rellene los campos vacios", Toast.LENGTH_SHORT).show();
                }else if (tipAct.equals("Ingreso")){
                    monto=Double.parseDouble(edtMonto.getText().toString());
                    daoUsuario.agregarActividad(monto,fecha,idUsuarioGasto,getIdTipoActividad(tipAct),user.getUsuario());
                    Toast.makeText(view.getContext(), "Ingreso añadido", Toast.LENGTH_SHORT).show();
                    FragmentActividad fgtActividad=new FragmentActividad();
                    fgtActividad.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,fgtActividad)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                }else{
                    monto=Double.parseDouble(edtMonto.getText().toString());
                    daoUsuario.agregarActividad(monto,fecha,idUsuarioGasto,getIdTipoActividad(tipAct),user.getUsuario());
                    Toast.makeText(view.getContext(), "Gasto añadido", Toast.LENGTH_SHORT).show();
                    FragmentActividad fgtActividad=new FragmentActividad();
                    fgtActividad.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container,fgtActividad)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
    private void recuperarUsuario() {
        bundle = getArguments();
        user = (Usuario) bundle.getSerializable("user");
    }

    private int getIdTipoActividad(String descrip){
        ArrayList<String> listTipoAct=daoUsuario.getTipoActividad();
        ArrayList<Integer> listIdTipoAct= daoUsuario.getIdTipoActividad();
        int id=0;
        for (int i = 0; i < listIdTipoAct.size(); i++) {
            if(listTipoAct.get(i).equals(descrip)){
                id=listIdTipoAct.get(i);
            }
        }
        return id;
    }
    private ArrayList<String> getUsuariosAmigos(){
        ArrayList<String> listUsuariosAmigos=new ArrayList<>();
        ArrayList<Usuario> listAmigos= daoUsuario.misAmigos(user.getUsuario());
        listUsuariosAmigos.add(user.getUsuario());
        for (int i = 0; i < listAmigos.size(); i++) {
            listUsuariosAmigos.add(listAmigos.get(i).getUsuario());
        }
        return listUsuariosAmigos;
    }


}
