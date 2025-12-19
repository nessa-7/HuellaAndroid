package com.nessa.huella.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Manager {

    private ConexionBd conexionBd;
    private SQLiteDatabase db;

    public Manager(Context context){
        conexionBd = new ConexionBd(context);
    }

    private void openBdWr(){
        db = conexionBd.getWritableDatabase();
    }

    private void openBdRd(){
        db = conexionBd.getReadableDatabase();
    }

    private void closeBd(){
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // ================== INSERTAR ==================
    public long insertActividad(ActividadCarbono actividadCarbono) {

        openBdWr();

        ContentValues values = new ContentValues();
        values.put("TIPOACTIVIDAD", actividadCarbono.getTipo());
        values.put("CANTIDAD", actividadCarbono.getCantidad());
        values.put("EMISIONCO2", actividadCarbono.getEmision());
        values.put("FECHA", actividadCarbono.getFecha().getTime());

        long idGenerado = db.insert("ACTIVIDAD_CARBONO", null, values);

        closeBd();
        return idGenerado;
    }

    // ================== LISTAR ==================
    public List<String> getActividadesTexto() {

        List<String> lista = new ArrayList<>();
        openBdRd();

        Cursor c = db.rawQuery(
                "SELECT TIPOACTIVIDAD, CANTIDAD, EMISIONCO2, FECHA FROM ACTIVIDAD_CARBONO",
                null
        );

        // FORMATO FECHA
        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        if (c.moveToFirst()) {
            do {
                String tipo = c.getString(0);
                int cantidad = c.getInt(1);
                double emision = c.getDouble(2);

                long fechaMillis = c.getLong(3);
                String fecha = sdf.format(new Date(fechaMillis));

                String texto =
                        tipo +
                                " | Cantidad: " + cantidad +
                                " | Emisión: " + emision +
                                " | Fecha: " + fecha;

                lista.add(texto);

            } while (c.moveToNext());
        }

        c.close();
        closeBd();

        return lista;
    }
}
