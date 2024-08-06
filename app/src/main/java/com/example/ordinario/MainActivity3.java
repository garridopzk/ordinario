package com.example.ordinario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.ordinario.db.OrdinarioBd;

public class MainActivity3 extends AppCompatActivity {

    private OrdinarioBd dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        dbHelper = new OrdinarioBd(this);

        Button btnInsertar = findViewById(R.id.btnInsertar);
        Button btnEliminar = findViewById(R.id.btnEliminar);
        Button btnActualizar = findViewById(R.id.btnActualizar);

        btnInsertar.setOnClickListener(v -> showInsertDialog());
        btnEliminar.setOnClickListener(v -> showDeleteDialog());
        btnActualizar.setOnClickListener(v -> showUpdateDialog());
    }

    private void showInsertDialog() {
        InsertDialog dialog = new InsertDialog();
        dialog.show(getSupportFragmentManager(), "insert_dialog");
    }

    private void showDeleteDialog() {
        DeleteDialog dialog = new DeleteDialog();
        dialog.show(getSupportFragmentManager(), "delete_dialog");
    }

    private void showUpdateDialog() {
        UpdateDialog dialog = new UpdateDialog();
        dialog.show(getSupportFragmentManager(), "update_dialog");
    }

    // InsertDialog class
    public static class InsertDialog extends DialogFragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_insert, container, false);

            EditText nombreInput = view.findViewById(R.id.nombre_input);
            EditText precioInput = view.findViewById(R.id.precio_input);
            EditText cantidadInput = view.findViewById(R.id.cantidad_input);
            Button btnInsertar = view.findViewById(R.id.btn_insertar);

            btnInsertar.setOnClickListener(v -> {
                String nombre = nombreInput.getText().toString();
                double precio = Double.parseDouble(precioInput.getText().toString());
                int cantidad = Integer.parseInt(cantidadInput.getText().toString());

                OrdinarioBd dbHelper = new OrdinarioBd(getActivity());
                dbHelper.insertarProducto(nombre, precio, cantidad, null, null);

                Toast.makeText(getActivity(), "Producto insertado", Toast.LENGTH_SHORT).show();
                dismiss();
            });

            return view;
        }
    }

    // DeleteDialog class
    public static class DeleteDialog extends DialogFragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_delete, container, false);

            EditText idInput = view.findViewById(R.id.id_input);
            Button btnEliminar = view.findViewById(R.id.btn_eliminar);

            btnEliminar.setOnClickListener(v -> {
                int id = Integer.parseInt(idInput.getText().toString());

                OrdinarioBd dbHelper = new OrdinarioBd(getActivity());
                dbHelper.eliminarProducto(id);

                Toast.makeText(getActivity(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                dismiss();
            });

            return view;
        }
    }

    // UpdateDialog class
    public static class UpdateDialog extends DialogFragment {

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_update, container, false);

            EditText idInput = view.findViewById(R.id.id_input);
            EditText precioInput = view.findViewById(R.id.precio_input);
            EditText cantidadInput = view.findViewById(R.id.cantidad_input);
            Button btnActualizar = view.findViewById(R.id.btn_actualizar);

            btnActualizar.setOnClickListener(v -> {
                int id = Integer.parseInt(idInput.getText().toString());
                double precio = Double.parseDouble(precioInput.getText().toString());
                int cantidad = Integer.parseInt(cantidadInput.getText().toString());

                OrdinarioBd dbHelper = new OrdinarioBd(getActivity());
                dbHelper.actualizarProducto(id, precio, cantidad);

                Toast.makeText(getActivity(), "Producto actualizado", Toast.LENGTH_SHORT).show();
                dismiss();
            });

            return view;
        }
    }
}
