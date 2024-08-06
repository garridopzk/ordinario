package com.example.ordinario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ordinario.db.OrdinarioBd;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    OrdinarioBd dbHelper;
    ListView listViewProductos;
    ArrayList<String> productosList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        dbHelper = new OrdinarioBd(this);
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
            cursor = db.query(OrdinarioBd.TABLE_PRODUCTOS, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    int idColumnIndex = cursor.getColumnIndex(OrdinarioBd.COLUMN_ID);
                    int nombreColumnIndex = cursor.getColumnIndex(OrdinarioBd.COLUMN_NOMBRE);
                    int precioColumnIndex = cursor.getColumnIndex(OrdinarioBd.COLUMN_PRECIO);

                    if (idColumnIndex != -1 && nombreColumnIndex != -1 && precioColumnIndex != -1) {
                        String producto = cursor.getInt(idColumnIndex) + ": "
                                + cursor.getString(nombreColumnIndex) + " - "
                                + cursor.getDouble(precioColumnIndex);
                        productosList.add(producto);
                    }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingrese la cantidad");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String cantidad = input.getText().toString();
            // Implementa la lógica para manejar la cantidad ingresada
            Toast.makeText(MainActivity4.this, "Producto: " + producto + "\nCantidad: " + cantidad, Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void finalizarVenta() {
        // Implementa la lógica para finalizar la venta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Venta");
        builder.setMessage("¿Está seguro de que desea finalizar la venta?");

        builder.setPositiveButton("Sí", (dialog, which) -> {
            // Lógica para finalizar la venta (por ejemplo, actualizar la base de datos)
            Toast.makeText(MainActivity4.this, "Venta finalizada", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
