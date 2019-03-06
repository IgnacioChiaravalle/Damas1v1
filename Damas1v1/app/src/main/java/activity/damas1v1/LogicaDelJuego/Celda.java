package activity.damas1v1.LogicaDelJuego;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Pair;
import android.view.View;
import activity.damas1v1.Piezas.Pieza;
import activity.damas1v1.R;
import activity.damas1v1.TDAListaDE.Position;
import activity.damas1v1.TDAListaDE.PositionList;

@SuppressLint("ViewConstructor")
public class Celda extends android.support.v7.widget.AppCompatImageButton {
    private Pieza miPieza;
    private Juego juego;
    private int miI, miJ;

    public Celda(Juego jue, int i, int j){
        super(jue);
        juego = jue;
        miPieza = null;
        miI = i;
        miJ = j;
        setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                if (miPieza != null)
                    tengoPieza();
                else
                    noTengoPieza();
            }
        });
    }

    public int getI() { return miI; }
    public int getJ() { return miJ; }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void agregarPieza(Pieza pieza) {
        miPieza = pieza;
        if (miPieza != null)
            setImageResource(miPieza.getGrafico());
        else
            setImageResource(R.drawable.celda_vacia);

    }
    public boolean getTienePieza(){ return miPieza != null; }
    public Pieza getPieza(){ return miPieza; }


    private void tengoPieza(){
        juego.habilitarJugador(miPieza.getJugador()); //Para bloquear celdas sin pieza que se hayan desbloqueado al tocar otra pieza.
        juego.setCeldaSeleccionada(this);
        miPieza.definirPosicionesDisponibles();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void noTengoPieza(){
        Pieza pieza = juego.getCeldaSeleccionada().getPieza();
        boolean comida = verSiComida(pieza);
        pieza.setPosI(miI);
        pieza.setPosJ(miJ);
        agregarPieza(pieza);
        juego.getCeldaSeleccionada().agregarPieza(null);

        verSiTerminaElTurno(comida);
    }
        @RequiresApi(api = Build.VERSION_CODES.M)
        private boolean verSiComida(Pieza pieza){
            boolean comida;
            Celda celdaPiezaComida = null;

            if (miJ > juego.getCeldaSeleccionada().getJ()) {
                if (miI < juego.getCeldaSeleccionada().getI()) {
                    comida = (juego.getMatriz().getCelda(miI + 1, miJ - 1).getTienePieza()) && (juego.getMatriz().getCelda(miI + 1, miJ - 1).getPieza() != pieza);
                    if (comida)
                        celdaPiezaComida = juego.getMatriz().getCelda(miI + 1, miJ - 1);
                }
                else {
                    comida = (juego.getMatriz().getCelda(miI - 1, miJ - 1).getTienePieza()) && (juego.getMatriz().getCelda(miI - 1, miJ - 1).getPieza() != pieza);
                    if (comida)
                        celdaPiezaComida = juego.getMatriz().getCelda(miI - 1, miJ - 1);
                }
            }
            else {
                if (miI < juego.getCeldaSeleccionada().getI()) {
                    comida = (juego.getMatriz().getCelda(miI + 1, miJ + 1).getTienePieza()) && (juego.getMatriz().getCelda(miI + 1, miJ + 1).getPieza() != pieza);
                    if (comida)
                        celdaPiezaComida = juego.getMatriz().getCelda(miI + 1, miJ + 1);
                }
                else {
                    comida = (juego.getMatriz().getCelda(miI - 1, miJ + 1).getTienePieza()) && (juego.getMatriz().getCelda(miI - 1, miJ + 1).getPieza() != pieza);
                    if (comida)
                        celdaPiezaComida = juego.getMatriz().getCelda(miI - 1, miJ + 1);
                }
            }

            if (comida) celdaPiezaComida.agregarPieza(null);
            return comida;
        }

        private void verSiTerminaElTurno(boolean comida){
            juego.deshabilitarTodas();

            PositionList<Pair<Integer,Integer>> listaProximasComida = miPieza.verProximaComida();
            if (!listaProximasComida.isEmpty() && comida) {
                juego.setCeldaSeleccionada(this);
                for (Position<Pair<Integer,Integer>> posPar : listaProximasComida.positions())
                    juego.habilitarCelda(posPar.element().first, posPar.element().second);
            }
            else
                juego.cambiarTurno();
        }
}
