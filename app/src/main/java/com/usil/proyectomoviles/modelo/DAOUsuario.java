package com.usil.proyectomoviles.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.usil.proyectomoviles.entity.Actividad;
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
        //devuelve un arreglo con todos los usuarios registrados en la BD
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
        //devuelve el objeto Usuario con DATOS, si encuentra su usario "us" y contraseña "contra"
        //en caso no lo encuentre, devuelve el objeto usuario NULO
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
        //Devuelve el objeto Usuario con todos sus datos en base al parametro usuario "us"
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
    public boolean existeUsuario(String idUsuario){
        //validar que el usuario existe en la bd,
        // si estado=false(no existe usuario en la BD)
        // si estado=True(usuario existe en la bd)
        boolean estado=false;
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLAUSUARIO+" where usuario like '"+idUsuario+"'";
            Cursor c=database.rawQuery(sql,null);
            if((c != null) && (c.getCount() > 0)){
                estado=true;
            }
            return estado;
        }catch (Exception e){
            return estado;
        }
    }
    //------------Fin algoritmos Usuario---------------
    //------------Inicio algoritmos Grupo y Grupo_Usuario---------------
    public void registrarGrupo(Grupo g, Usuario u){
        //se registrar un nuevo grupo con el creados en las tablas TGrupo, y TGrupo_Usuario, respectivamente
        try {
            //Se registra un nuevo grupo en la bd en la tabla TGrupo
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
            //Se asocia el grupo creado anteriormente con el usuario que lo creo
            ContentValues contentValuesGrupo_Usuario = new ContentValues();
            contentValuesGrupo_Usuario.put("idGrupo",idG);
            contentValuesGrupo_Usuario.put("idUsuario",u.getUsuario());

            database.insert(ConstantesDB.TABLAGRUPO_USUARIO,null,contentValuesGrupo_Usuario);
        }catch (Exception e){
        }
    }

    public ArrayList<Grupo> getGrupos(Usuario u){
        //devuelve un arreglo de tipo GRUPO con todos los grupos en los que se encuentra el usuario "u"
        ArrayList<Grupo> listaGrupos=new ArrayList<>();
        try {
            String sql="SELECT TGU.idGrupo,TG.nombreGrupo from "+ConstantesDB.TABLAGRUPO_USUARIO+" TGU " +
                    "inner join "+ConstantesDB.TABLAGRUPO+" TG " +
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
    public Grupo getGrupo(int idGrupo){
        //devuelve el objeto Grupo con todos sus datos, tomando como parametro su id "idGrupo"
        Grupo grupo=null;
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLAGRUPO+ " where id="+idGrupo;
            Cursor c=database.rawQuery(sql,null);
            while (c.moveToNext()){
                grupo=new Grupo(c.getInt(0), c.getString(1));
            }
            return grupo;
        }catch (Exception e){
            return grupo;
        }
    }
    public void eliminarGrupo(int idGrupo, Usuario u){
        //Falta realizar validaciones
        //Elimina el grupo con id "idGrupo" y la relacion con su creador con el usuario "u"
        try{
            database.delete(ConstantesDB.TABLAGRUPO,"id="+idGrupo,null);
            database.delete(ConstantesDB.TABLAGRUPO_USUARIO,"idGrupo="+idGrupo+" AND idUsuario like '"+u.getUsuario()+"'",null);
        } catch (Exception e){

        }
    }
    public void cambiarNombreGrupo(int idGrupo, String nombreG){
        //Cambia el nombre del grupo de id "idGrupo" y le da el nombre "nombreG"
        try {
            ContentValues grupo = new ContentValues();
            grupo.put("nombreGrupo",nombreG);
            database.update(ConstantesDB.TABLAGRUPO,grupo,"id="+idGrupo,null);
        }catch (Exception e){
        }
    }

    public ArrayList<Usuario> getUsuarios_de_Grupo(int idGrupo){
        //Devuelve un arreglo con los usuarios que se encuentran dentro del grupo con el id ="idGrupo"
        ArrayList<Usuario> listaUser_Grupo=new ArrayList<>();
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLAGRUPO_USUARIO+ " where idGrupo="+idGrupo;
            Cursor c=database.rawQuery(sql,null);
            while (c.moveToNext()){
                listaUser_Grupo.add(getUser(c.getString(2)));
            }
            return listaUser_Grupo;
        }catch (Exception e){
            return listaUser_Grupo;
        }
    }

    public void añadirParticipanteGrupo(int idGrupo, String idUser, Context context){
        //agrega el usuario con el id "idUser" al grupo cuyo id es "idGrupo"
        ArrayList<Usuario> listaUsuariosDeGrupo=getUsuarios_de_Grupo(idGrupo);
        boolean estado=false;
        try {
            for (int i = 0; i < listaUsuariosDeGrupo.size(); i++) {
                if(listaUsuariosDeGrupo.get(i).getUsuario().equals(idUser)){
                    estado=true;
                    break;
                }
            }
            if(estado){
                Toast.makeText(context, "Participante ya agregado", Toast.LENGTH_SHORT).show();
            }else{
                ContentValues contGrupo_Usuario = new ContentValues();
                contGrupo_Usuario.put("idGrupo",idGrupo);
                contGrupo_Usuario.put("idUsuario",idUser);
                database.insert(ConstantesDB.TABLAGRUPO_USUARIO,null,contGrupo_Usuario);
                Toast.makeText(context, "Usuario agregado al grupo", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
    }
    //------------Fin algoritmos Grupo---------------
    //--------------Inicio algoritmos Amigos-----------
    public void agregarAmigo(String idAmigo1, String idAmigo2, Context context){
        /*
        idAmigo1: Envia la solicitud
        idAmigo2: Recibe la solicitud
        Solicitud: Pendiente (Falta confirmar amigo)
                    Confirmado (Ya son amigos)
        */
        //Si estado es false, no existe registro de que idAmigo1 y idAmigo2 son amigos;
        // caso contrario, si lo son o esta "Pendiente"
        boolean estado=validarSolicitud(idAmigo1,idAmigo2);
        //--------------
        try {
            if(estado){
                Toast.makeText(context, "Amigo ya agregado", Toast.LENGTH_SHORT).show();
            }else{
                ContentValues contentValuesAmigos = new ContentValues();
                contentValuesAmigos.put("idUsuario1",idAmigo1);
                contentValuesAmigos.put("idUsuario2",idAmigo2);
                contentValuesAmigos.put("solicitudEstado","Pendiente");
                database.insert(ConstantesDB.TABLAAMIGO,null,contentValuesAmigos);
                Toast.makeText(context, "Se mando la solicitud a "+idAmigo2, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
    }

    public ArrayList<Usuario> getSolicitudesAmistad(String idUsuario){
        //Obtener todas las solicitudes en estado "Pendiente" del usuario logeado con id "idUsuario"
        ArrayList<Usuario> listaUsuarios=new ArrayList<>();
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLAAMIGO+" where idUsuario2 like '"+idUsuario+"'"+" AND solicitudEstado like 'Pendiente'";
            Cursor c=database.rawQuery(sql,null);
            while (c.moveToNext()){
                listaUsuarios.add( getUser(c.getString(1)));
            }
            return listaUsuarios;
        }catch (Exception e){
            return listaUsuarios;
        }
    }
    public void aceptarAmigo(String userAmigo1,String userAmigo2){
        /*
        userAmigo1: Envia la solicitud
        userAmigo2: Recibe la solicitud
        Solicitud: Pendiente (Falta confirmar amigo)
                    Confirmado (Ya son amigos)
         */
        //Cambia el campo solicitudEstado de la tabla TAmigo de "Pendiente" a "Confirmado"
        //del usuario con id(userAmigo1) con el usuario con id(userAmigo2)
        try {
            ContentValues amigo = new ContentValues();
            amigo.put("solicitudEstado","Confirmado");
            database.update(ConstantesDB.TABLAAMIGO,amigo,"idUsuario1 like '"+userAmigo1+"' AND idUsuario2 like '"+userAmigo2+"'",null);
        }catch (Exception e){

        }
    }
    public boolean validarSolicitud(String u1, String u2){
        //Validar que no se envia solicitud de amistad a un usuario que ya ha sido agregado como amigo o que ya se le envio la solicitud
        //Si estado es false, no existe registro de que u1 y u2 son amigos, caso contrario, si lo son o esta "Pendiente"
        boolean estado=false;
        try {
            String sql1="SELECT * FROM "+ConstantesDB.TABLAAMIGO+" where idUsuario1 like '"+u1+"' AND idUsuario2 like '"+u2+"'";
            String sql2="SELECT * FROM "+ConstantesDB.TABLAAMIGO+" where idUsuario1 like '"+u2+"' AND idUsuario2 like '"+u1+"'";
            Cursor c=database.rawQuery(sql1,null);
            Cursor c2=database.rawQuery(sql2,null);
            if((c != null) && (c.getCount() > 0)){
                estado=true;
            }
            if((c2 != null) && (c2.getCount() > 0)){
                estado=true;
            }
            return estado;
        }catch (Exception e){
            return estado;
        }
    }
    public ArrayList<Usuario> misAmigos(String idUsuario){
        //devuelve un arreglo de todos los usurios en estado Confirmado de "idUsuario" (usuario logeado)
        ArrayList<Usuario> listaAmigos=new ArrayList<>();
        try {
            String sql1="SELECT * FROM "+ConstantesDB.TABLAAMIGO+" where idUsuario1 like '"+idUsuario+"' AND solicitudEstado like 'Confirmado'";
            String sql2="SELECT * FROM "+ConstantesDB.TABLAAMIGO+" where idUsuario2 like '"+idUsuario+"' AND solicitudEstado like 'Confirmado'";
            Cursor cur1=database.rawQuery(sql1,null);
            Cursor cur2=database.rawQuery(sql2,null);
            while(cur1.moveToNext()){
                listaAmigos.add( getUser(cur1.getString(2)));
            }
            while(cur2.moveToNext()){
                listaAmigos.add( getUser(cur2.getString(1)));
            }
            return listaAmigos;
        }catch (Exception e){
            return listaAmigos;
        }
    }
    public boolean isAmigo(String userLog, String userAmigo){
        //estado=true:Son amigos
        //estado=false: No son amigos
        boolean estado=false;
        ArrayList<Usuario> listaMisAmigos;
        try {
            listaMisAmigos=misAmigos(userLog);
            for (int i = 0; i <listaMisAmigos.size(); i++) {
                if(listaMisAmigos.get(i).getUsuario().equals(userAmigo)){
                    estado=true;
                }
            }
            return estado;
        }catch (Exception e){
            return estado;
        }
    }
    //--------------Fin algoritmos Amigos-----------

    //-----------Inicio algoritmos User_Activity-----------
    public ArrayList<Integer> getIdMiActividad(String usuario){
        //Devuelve un arreglo con los ids de las actividades del "usuario" logeado obtenidos de la tabla TUser_Activity
        ArrayList<Integer> listaIdsMiActividad=new ArrayList<>();
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLAUSER_ACTIVITY+" where idUsuario like '"+usuario+"'";
            Cursor cursor=database.rawQuery(sql,null);
            while (cursor.moveToNext()){
                listaIdsMiActividad.add(cursor.getInt(2));
            }
            return listaIdsMiActividad;
        }catch (Exception e){
            return listaIdsMiActividad;
        }
    }
    //-----------Fin algoritmos User_Activity-----------

    //--------------Inicio algoritmos Actividad---------

    public void agregarActividad(double monto, String fecha,String idUsuarioGasto, int idTipoActividad, String userLog){
        int idActividad=0;
        try {
            if (idUsuarioGasto.equals(userLog)){
                ContentValues contActividad_User_Registra = new ContentValues();
                contActividad_User_Registra.put("monto",monto);
                contActividad_User_Registra.put("fecha",fecha);
                contActividad_User_Registra.put("idUsuarioGasto",idUsuarioGasto);
                contActividad_User_Registra.put("estado","Pendiente");
                contActividad_User_Registra.put("idTipoActividad",idTipoActividad);
                database.insert(ConstantesDB.TABLAACTIVIDAD,null,contActividad_User_Registra);
                Cursor c = database.rawQuery("SELECT last_insert_rowid()",null);
                while (c.moveToNext()){
                    idActividad=c.getInt(0);
                }
                ContentValues contUser_Registra=new ContentValues();
                contUser_Registra.put("idUsuario", userLog);
                contUser_Registra.put("idActividad", idActividad);
                database.insert(ConstantesDB.TABLAUSER_ACTIVITY,null,contUser_Registra);
            }else {
                ContentValues contActividad_User_Registra = new ContentValues();
                contActividad_User_Registra.put("monto",monto);
                contActividad_User_Registra.put("fecha",fecha);
                contActividad_User_Registra.put("idUsuarioGasto",idUsuarioGasto);
                contActividad_User_Registra.put("estado","Pendiente");
                contActividad_User_Registra.put("idTipoActividad",idTipoActividad);
                database.insert(ConstantesDB.TABLAACTIVIDAD,null,contActividad_User_Registra);
                Cursor c = database.rawQuery("SELECT last_insert_rowid()",null);
                while (c.moveToNext()){
                    idActividad=c.getInt(0);
                }
                ContentValues contUser_Registra=new ContentValues();
                contUser_Registra.put("idUsuario", userLog);
                contUser_Registra.put("idActividad", idActividad);
                database.insert(ConstantesDB.TABLAUSER_ACTIVITY,null,contUser_Registra);

                if(idTipoActividad==1){
                    idTipoActividad=2;
                }else{
                    idTipoActividad=1;
                }
                ContentValues contActividad_User_Gasto = new ContentValues();
                contActividad_User_Gasto.put("monto",monto);
                contActividad_User_Gasto.put("fecha",fecha);
                contActividad_User_Gasto.put("idUsuarioGasto",userLog);
                contActividad_User_Gasto.put("estado","Pendiente");
                contActividad_User_Gasto.put("idTipoActividad",idTipoActividad);
                database.insert(ConstantesDB.TABLAACTIVIDAD,null,contActividad_User_Gasto);
                Cursor cursor = database.rawQuery("SELECT last_insert_rowid()",null);
                while (cursor.moveToNext()){
                    idActividad=cursor.getInt(0);
                }
                ContentValues contUser_Gasto=new ContentValues();
                contUser_Gasto.put("idUsuario", idUsuarioGasto);
                contUser_Gasto.put("idActividad", idActividad);
                database.insert(ConstantesDB.TABLAUSER_ACTIVITY,null,contUser_Gasto);
            }

        }catch (Exception e){

        }
    }
    public ArrayList<Actividad> getMiActividad(String idUsuario){
        ArrayList<Actividad> listaMiActividad=new ArrayList<>();
        ArrayList<Integer> listaIdsActividad=getIdMiActividad(idUsuario);
        String sql;
        try {
            for (int i = 0; i < listaIdsActividad.size(); i++) {
                sql="SELECT * FROM "+ConstantesDB.TABLAACTIVIDAD+" where id="+listaIdsActividad.get(i);
                Cursor cur=database.rawQuery(sql,null);
                while (cur.moveToNext()){
                    listaMiActividad.add(new Actividad(cur.getInt(0),cur.getDouble(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getInt(5)));
                }
            }
            return listaMiActividad;
        }catch (Exception e){
            return listaMiActividad;
        }
    }
    public double obtenerBalanceDinero(String user){
        double dineroTotal=0.0;
        ArrayList<Actividad> listaMisActividades;
        try {
            listaMisActividades=getMiActividad(user);
            for (int i = 0; i < listaMisActividades.size(); i++) {
                if(listaMisActividades.get(i).getIdTipoActividad()==1){
                    dineroTotal-=listaMisActividades.get(i).getMonto();
                }else{
                    dineroTotal+=listaMisActividades.get(i).getMonto();
                }
            }
            return dineroTotal;
        }catch (Exception e){
            return dineroTotal;
        }
    }

    //--------------Fin algoritmos Actividad---------
    //--------------Inicio algoritmos Tipo_Actividad---------
    public ArrayList<String> getTipoActividad(){
        //devuelve los datos de la tabla TipoActividad
        ArrayList<String> listaTipoActividad=new ArrayList<>();
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLATIPOACTIVIDAD;
            Cursor c=database.rawQuery(sql,null);
            while (c.moveToNext()){
                listaTipoActividad.add(c.getString(1));
            }
            return listaTipoActividad;
        }catch (Exception e){
            return listaTipoActividad;
        }
    }
    public String getDescripTipoActividad(int idTipoAct){
        String des=null;
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLATIPOACTIVIDAD+" where id="+idTipoAct;
            Cursor c=database.rawQuery(sql,null);
            while (c.moveToNext()){
                des=c.getString(1);
            }
            return des;
        }catch (Exception e){
            return des;
        }
    }
    public ArrayList<Integer> getIdTipoActividad(){
        //devuelve los datos de la tabla TipoActividad
        ArrayList<Integer> listaIdTipoActividad=new ArrayList<>();
        try {
            String sql="SELECT * FROM "+ConstantesDB.TABLATIPOACTIVIDAD;
            Cursor c=database.rawQuery(sql,null);
            while (c.moveToNext()){
                listaIdTipoActividad.add(c.getInt(0));
            }
            return listaIdTipoActividad;
        }catch (Exception e){
            return listaIdTipoActividad;
        }
    }

    //--------------Fin algoritmos Tipo_Actividad---------
}
