package com.example.act1_unidad10_miguelangelruizaguilar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Referencia a los botones
        Button buttonPreferencias = findViewById(R.id.button_preferencias);
        Button buttonAlmacenamiento = findViewById(R.id.button_almacenamiento);

        // Eventos de clic para botones
        buttonPreferencias.setOnClickListener(v -> {
            Intent intent = new Intent(this, PreferenciasActivity.class);
            startActivity(intent);
        });

        buttonAlmacenamiento.setOnClickListener(v -> {
            Intent intent = new Intent(this, AlmacenamientoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_preferencias:
                startActivity(new Intent(this, PreferenciasActivity.class));
                return true;
            case R.id.menu_almacenamiento:
                startActivity(new Intent(this, AlmacenamientoActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
