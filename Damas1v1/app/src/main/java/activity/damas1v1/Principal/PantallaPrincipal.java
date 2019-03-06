package activity.damas1v1.Principal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import activity.damas1v1.Jugadores.AdministradorDeJugadores;
import activity.damas1v1.R;

public class PantallaPrincipal extends AppCompatActivity {
    private Button btnJugar, btnAdministrarJugadores, btnAyuda;
    private Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        btnJugar = findViewById(R.id.btnJugar);
        btnAdministrarJugadores = findViewById(R.id.btnAdministrarJugadores);
        btnAyuda = findViewById(R.id.btnAyuda);

        setearListeners();
    }

    private void setearListeners(){
        btnJugar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(PantallaPrincipal.this, SeleccionDeJugadores.class);
                startActivity(intent);
            }
        });
        btnAdministrarJugadores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(PantallaPrincipal.this, AdministradorDeJugadores.class);
                startActivity(intent);
            }
        });
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intent = new Intent(PantallaPrincipal.this, PantallaDeAyuda.class);
                startActivity(intent);
            }
        });
    }
}
