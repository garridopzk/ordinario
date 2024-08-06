package com.example.ordinario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private Button btnVenta;
    private Button btnInventario;
    private Button btnSakir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnVenta = findViewById(R.id.btnVenta);
        Button btnInventario = findViewById(R.id.btnInventario);
        Button btnSalir = findViewById(R.id.btnSalir);

        btnVenta.setOnClickListener(v -> startActivity(new Intent(MainActivity2.this, MainActivity4.class)));

        btnInventario.setOnClickListener(v -> startActivity(new Intent(MainActivity2.this, MainActivity3.class)));

        btnSalir.setOnClickListener(v -> finish());
    }
}
