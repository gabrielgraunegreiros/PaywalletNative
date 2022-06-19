package com.usil.proyectomoviles.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBPaywallet extends SQLiteOpenHelper {
    public DBPaywallet(@Nullable Context context) {
        super(context, ConstantesDB.NOMBREDB, null, ConstantesDB.VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ConstantesDB.TABLAUSUARIO +
                "(nombre text not null,"+
                "apellidos text not null,"+
                "correo text not null,"+
                "usuario text PRIMARY KEY,"+
                "contrasena text not null);");

        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE "+ConstantesDB.TABLAGRUPO +""+
                                "("+
                                "id integer PRIMARY KEY AUTOINCREMENT,"+
                                "nombreGrupo text not null"+
                                ");"
                );

        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE "+ConstantesDB.TABLAGRUPO_USUARIO +""+
                                "("+
                                "id integer PRIMARY KEY AUTOINCREMENT,"+
                                "idGrupo integer not null,"+
                                "idUsuario integer not null,"+
                                "FOREIGN KEY(idGrupo) REFERENCES "+ConstantesDB.TABLAGRUPO+"(id),"+
                                "FOREIGN KEY(idUsuario) REFERENCES "+ConstantesDB.TABLAUSUARIO+"(usuario)"+
                                ");"
                );

        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE "+ConstantesDB.TABLAAMIGO +""+
                                "("+
                                "id integer PRIMARY KEY AUTOINCREMENT,"+
                                "idUsuario1 integer not null,"+
                                "idUsuario2 integer not null,"+
                                "solicitudEstado text not null,"+
                                "FOREIGN KEY(idUsuario1) REFERENCES "+ConstantesDB.TABLAUSUARIO+"(usuario),"+
                                "FOREIGN KEY(idUsuario2) REFERENCES "+ConstantesDB.TABLAUSUARIO+"(usuario)"+
                                ");"
                );

        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE "+ConstantesDB.TABLATIPOACTIVIDAD +""+
                                "("+
                                "id integer PRIMARY KEY AUTOINCREMENT,"+
                                "descripcion text not null"+
                                ");"
                );
        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE "+ConstantesDB.TABLAACTIVIDAD +""+
                                "("+
                                "id integer PRIMARY KEY AUTOINCREMENT,"+
                                "monto real not null,"+
                                "solicitudEstado text not null,"+
                                "idTipoActividad integer not null,"+
                                "FOREIGN KEY(idTipoActividad) REFERENCES "+ConstantesDB.TABLATIPOACTIVIDAD+"(id)"+
                                ");"
                );
        sqLiteDatabase.execSQL
                (
                        "CREATE TABLE "+ConstantesDB.TABLAUSER_ACTIVITY +""+
                                "("+
                                "id integer PRIMARY KEY AUTOINCREMENT,"+
                                "idUsuario integer not null,"+
                                "idActividad integer not null,"+
                                "FOREIGN KEY(idUsuario) REFERENCES "+ConstantesDB.TABLAUSUARIO+"(usuario),"+
                                "FOREIGN KEY(idActividad) REFERENCES "+ConstantesDB.TABLAACTIVIDAD+"(id)"+
                                ");"
                );
        //Insercion valores predeterminados
        sqLiteDatabase.execSQL
                (
                        "INSERT INTO "+ConstantesDB.TABLATIPOACTIVIDAD+"(descripcion)"+" VALUES('debes');"
                );
        sqLiteDatabase.execSQL
                (
                        "INSERT INTO "+ConstantesDB.TABLATIPOACTIVIDAD+"(descripcion)"+" VALUES('te debe');"
                );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
