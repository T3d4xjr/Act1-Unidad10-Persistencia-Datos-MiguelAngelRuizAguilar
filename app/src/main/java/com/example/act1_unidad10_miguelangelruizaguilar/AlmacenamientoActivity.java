package com.example.act1_unidad10_miguelangelruizaguilar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AlmacenamientoActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Button btnGuardar, btnRecuperar;

    private static final String FILE_NAME = "almacenamiento_datos.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnRecuperar = findViewById(R.id.btnRecuperar);

        btnGuardar.setOnClickListener(v -> guardarDatos());

        btnRecuperar.setOnClickListener(v -> recuperarDatos());
    }

    private void guardarDatos() {
        String datos = editText.getText().toString();

        if (!datos.isEmpty()) {
            try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
                fos.write(datos.getBytes());
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingresa algún dato", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperarDatos() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String datos = new String(buffer);

            textView.setText(datos);
            Toast.makeText(this, "Datos recuperados correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se han encontrado datos guardados", Toast.LENGTH_SHORT).show();
        }
    }
}
