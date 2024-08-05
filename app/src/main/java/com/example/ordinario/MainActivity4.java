package com.example.ordinario;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordinario.db.DatabaseHelper;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ListView listViewProductos;
    ArrayList<String> productosList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        dbHelper = new DatabaseHelper(this);
        listViewProductos = findViewById(R.id.listViewProductos);
        productosList = new ArrayList<>();

        loadProductos();

        listViewProductos.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            showCantidadDialog(selectedItem);
        });

        Button btnFinalizarVenta = findViewById(R.id.btnFinalizarVenta);
        btnFinalizarVenta.setOnClickListener(v -> finalizarVenta());
    }

    private void loadProductos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(DatabaseHelper.TABLE_PRODUCTOS, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    String producto = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)) + ": "
                            + cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)) + " - "
                            + cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_PRECIO));
                    productosList.add(producto);
                } while (cursor.moveToNext());
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productosList);
            listViewProductos.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void showCantidadDialog(String producto) {
        // Implementa el diálogo para ingresar la cantidad
    }

    private void finalizarVenta() {
        // Implementa la lógica para finalizar la venta
    }
}
