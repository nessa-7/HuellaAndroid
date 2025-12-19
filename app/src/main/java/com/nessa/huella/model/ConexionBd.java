package com.nessa.huella.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionBd extends SQLiteOpenHelper {


    public ConexionBd(@Nullable Context context) {
        super(context, Constantes.Name_BD, null, Constantes.VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE ACTIVIDAD_CARBONO (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "TIPOACTIVIDAD TEXT, " +
                        "CANTIDAD INTEGER, " +
                        "EMISIONCO2 REAL, " +
                        "FECHA INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ACTIVIDAD_CARBONO");
        onCreate(db);
    }
}
