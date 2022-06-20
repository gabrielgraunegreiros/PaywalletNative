package com.usil.proyectomoviles.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.usil.proyectomoviles.entity.Grupo;
import com.usil.proyectomoviles.entity.Usuario;
import com.usil.proyectomoviles.util.ConstantesDB;
import com.usil.proyectomoviles.util.DBPaywallet;

import java.io.Serializable;
import java.util.ArrayList;

public class DAOUsuario implements Serializable {
    DBPaywallet dbPaywallet;
    SQLiteDatabase database;

    public DAOUsuario(Context context) {
        dbPaywallet = new DBPaywallet(context);
    }

    public void openDB(){
        database = dbPaywallet.getWritableDatabase();
    }

    public void closeDB(){
        dbPaywallet.close();
        database.close();
    }

    //------------Inicio algoritmos Usuario---------------
    public void registrarUsuario(Usuario u){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre",u.getNombre());
            contentValues.put("apellidos",u.getApellidos());
            contentValues.put("correo",u.getCorreo());
            contentValues.put("usuario",u.getUsuario());
            contentValues.put("contrasena",u.getContrasena());
            database.insert(ConstantesDB.TABLAUSUARIO,null,contentValues);
        }catch (Exception e){

        }
    }

    public void modificarDatos(Usuario u){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre",u.getNombre());
            contentValues.put("apellidos",u.getApellidos());
            contentValues.put("correo",u.getCorreo());
            contentValues.put("usuario",u.getUsuario());
            contentValues.put("contrasena",u.getContrasena());
            database.update(ConstantesDB.TABLAUSUARIO,contentValues,"usuario LIKE '"+u.getUsuario()+"'",null);
            /*Otra forma es:
            database.update(ConstantesDB.NOMBRETABLA,contentValues,"id="+p.getId(),null);*/
        } catch (Exception e){
        }
    }

    public void eliminarUsuario(String usuario){
        try{
            database.delete(ConstantesDB.TABLAUSUARIO,"usuario LIKE '"+usuario+"'",null);
        } catch (Exception e){

        }
    }

    public ArrayList<Usuario> getUsuario(){
        ArrayList<Usuario> listaUsu = new ArrayList<>();
        try {
            Cursor c = database.rawQuery("SELECT * FROM " + ConstantesDB.TABLAUSUARIO,null);
            while (c.moveToNext()){
                listaUsu.add(new Usuario(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
            }
        } catch (Exception e){
            return null;
        }
        return listaUsu;
    }

    public Usuario validarUsuario(String us, String contra){
        ArrayList<Usuario> listaUsu = new ArrayList<>();
        Usuario user=null;
        try {
            Cursor c = database.rawQuery("SELECT * FROM " + ConstantesDB.TABLAUSUARIO,null);
            while (c.moveToNext()){
                listaUsu.add(new Usuario(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
            }
            for (Usuario u: listaUsu) {
                if(u.getUsuario().equals(us) && u.getContrasena().equals(contra)){
                    user=u;
                }
            }
            return user;
        } catch (Exception e){
            return user;
        }
    }

    public Usuario getUser(String us){
        Usuario user=null;
        ArrayList<Usuario> listaUsuarios=new ArrayList<>();
        try {
            Cursor c = database.rawQuery("SELECT * FROM " + ConstantesDB.TABLAUSUARIO,null);
            while (c.moveToNext()){
                listaUsuarios.add(new Usuario(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
            }
            for (Usuario u: listaUsuarios) {
                if(u.getUsuario().equals(us)){
                    user=u;
                }
            }
            return user;
        }catch (Exception e){
            return user;
        }
    }

    //------------Fin algoritmos Usuario---------------
    //------------Inicio algoritmos Grupo---------------
    public void registrarGrupo(Grupo g, Usuario u){
        try {
            ContentValues contentValuesGrupo = new ContentValues();
            contentValuesGrupo.put("nombreGrupo",g.getNombreGrupo());
            database.insert(ConstantesDB.TABLAGRUPO,null,contentValuesGrupo);
            //obtener el id del grupo creado recientemente, last_insert_rowid() devuelve devuelve el ROWID de la última fila insertada desde la conexión de la base de datos que invocó la función
            int idG=0;
            Cursor c = database.rawQuery("SELECT last_insert_rowid()",null);
            while (c.moveToNext()){
                idG=c.getInt(0);
            }
            //-----------------
            ContentValues contentValuesGrupo_Usuario = new ContentValues();
            contentValuesGrupo_Usuario.put("idGrupo",idG);
            contentValuesGrupo_Usuario.put("idUsuario",u.getUsuario());

            database.insert(ConstantesDB.TABLAGRUPO_USUARIO,null,contentValuesGrupo_Usuario);
        }catch (Exception e){
        }
    }

    public ArrayList<Grupo> getGrupos(Usuario u){
        ArrayList<Grupo> listaGrupos=new ArrayList<>();
        try {
            String sql="SELECT TGU.idGrupo,TG.nombreGrupo from TGrupo_Usuario TGU " +
                    "inner join TGrupo TG " +
                    "on TG.id=TGU.idgrupo " +
                    "where TGU.idUsuario " +
                    "like '"+u.getUsuario()+"'";
            Cursor c = database.rawQuery(sql,null);
            while (c.moveToNext()){
                listaGrupos.add(new Grupo(c.getInt(0),c.getString(1)));
            }
            return listaGrupos;
        }catch (Exception e){
            return listaGrupos;
        }
    }
    //------------Fin algoritmos Grupo---------------
}
