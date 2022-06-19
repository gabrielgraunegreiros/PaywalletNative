package com.usil.proyectomoviles.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
