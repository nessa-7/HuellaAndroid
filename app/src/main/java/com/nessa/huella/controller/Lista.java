package com.nessa.huella.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.nessa.huella.R;
import com.nessa.huella.model.Manager;

import java.util.List;

public class Lista extends AppCompatActivity {

    Button btnvolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        ListView listView = findViewById(R.id.lista);

        Manager manager = new Manager(this);
        List<String> datos = manager.getActividadesTexto();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        datos);

        listView.setAdapter(adapter);

        btnvolver = findViewById(R.id.btnvolver);

        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lista.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}