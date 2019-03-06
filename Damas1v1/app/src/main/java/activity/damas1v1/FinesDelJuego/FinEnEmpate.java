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

public class FinEnEmpate extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_del_juego);

        LinearLayout fondo = findViewById(R.id.fondoFinDelJuego);
        fondo.setBackgroundColor(Color.YELLOW);

        TextView textView = findViewById(R.id.tvTextoFin);
        Button boton = findViewById(R.id.btnVolverAlInicio);
        Jugador jugador1 = (Jugador) getIntent().getSerializableExtra("Jugador1");
        Jugador jugador2 = (Jugador) getIntent().getSerializableExtra("Jugador2");

        textView.setText("¡" + jugador1.getNombre() + " y " + jugador2.getNombre() + " empataron!");

        AdministradorDeLista administradorDeLista = new AdministradorDeLista(FinEnEmpate.this);
        boolean encontre1 = false, encontre2 = false;
        for (Position<Jugador> position : administradorDeLista.getListadoJugadores().positions()){
            if (position.element().getNombre().equals(jugador1.getNombre())) {
                position.element().sumarEmpate();
                encontre1 = true;
            }
            if (position.element().getNombre().equals(jugador2.getNombre())) {
                position.element().sumarEmpate();
                encontre2 = true;
            }
            if (encontre1 && encontre2)
                break;
        }
        administradorDeLista.sobreescribirArchivo(FinEnEmpate.this);

        boton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent retorno = new Intent(FinEnEmpate.this, PantallaPrincipal.class);
                startActivity(retorno);
            }
        });
    }
}
