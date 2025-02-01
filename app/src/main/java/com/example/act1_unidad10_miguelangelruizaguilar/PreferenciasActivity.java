package com.example.act1_unidad10_miguelangelruizaguilar;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.HashMap;

public class PreferenciasActivity extends AppCompatActivity {

    private Spinner spinnerFondo;
    private Spinner spinnerIdioma;
    private Spinner spinnerTipoLetra;
    private Button btnModificar;
    private Button btnAplicar;
    private Button btnResetear;
    private RelativeLayout layoutPrincipal;
    private Button btnAtras;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String[] fondos = {"Charmander", "Bulbasaur", "Squirtle"};
    private String[] idiomas = {"Español", "Inglés", "Francés"};
    private String[] letras = {"Sans-serif", "Monospace", "Serif"};

    private HashMap<String, HashMap<String, String>> traducciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        spinnerFondo = findViewById(R.id.spinnerFondo);
        spinnerIdioma = findViewById(R.id.spinnerIdioma);
        spinnerTipoLetra = findViewById(R.id.spinnerTipoLetra);
        btnModificar = findViewById(R.id.btnModificar);
        btnAplicar = findViewById(R.id.btnAplicar);
        btnResetear = findViewById(R.id.btnResetear);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        inicializarTraducciones();

        ArrayAdapter<String> adapterFondo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fondos);
        ArrayAdapter<String> adapterIdioma = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idiomas);
        ArrayAdapter<String> adapterLetra = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letras);

        adapterFondo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterIdioma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLetra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFondo.setAdapter(adapterFondo);
        spinnerIdioma.setAdapter(adapterIdioma);
        spinnerTipoLetra.setAdapter(adapterLetra);

        btnModificar.setOnClickListener(v -> guardarPreferencias());
        btnAplicar.setOnClickListener(v -> aplicarModo());
        btnResetear.setOnClickListener(v -> resetearModo());
    }

    private void guardarPreferencias() {
        String fondoSeleccionado = spinnerFondo.getSelectedItem().toString();
        String idiomaSeleccionado = spinnerIdioma.getSelectedItem().toString();
        String letraSeleccionada = spinnerTipoLetra.getSelectedItem().toString();

        editor.putString("fondo", fondoSeleccionado);
        editor.putString("idioma", idiomaSeleccionado);
        editor.putString("letra", letraSeleccionada);
        editor.apply();

        Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_SHORT).show();
    }

    private void aplicarModo() {
        String fondoSeleccionado = sharedPreferences.getString("fondo", "blanco");
        String idiomaSeleccionado = sharedPreferences.getString("idioma", "Español");
        String letraSeleccionada = sharedPreferences.getString("letra", "Sans-serif");

        actualizarTextoSegunIdioma(idiomaSeleccionado);
        actualizarFondo(fondoSeleccionado);
        aplicarTipoLetra(letraSeleccionada);

        Toast.makeText(this, "Modo aplicado", Toast.LENGTH_SHORT).show();
    }

    private void resetearModo() {
        getWindow().setBackgroundDrawableResource(R.drawable.blanco);

        editor.clear(); // Borra todas las preferencias guardadas
        editor.putString("fondo", "blanco");
        editor.putString("idioma", "Español");
        editor.putString("letra", "Sans-serif");
        editor.apply();

        spinnerFondo.setSelection(0); // Reinicia el spinner al primer elemento
        spinnerIdioma.setSelection(0);
        spinnerTipoLetra.setSelection(0);

        aplicarTipoLetra("Sans-serif");
        actualizarTextoSegunIdioma("Español");

        Toast.makeText(this, "Preferencias restablecidas", Toast.LENGTH_SHORT).show();
    }

    private void inicializarTraducciones() {
        traducciones = new HashMap<>();

        HashMap<String, String> espanol = new HashMap<>();
        espanol.put("btnModificar", "Modificar Parámetros");
        espanol.put("btnAplicar", "Aplicar Modo");
        espanol.put("btnResetear", "Resetear Modo");

        HashMap<String, String> ingles = new HashMap<>();
        ingles.put("btnModificar", "Modify Parameters");
        ingles.put("btnAplicar", "Apply Mode");
        ingles.put("btnResetear", "Reset Mode");

        HashMap<String, String> frances = new HashMap<>();
        frances.put("btnModificar", "Modifier les Paramètres");
        frances.put("btnAplicar", "Appliquer le Mode");
        frances.put("btnResetear", "Réinitialiser le Mode");

        traducciones.put("Español", espanol);
        traducciones.put("Inglés", ingles);
        traducciones.put("Francés", frances);
    }

    private void actualizarTextoSegunIdioma(String idioma) {
        HashMap<String, String> textos = traducciones.get(idioma);
        if (textos != null) {
            btnModificar.setText(textos.get("btnModificar"));
            btnAplicar.setText(textos.get("btnAplicar"));
            btnResetear.setText(textos.get("btnResetear"));
        }
    }

    private void actualizarFondo(String fondo) {
        switch (fondo) {
            case "Charmander":
                getWindow().setBackgroundDrawableResource(R.drawable.charmander);
                break;
            case "Bulbasaur":
                getWindow().setBackgroundDrawableResource(R.drawable.bulbasaur);
                break;
            case "Squirtle":
                getWindow().setBackgroundDrawableResource(R.drawable.squirtle);
                break;
            default:
                getWindow().setBackgroundDrawableResource(R.drawable.blanco);
                break;
        }
    }

    private void aplicarTipoLetra(String letra) {
        Typeface tipoFuente;
        switch (letra) {
            case "Sans-serif":
                tipoFuente = Typeface.SANS_SERIF;
                break;
            case "Monospace":
                tipoFuente = Typeface.MONOSPACE;
                break;
            case "Serif":
                tipoFuente = Typeface.SERIF;
                break;
            default:
                tipoFuente = Typeface.DEFAULT;
        }
        btnModificar.setTypeface(tipoFuente);
        btnAplicar.setTypeface(tipoFuente);
        btnResetear.setTypeface(tipoFuente);
    }
}
