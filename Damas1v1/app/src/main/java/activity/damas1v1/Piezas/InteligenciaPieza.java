package activity.damas1v1.Piezas;

import android.util.Pair;
import activity.damas1v1.LogicaDelJuego.MatrizTablero;
import activity.damas1v1.TDAListaDE.PositionList;

abstract class InteligenciaPieza {
    Pieza miPieza;
    MatrizTablero tablero;

    InteligenciaPieza(Pieza pieza, MatrizTablero t){
        miPieza = pieza;
        tablero = t;
    }

    abstract PositionList<Pair<Integer,Integer>> verDisponibles();
    abstract PositionList<Pair<Integer,Integer>> verProximaComida();
}
