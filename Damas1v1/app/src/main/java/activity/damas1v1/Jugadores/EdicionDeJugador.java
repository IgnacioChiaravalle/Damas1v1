package activity.damas1v1.Jugadores;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import activity.damas1v1.R;
import activity.damas1v1.TDAListaDE.Position;

public class EdicionDeJugador extends AppCompatActivity implements View.OnClickListener {
    private Jugador jugador;
    private EditText etNombreJugador;
    private TextView tvPartidasJugadas, tvPromedio, tvVictorias, tvEmpates, tvDerrotas;
    private Button btnVolver, btnSumarVictoria, btnSumarEmpate, btnSumarDerrota, btnQuitarVictoria, btnQuitarEmpate, btnQuitarDerrota;
    private AdministradorDeLista administradorDeLista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_de_jugador);

        administradorDeLista = (AdministradorDeLista) getIntent().getSerializableExtra("Administrador de Lista");
        jugador = (Jugador) getIntent().getSerializableExtra("Jugador");

        crearViews();
        etNombreJugador.setText(jugador.getNombre());
        setearTextViews();
        setearListeners();
    }

    private void crearViews(){
        etNombreJugador = findViewById(R.id.etNombreJugador);
        tvPartidasJugadas = findViewById(R.id.tvPartidasJugadas);
        tvPromedio = findViewById(R.id.tvPromedioDeVictorias);
        tvVictorias = findViewById(R.id.tvVictorias);
        tvEmpates = findViewById(R.id.tvEmpates);
        tvDerrotas = findViewById(R.id.tvDerrotas);
        btnSumarVictoria = findViewById(R.id.btnSumarVictoria);
        btnSumarEmpate = findViewById(R.id.btnSumarEmpate);
        btnSumarDerrota = findViewById(R.id.btnSumarDerrota);
        btnQuitarVictoria = findViewById(R.id.btnQuitarVictoria);
        btnQuitarEmpate = findViewById(R.id.btnQuitarEmpate);
        btnQuitarDerrota = findViewById(R.id.btnQuitarDerrota);
        btnVolver = findViewById(R.id.btnVolver);
    }

    @SuppressLint("SetTextI18n")
    private void setearTextViews(){
        tvPartidasJugadas.setText("Partidas Jugadas: " + jugador.getPartidasJugadas());
        tvPromedio.setText("Promedio de Victorias: " + jugador.getPromedioVictorias() + "%");
        tvVictorias.setText("Victorias: " + jugador.getVictorias());
        tvEmpates.setText("Empates: " + jugador.getEmpates());
        tvDerrotas.setText("Derrotas: " + jugador.getDerrotas());
    }

    private void setearListeners(){
        btnSumarVictoria.setOnClickListener(this);
        btnSumarEmpate.setOnClickListener(this);
        btnSumarDerrota.setOnClickListener(this);
        btnQuitarVictoria.setOnClickListener(this);
        btnQuitarEmpate.setOnClickListener(this);
        btnQuitarDerrota.setOnClickListener(this);
        btnVolver.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSumarVictoria:
                jugador.sumarVictoria();
                setearTextViews();
                break;
            case R.id.btnSumarEmpate:
                jugador.sumarEmpate();
                setearTextViews();
                break;
            case R.id.btnSumarDerrota:
                jugador.sumarDerrota();
                setearTextViews();
                break;
            case R.id.btnQuitarVictoria:
                jugador.quitarVictoria();
                setearTextViews();
                break;
            case R.id.btnQuitarEmpate:
                jugador.quitarEmpate();
                setearTextViews();
                break;
            case R.id.btnQuitarDerrota:
                jugador.quitarDerrota();
                setearTextViews();
                break;
            case R.id.btnVolver:
                String nombre = etNombreJugador.getText().toString();
                if (nombre.trim().length() == 0)
                    mostrarErrorEnNombre("El nombre de un Jugador debe contener al menos un caracter que no sea un espacio.");
                else {
                    if (nombre.contains("\n"))
                        mostrarErrorEnNombre("El nombre de un Jugador no puede contener saltos de línea.");
                    else {
                        if (nombreYaUsado(nombre))
                            mostrarErrorEnNombre("Este nombre ya ha sido usado para otro Jugador anteriormente.");
                        else {
                            jugador.setNombre(nombre);
                            Intent retorno = new Intent(EdicionDeJugador.this, AdministradorDeJugadores.class);
                            retorno.putExtra("Administrador de Lista", administradorDeLista);
                            retorno.putExtra("Jugador", jugador);
                            startActivity(retorno);
                        }
                    }
                }
                break;
        }
    }

    private boolean nombreYaUsado(String nombreActual){
        for (Position<Jugador> position : administradorDeLista.getListadoJugadores().positions()) {
            if (position.element().getNombre().equals(nombreActual))
                return true;
        }
        return false;
    }

    private void mostrarErrorEnNombre(String error){
        administradorDeLista.sobreescribirArchivo(EdicionDeJugador.this);
        AlertDialog.Builder mensaje = new AlertDialog.Builder(EdicionDeJugador.this);
        mensaje.setTitle("Error en el Nombre del Jugador")
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { closeContextMenu(); }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
