package activity.damas1v1.LogicaDelJuego;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.GridLayout;

import java.util.Objects;

import activity.damas1v1.ColoresDePiezas.Blancas;
import activity.damas1v1.ColoresDePiezas.Marrones;
import activity.damas1v1.FinesDelJuego.*;
import activity.damas1v1.Jugadores.Jugador;
import activity.damas1v1.Piezas.Pieza;
import activity.damas1v1.R;

public class Juego extends AppCompatActivity {
    private int longitudLado, ladoCelda;
    private MatrizTablero matriz;
    private GridLayout tablero;
    private Celda celdaSeleccionada;
    private AdministradorDeTurnos administradorDeTurnos;
    private Jugador jugador1, jugador2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int anchoPantalla = size.x;

        longitudLado = Objects.requireNonNull(getIntent().getExtras()).getInt("LongitudLado");
        ladoCelda = anchoPantalla / longitudLado + 15;

        matriz = new MatrizTablero(longitudLado, ladoCelda, this);

        tablero = findViewById(R.id.glTablero);
        tablero.setColumnCount(longitudLado);
        tablero.setRowCount(longitudLado);
        for (int i = 0; i < longitudLado; i++){
            for (int j = 0; j < longitudLado; j++)
                tablero.addView(matriz.getCelda(i,j));
        }

        jugador1 = (Jugador) getIntent().getSerializableExtra("Jugador Blancas"); jugador1.setColorDePiezas(new Blancas());
        jugador2 = (Jugador) getIntent().getSerializableExtra("Jugador Marrones"); jugador2.setColorDePiezas(new Marrones());
        crearEquipos(jugador1, jugador2);

        administradorDeTurnos = new AdministradorDeTurnos(this, jugador1, jugador2);
        administradorDeTurnos.setearTurno(jugador1);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void crearEquipos(Jugador j1, Jugador j2){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < longitudLado; j++) {
                if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0))
                    matriz.getCelda(i,j).agregarPieza(new Pieza(i,j,this, j1, 1));
            }
        }

        for (int i = longitudLado - 1; i > longitudLado - 4; i--){
            for (int j = 0; j < longitudLado; j++) {
                if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0))
                    matriz.getCelda(i,j).agregarPieza(new Pieza(i,j,this, j2, -1));
            }
        }
    }

    public MatrizTablero getMatriz(){ return matriz; }
    public void cambiarTurno() { administradorDeTurnos.cambiarTurno(); }

    public void habilitarCelda(int i, int j){ matriz.habilitarCelda(i,j); }
    public void deshabilitarCelda(int i, int j){ matriz.deshabilitarCelda(i,j); }

    public void setCeldaSeleccionada(Celda celda) { celdaSeleccionada = celda; }
    public Celda getCeldaSeleccionada() { return celdaSeleccionada; }

    public void habilitarJugador(Jugador jugador){
        boolean todasBloqueadas = true, perdio = true;
        for (int i = 0; i < longitudLado; i++) {
            for (int j = 0; j < longitudLado; j++) {
                if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0)) {
                    if (matriz.getCelda(i,j).getTienePieza()) {
                        if (matriz.getCelda(i,j).getPieza().getJugador() == jugador) {
                            habilitarCelda(i,j);
                            if (perdio) perdio = false;
                            if (todasBloqueadas && !matriz.getCelda(i,j).getPieza().estaBloqueada()) todasBloqueadas = false;
                        }
                        else
                            deshabilitarCelda(i,j);
                    }
                    else
                        deshabilitarCelda(i,j);
                }
            }
        }
        if (perdio)
            terminarEnDerrota(jugador);
        else {
            if (todasBloqueadas)
                terminarEnTablas();
        }
    }

    public void deshabilitarTodas(){
        for (int i = 0; i < longitudLado; i++) {
            for (int j = 0; j < longitudLado; j++) {
                if ((i % 2 == 0 && j % 2 != 0) || (i % 2 != 0 && j % 2 == 0))
                        deshabilitarCelda(i,j);
            }
        }
    }

    private void terminarEnDerrota(Jugador perdedor){
        Intent fin = new Intent(Juego.this, FinEnVictoria.class);
        Jugador ganador;
        if (perdedor == jugador2)
            ganador = jugador1;
        else
            ganador = jugador2;
        fin.putExtra("Ganador",ganador);
        fin.putExtra("Perdedor",perdedor);
        startActivity(fin);
    }
    private void terminarEnTablas(){
        Intent fin = new Intent(Juego.this,FinEnEmpate.class);
        fin.putExtra("Jugador1",jugador1);
        fin.putExtra("Jugador2",jugador2);
        startActivity(fin);
    }
}
