package activity.damas1v1.Principal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import activity.damas1v1.Jugadores.AdministradorDeLista;
import activity.damas1v1.Jugadores.Jugador;
import activity.damas1v1.R;
import activity.damas1v1.TDAListaDE.EmptyListException;
import activity.damas1v1.TDAListaDE.Position;

public class SeleccionDeJugadores extends AppCompatActivity {
    private Spinner spinner1, spinner2;
    private AdministradorDeLista administradorDeLista;
    private Jugador jugadorBlancas, jugadorMarrones;
    private String[] nombres;
    private Button seleccionarTablero, atras;
    private TextView jugadoresInvalidos;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_de_jugadores);

        administradorDeLista = new AdministradorDeLista(SeleccionDeJugadores.this);
        nombres = new String[administradorDeLista.getListadoJugadores().size()];

        jugadoresInvalidos = findViewById(R.id.tvJugadoresInvalidos); jugadoresInvalidos.setVisibility(View.INVISIBLE);
        seleccionarTablero = findViewById(R.id.btnIrASeleccionDeTablero); seleccionarTablero.setVisibility(View.INVISIBLE);
        seleccionarTablero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent irASeleccionTablero = new Intent(SeleccionDeJugadores.this, SeleccionDeTablero.class);
                irASeleccionTablero.putExtra("Jugador Blancas", jugadorBlancas);
                irASeleccionTablero.putExtra("Jugador Marrones", jugadorMarrones);
                startActivity(irASeleccionTablero);
            }
        });

        try {
            jugadorBlancas = administradorDeLista.getListadoJugadores().first().element();
            jugadorMarrones = administradorDeLista.getListadoJugadores().first().element();
        } catch (EmptyListException e) {
            jugadorBlancas = null;
            jugadorMarrones = null;
        }

        spinner1 = findViewById(R.id.spinnerJugadores1);
        spinner2 = findViewById(R.id.spinnerJugadores2);
        ponerListaEnSpinner(spinner1);
        ponerListaEnSpinner(spinner2);
        setearListenersDeItems();

        atras = findViewById(R.id.btnAtrasJugadores);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent volver = new Intent(SeleccionDeJugadores.this, PantallaPrincipal.class);
                startActivity(volver);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void ponerListaEnSpinner(Spinner spinner) {
        if (administradorDeLista.getListadoJugadores().isEmpty()) {
            nombres = new String[1];
            nombres[0] = "[NO SE HAN CREADO JUGADORES]";
        }
        else {
            int i = 0;
            for (Position<Jugador> posJugador : administradorDeLista.getListadoJugadores().positions())
                nombres[i++] = posJugador.element().getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres);
        spinner.setAdapter(adapter);
    }

    private void setearListenersDeItems(){
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Position<Jugador> posJugador : administradorDeLista.getListadoJugadores().positions()){
                    if (posJugador.element().getNombre().equals(nombres[position])) {
                        jugadorBlancas = posJugador.element();
                        if (jugadorMarrones != null) activarViews();
                        break;
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Position<Jugador> posJugador : administradorDeLista.getListadoJugadores().positions()){
                    if (posJugador.element().getNombre().equals(nombres[position])) {
                        jugadorMarrones = posJugador.element();
                        if (jugadorBlancas != null) activarViews();
                        break;
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

        private void activarViews(){
            if (!jugadorBlancas.getNombre().equals(jugadorMarrones.getNombre())) {
                seleccionarTablero.setVisibility(View.VISIBLE);
                jugadoresInvalidos.setVisibility(View.INVISIBLE);
            }
            else {
                seleccionarTablero.setVisibility(View.INVISIBLE);
                jugadoresInvalidos.setVisibility(View.VISIBLE);
            }
        }

}
