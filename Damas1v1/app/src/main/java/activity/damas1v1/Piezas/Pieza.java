package activity.damas1v1.Piezas;

import android.util.Pair;

import activity.damas1v1.Jugadores.Jugador;
import activity.damas1v1.LogicaDelJuego.Juego;
import activity.damas1v1.TDAListaDE.Position;
import activity.damas1v1.TDAListaDE.PositionList;

public class Pieza {
    private InteligenciaPieza inteligencia;
    private int posI, posJ, direccion;
    private Juego juego;
    private Jugador miJugador;
    private int grafico;

    public Pieza(int i, int j, Juego jue, Jugador jugador, int dir){
        posI = i;
        posJ = j;
        direccion = dir;
        juego = jue;
        miJugador = jugador;

        inteligencia = new InteligenciaPeon(this, juego.getMatriz());
    }

    void setGrafico(int g) { grafico = g; }
    public int getGrafico() { return grafico; }

    public boolean estaBloqueada(){
        PositionList<Pair<Integer,Integer>> listadoDisponibles = inteligencia.verDisponibles();
        return listadoDisponibles.isEmpty();
    }

    public void definirPosicionesDisponibles(){
        PositionList<Pair<Integer,Integer>> listadoDisponibles = inteligencia.verDisponibles();
        for (Position<Pair<Integer,Integer>> posPar : listadoDisponibles.positions())
            juego.habilitarCelda(posPar.element().first, posPar.element().second);
    }
    public PositionList<Pair<Integer,Integer>> verProximaComida(){
        return inteligencia.verProximaComida();
    }

    public Jugador getJugador(){ return miJugador; }

    public void setPosI(int i) { posI = i; chequearPorDama(); }
    int getPosI() { return posI; }
    public void setPosJ(int j){ posJ = j; }
    int getPosJ() { return posJ; }

    int getProximoI() { return posI + direccion; }
    int getSiguienteProximoI() { return getProximoI() + direccion; }

    private void chequearPorDama() {
        if (posI == 0 || posI == juego.getMatriz().getLadoMatriz() - 1)
            inteligencia = new InteligenciaDama(this, juego.getMatriz());
    }

}
