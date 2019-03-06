package activity.damas1v1.FinesDelJuego;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import activity.damas1v1.Jugadores.AdministradorDeLista;
import activity.damas1v1.Jugadores.Jugador;
import activity.damas1v1.Principal.PantallaPrincipal;
import activity.damas1v1.R;
import activity.damas1v1.TDAListaDE.Position;

public class FinEnVictoria extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_del_juego);

        LinearLayout fondo = findViewById(R.id.fondoFinDelJuego);
        fondo.setBackgroundColor(Color.GREEN);

        TextView textView = findViewById(R.id.tvTextoFin);
        Button boton = findViewById(R.id.btnVolverAlInicio);
        Jugador ganador = (Jugador) getIntent().getSerializableExtra("Ganador");
        Jugador perdedor = (Jugador) getIntent().getSerializableExtra("Perdedor");

        textView.setText("¡Ganó " + ganador.getNombre() + "!\n¡¡Felicitaciones!!");

        AdministradorDeLista administradorDeLista = new AdministradorDeLista(FinEnVictoria.this);
        boolean encontreGanador = false, encontrePerdedor = false;
        for (Position<Jugador> position : administradorDeLista.getListadoJugadores().positions()){
            if (position.element().getNombre().equals(ganador.getNombre())) {
                position.element().sumarVictoria();
                encontreGanador = true;
            }
            if (position.element().getNombre().equals(perdedor.getNombre())) {
                position.element().sumarDerrota();
                encontrePerdedor = true;
            }
            if (encontreGanador && encontrePerdedor)
                break;
        }
        administradorDeLista.sobreescribirArchivo(FinEnVictoria.this);

        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent retorno = new Intent(FinEnVictoria.this, PantallaPrincipal.class);
                startActivity(retorno);
            }
        });
    }
}
