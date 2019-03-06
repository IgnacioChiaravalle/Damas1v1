package activity.damas1v1.Principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import activity.damas1v1.LogicaDelJuego.Juego;
import activity.damas1v1.R;

public class SeleccionDeTablero extends AppCompatActivity {
    private Button btn8x8, btn10x10, btnAtras;
    private Intent avanzar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_de_tablero);

        btn8x8 = findViewById(R.id.btn8x8);
        btn10x10 = findViewById(R.id.btn10x10);
        btnAtras = findViewById(R.id.btnAtrasTablero);
        avanzar = new Intent(SeleccionDeTablero.this, Juego.class);
        avanzar.putExtra("Jugador Blancas", getIntent().getSerializableExtra("Jugador Blancas"));
        avanzar.putExtra("Jugador Marrones", getIntent().getSerializableExtra("Jugador Marrones"));
        setearListeners();
    }

    private void setearListeners(){
        btnAtras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent retornar = new Intent(SeleccionDeTablero.this, SeleccionDeJugadores.class);
                startActivity(retornar);
            }
        });

        btn8x8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                avanzar.putExtra("LongitudLado", 8);
                startActivity(avanzar);
            }
        });
        btn10x10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                avanzar.putExtra("LongitudLado", 10);
                startActivity(avanzar);
            }
        });
    }
}
