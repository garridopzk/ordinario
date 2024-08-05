package com.example.ordinario;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ordinario.db.DatabaseHelper;

public class MainActivity3 extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        dbHelper = new DatabaseHelper(this);

        Button btnInsertar = findViewById(R.id.btnInsertar);
        Button btnEliminar = findViewById(R.id.btnEliminar);
        Button btnActualizar = findViewById(R.id.btnActualizar);

        btnInsertar.setOnClickListener(v -> showInsertDialog());
        btnEliminar.setOnClickListener(v -> showDeleteDialog());
        btnActualizar.setOnClickListener(v -> showUpdateDialog());
    }

    private void showInsertDialog() {
        // Implementa el diálogo para insertar productos en tu base de datos
        // Por ejemplo:
        // new InsertDialog().show(getSupportFragmentManager(), "insert_dialog");
    }

    private void showDeleteDialog() {
        // Implementa el diálogo para eliminar productos de tu base de datos
        // Por ejemplo:
        // new DeleteDialog().show(getSupportFragmentManager(), "delete_dialog");
    }

    private void showUpdateDialog() {
        // Implementa el diálogo para actualizar productos en tu base de datos
        // Por ejemplo:
        // new UpdateDialog().show(getSupportFragmentManager(), "update_dialog");
    }
}
