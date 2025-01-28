package com.example.act1_unidad10_miguelangelruizaguilar;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class PreferenciasActivity extends AppCompatActivity {

    private Spinner spinnerFondo;
    private Spinner spinnerIdioma;
    private Spinner spinnerTipoLetra;
    private Button btnModificar;
    private Button btnAplicar;
    private Button btnResetear;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String[] fondos = {"Charmander", "Bulbasaur", "Squirtle"};
    private String[] idiomas = {"Español", "Inglés", "Francés"};
    private String[] letras = {"Sans-serif", "Monospace", "Serif"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        // Referencias a los UI
        spinnerFondo = findViewById(R.id.spinnerFondo);
        spinnerIdioma = findViewById(R.id.spinnerIdioma);
        spinnerTipoLetra = findViewById(R.id.spinnerTipoLetra);
        btnModificar = findViewById(R.id.btnModificar);
        btnAplicar = findViewById(R.id.btnAplicar);
        btnResetear = findViewById(R.id.btnResetear);

        // Inicializar SharedPreferences (para los modos Pokémon)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        // Crear adaptadores para los Spinners
        ArrayAdapter<String> adapterFondo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fondos);
        ArrayAdapter<String> adapterIdioma = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idiomas);
        ArrayAdapter<String> adapterLetra = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, letras);

        adapterFondo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterIdioma.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLetra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFondo.setAdapter(adapterFondo);
        spinnerIdioma.setAdapter(adapterIdioma);
        spinnerTipoLetra.setAdapter(adapterLetra);

        // Cargar las preferencias guardadas en los Spinners
        cargarPreferencias();

        // Configuración del botón "Modificar Parámetros"
        btnModificar.setOnClickListener(v -> guardarPreferencias());

        // Configuración del botón "Aplicar Modo"
        btnAplicar.setOnClickListener(v -> aplicarModo());

        // Configuración del botón "Resetear Modo"
        btnResetear.setOnClickListener(v -> resetearModo());
    }

    // Cargar las preferencias previamente guardadas (solo para los modos Pokémon)
    private void cargarPreferencias() {
        String fondo = sharedPreferences.getString("fondo", "charmander");
        String idioma = sharedPreferences.getString("idioma", "español");
        String letra = sharedPreferences.getString("letra", "sans-serif");

        spinnerFondo.setSelection(getIndex(fondo, fondos));
        spinnerIdioma.setSelection(getIndex(idioma, idiomas));
        spinnerTipoLetra.setSelection(getIndex(letra, letras));
    }

    // Guardar las preferencias seleccionadas por el usuario
    private void guardarPreferencias() {
        String fondoSeleccionado = spinnerFondo.getSelectedItem().toString().toLowerCase();
        String idiomaSeleccionado = spinnerIdioma.getSelectedItem().toString().toLowerCase();
        String letraSeleccionada = spinnerTipoLetra.getSelectedItem().toString();

        editor.putString("fondo", fondoSeleccionado);
        editor.putString("idioma", idiomaSeleccionado);
        editor.putString("letra", letraSeleccionada);
        editor.apply();
    }

    // Aplicar los cambios y actualizar la interfaz de usuario según las preferencias
    private void aplicarModo() {
        String fondoSeleccionado = sharedPreferences.getString("fondo", "charmander");
        String idiomaSeleccionado = sharedPreferences.getString("idioma", "español");
        String letraSeleccionada = sharedPreferences.getString("letra", "sans-serif");

        // Cambiar el fondo según la selección
        if (fondoSeleccionado.equals("charmander")) {
            setFondo(R.drawable.charmander);
        } else if (fondoSeleccionado.equals("bulbasaur")) {
            setFondo(R.drawable.bulbasaur);
        } else if (fondoSeleccionado.equals("squirtle")) {
            setFondo(R.drawable.squirtle);
        }

        // Cambiar idioma - solo muestra un Toast como ejemplo
        Toast.makeText(this, "Idioma seleccionado: " + idiomaSeleccionado, Toast.LENGTH_SHORT).show();

        // Cambiar tipo de letra
        Typeface tipoLetra;
        if (letraSeleccionada.equals("Sans-serif")) {
            tipoLetra = Typeface.SANS_SERIF;
        } else if (letraSeleccionada.equals("Monospace")) {
            tipoLetra = Typeface.MONOSPACE;
        } else {
            tipoLetra = Typeface.SERIF;
        }

        // Aplicar el tipo de letra a los elementos de la UI
        aplicarTipoLetra(tipoLetra);
    }

    private void aplicarTipoLetra(Typeface tipoLetra) {
        // Cambiar el tipo de letra de los botones
        btnModificar.setTypeface(tipoLetra);
        btnAplicar.setTypeface(tipoLetra);
        btnResetear.setTypeface(tipoLetra);

        // Cambiar el tipo de letra de los Spinners
        setSpinnerFont(spinnerFondo, tipoLetra);
        setSpinnerFont(spinnerIdioma, tipoLetra);
        setSpinnerFont(spinnerTipoLetra, tipoLetra);
    }

    // Método auxiliar para cambiar la fuente de un Spinner
    private void setSpinnerFont(Spinner spinner, Typeface tipoLetra) {
        // Obtener el adaptador del Spinner
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();

        // Cambiar el tipo de letra del adaptador para que se aplique a los elementos en el Spinner
        if (adapter != null) {
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            TextView view = new TextView(getApplicationContext());
            view.setTypeface(tipoLetra); // Cambia el tipo de letra del texto en el Spinner
        }
    }



    // Restablecer preferencias a los valores predeterminados del archivo settings.xml (valores de fábrica)
    private void resetearModo() {
        SharedPreferences prefsDefault = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editorDefault = prefsDefault.edit();

        editorDefault.putString("fondo", "charmander");
        editorDefault.putString("idioma", "español");
        editorDefault.putString("letra", "sans-serif");
        editorDefault.apply();

        cargarPreferencias(); // Volver a cargar las preferencias para actualizarlas en la UI
    }

    // Cambiar el fondo de pantalla
    private void setFondo(int recursoImagen) {
        // Cambiar el fondo de la pantalla de la actividad
        getWindow().getDecorView().setBackgroundResource(recursoImagen);
    }

    // Obtener el índice del valor seleccionado en el Spinner
    private int getIndex(String value, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0;
    }
}
