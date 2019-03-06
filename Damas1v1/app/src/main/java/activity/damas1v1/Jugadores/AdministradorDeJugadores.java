package activity.damas1v1.Jugadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Vibrator;

import java.io.Serializable;
import java.util.ArrayList;

import activity.damas1v1.Principal.PantallaPrincipal;
import activity.damas1v1.R;
import activity.damas1v1.TDAListaDE.Position;
import activity.damas1v1.TDAListaDE.PositionList;

public class AdministradorDeJugadores extends AppCompatActivity implements Serializable {
    private PositionList<Jugador> listadoJugadores;
    private AdministradorDeLista administradorDeLista;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador_de_jugadores);

        administradorDeLista = (AdministradorDeLista) getIntent().getSerializableExtra("Administrador de Lista");
        if (administradorDeLista == null)
            administradorDeLista = new AdministradorDeLista(AdministradorDeJugadores.this);

        listadoJugadores = administradorDeLista.getListadoJugadores();

        Jugador jugador = (Jugador) getIntent().getSerializableExtra("Jugador");
        if (jugador != null) {
            listadoJugadores.addLast(jugador);
            administradorDeLista.sobreescribirArchivo(this);
            mostrarGuardado();
        }

        crearBotonesDeJugadores();
        crearBotonesDeOpciones();
    }


    private void crearBotonesDeJugadores(){
        ListView listView = findViewById(R.id.listadoDeJugadores);
        ArrayList<String> listadoBotones = new ArrayList<>();
        for (Position<Jugador> posJugador : listadoJugadores.positions()) {
            String textoBoton = posJugador.element().getNombre() + "\nPromedio de Victorias: " + posJugador.element().getPromedioVictorias() + "%";
            listadoBotones.add(textoBoton);
        }
        ArrayAdapter<String> lista = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoBotones);
        listView.setAdapter(lista);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Position<Jugador> posJugador : listadoJugadores.positions()) {
                    if (sonIgualesLosNombres(posJugador.element().getNombre(), parent.getItemAtPosition(position).toString())){
                        administradorDeLista.quitarJugador(posJugador);
                        administradorDeLista.sobreescribirArchivo(AdministradorDeJugadores.this);
                        Intent editarJugador = new Intent(AdministradorDeJugadores.this, EdicionDeJugador.class);
                        editarJugador.putExtra("Administrador de Lista", administradorDeLista);
                        editarJugador.putExtra("Jugador",posJugador.element());
                        startActivity(editarJugador);
                    }
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (Position<Jugador> posJugador : listadoJugadores.positions()) {
                    if (sonIgualesLosNombres(posJugador.element().getNombre(), parent.getItemAtPosition(position).toString()))
                        return onLongClickListener(posJugador,posJugador.element());
                }
                return true;
            }
        });
    }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private boolean onLongClickListener(final Position<Jugador> posJugador, Jugador jugador){
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(AdministradorDeJugadores.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(AdministradorDeJugadores.this);
            }
            builder.setTitle("Eliminar a " + jugador.getNombre())
                    .setMessage("¿Está seguro de que desea eliminar a " + jugador.getNombre() + "?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            administradorDeLista.quitarJugador(posJugador);
                            administradorDeLista.sobreescribirArchivo(AdministradorDeJugadores.this);
                            crearBotonesDeJugadores();
                            mostrarGuardado();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) { closeContextMenu(); }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }

        private boolean sonIgualesLosNombres(String nombreJugador, String cadenaEnLista) {
            StringBuilder nombreEnLista = new StringBuilder();
            int i = 0;
            char actual = cadenaEnLista.charAt(i);
            while (actual != '\n') {
                nombreEnLista.append(actual);
                actual = cadenaEnLista.charAt(++i);
            }
            return nombreEnLista.toString().equals(nombreJugador);
        }


    private void crearBotonesDeOpciones(){
        Button btnSalir = findViewById(R.id.btnSalirAlInicio);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                administradorDeLista.sobreescribirArchivo(AdministradorDeJugadores.this);
                Intent volver = new Intent(AdministradorDeJugadores.this, PantallaPrincipal.class);
                startActivity(volver);
            }
        });
        Button btnCrearJugador = findViewById(R.id.btnCrearJugador);
        btnCrearJugador.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Jugador jugador = new Jugador("[Insertar Nombre del Jugador]");
                Intent editarJugador = new Intent(AdministradorDeJugadores.this, EdicionDeJugador.class);
                editarJugador.putExtra("Administrador de Lista", administradorDeLista);
                editarJugador.putExtra("Jugador", jugador);
                startActivity(editarJugador);
            }
        });
    }

    private void mostrarGuardado(){
        administradorDeLista.sobreescribirArchivo(AdministradorDeJugadores.this);
        AlertDialog.Builder mensaje = new AlertDialog.Builder(AdministradorDeJugadores.this);
        mensaje.setTitle("Guardado")
                .setMessage("Listado guardado con éxito.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) { closeContextMenu(); }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

}
