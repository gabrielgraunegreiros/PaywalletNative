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
        sqLiteDatabase.execSQL("CREATE TABLE " + ConstantesDB.NOMBRETABLA +
                "(nombre text not null,"+
                "apellidos text not null,"+
                "correo text not null,"+
                "usuario text PRIMARY KEY,"+
                "contrasena text not null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
