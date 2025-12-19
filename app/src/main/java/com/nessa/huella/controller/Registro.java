package com.nessa.huella.controller;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nessa.huella.R;
import com.nessa.huella.model.ActividadCarbono;
import com.nessa.huella.model.Manager;

import java.util.Date;
import java.util.Locale;

public class Registro extends AppCompatActivity {


    private EditText edtCantidad;
    private Spinner spTipo;
    private Button btnRegistrarActividad, btnvolver;
    private TextView tvFecha, tvEmisiones;

    private Manager manager;
    private Date fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        btnvolver = findViewById(R.id.btnvolver);

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        edtCantidad = findViewById(R.id.edtCantidad);
        tvFecha = findViewById(R.id.tvFecha);
        tvEmisiones = findViewById(R.id.tvEmisiones);
        spTipo = findViewById(R.id.SpTipo);
        btnRegistrarActividad = findViewById(R.id.btnregistrar);


        manager = new Manager(this);

        // 📅 Fecha automática
        fecha = new Date();
        tvFecha.setText(
                android.text.format.DateFormat.format("yyyy-MM-dd", fecha)
        );

        btnRegistrarActividad.setEnabled(false);
        tvEmisiones.setText("0.00 kg CO₂");

        // 🔁 Spinner
        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualizarEmisiones();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 🔁 Cantidad
        edtCantidad.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validarFormulario();
                actualizarEmisiones();
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        // 💾 Guardar
        btnRegistrarActividad.setOnClickListener(v -> guardarActividad());
    }

    // ✅ Validación
    private void validarFormulario() {
        String cantidadTexto = edtCantidad.getText().toString().trim();

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            btnRegistrarActividad.setEnabled(cantidad > 0);
        } catch (NumberFormatException e) {
            btnRegistrarActividad.setEnabled(false);
        }
    }

    // 💾 Guardar actividad
    private void guardarActividad() {

        String cantidadTexto = edtCantidad.getText().toString().trim();
        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese una cantidad válida", Toast.LENGTH_SHORT).show();
            return;
        }

        int tipoPosicion = spTipo.getSelectedItemPosition();
        String tipo = spTipo.getSelectedItem().toString();
        double emisiones = calcularEmisiones(tipoPosicion, cantidad);

        ActividadCarbono actividad =
                new ActividadCarbono(tipo, cantidad, emisiones, fecha);

        long result = manager.insertActividad(actividad);

        if (result > 0) {
            Toast.makeText(this, "Actividad registrada", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error al insertar datos", Toast.LENGTH_SHORT).show();
        }
    }

    // 🔢 Cálculo por posición del Spinner
    private double calcularEmisiones(int position, int cantidad) {
        switch (position) {
            case 0: // Transporte
                return cantidad * 0.21;
            case 1: // Energía
                return cantidad * 0.38;
            case 2: // Alimentación
                return cantidad * 2.5;
            default:
                return 0;
        }
    }

    // 🔄 Actualizar UI
    private void actualizarEmisiones() {

        String cantidadTexto = edtCantidad.getText().toString().trim();

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            int position = spTipo.getSelectedItemPosition();

            double emisiones = calcularEmisiones(position, cantidad);

            tvEmisiones.setText(
                    String.format(Locale.getDefault(), "%.2f kg CO₂", emisiones)
            );
        } catch (NumberFormatException e) {
            tvEmisiones.setText("0.00 kg CO₂");
        }
    }



}
