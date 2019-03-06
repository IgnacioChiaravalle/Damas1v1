package activity.damas1v1.Principal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import activity.damas1v1.R;

public class PantallaDeAyuda extends AppCompatActivity {
    private Button atras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_ayuda);

        atras = findViewById(R.id.btnAtrasAyuda);
        atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent retorno = new Intent(PantallaDeAyuda.this, PantallaPrincipal.class);
                startActivity(retorno);
            }
        });
    }
}
