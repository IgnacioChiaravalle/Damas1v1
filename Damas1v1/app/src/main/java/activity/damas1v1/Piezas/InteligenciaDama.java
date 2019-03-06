package activity.damas1v1.Piezas;

import android.util.Pair;
import activity.damas1v1.LogicaDelJuego.MatrizTablero;
import activity.damas1v1.TDAListaDE.ListaDoblementeEnlazada;
import activity.damas1v1.TDAListaDE.PositionList;

class InteligenciaDama extends InteligenciaPieza {

    InteligenciaDama(Pieza pieza, MatrizTablero t){
        super(pieza, t);
        miPieza.setGrafico(miPieza.getJugador().getColorDeDamas());
    }

    PositionList<Pair<Integer, Integer>> verDisponibles() {
        PositionList<Pair<Integer,Integer>> toReturn = new ListaDoblementeEnlazada<>();
        direccion(toReturn, -1,-1, false);
        direccion(toReturn, -1,1, false);
        direccion(toReturn, 1,-1, false);
        direccion(toReturn, 1,1, false);
        return toReturn;
    }

    PositionList<Pair<Integer,Integer>> verProximaComida(){
        PositionList<Pair<Integer,Integer>> toReturn = new ListaDoblementeEnlazada<>();
        direccion(toReturn, -1,-1, true);
        direccion(toReturn, -1,1, true);
        direccion(toReturn, 1,-1, true);
        direccion(toReturn, 1,1, true);
        return toReturn;
    }


    private void direccion(PositionList<Pair<Integer,Integer>> toReturn, int direccionI, int direccionJ, boolean estoyViendoComida) {
        boolean noMas = false;
        int celdasMovidas = 1, movimientoI, movimientoJ;

        while (!noMas){
            movimientoI = miPieza.getPosI() + (celdasMovidas * direccionI);
            movimientoJ = miPieza.getPosJ() + (celdasMovidas * direccionJ);
            if (movimientoI >= 0 && movimientoI < tablero.getLadoMatriz() && movimientoJ >= 0 && movimientoJ < tablero.getLadoMatriz()) {
                if (!tablero.getCelda(movimientoI, movimientoJ).getTienePieza()) {
                    if (!estoyViendoComida) toReturn.addLast(new Pair<>(movimientoI, movimientoJ));
                    celdasMovidas++;
                }
                else {
                    if (tablero.getCelda(movimientoI, movimientoJ).getPieza().getJugador() != miPieza.getJugador()) {
                        movimientoI = miPieza.getPosI() + (++celdasMovidas * direccionI);
                        movimientoJ = miPieza.getPosJ() + (celdasMovidas * direccionJ);
                        if (enRango(movimientoI,movimientoJ) && !tablero.getCelda(movimientoI, movimientoJ).getTienePieza())
                            toReturn.addLast(new Pair<>(movimientoI, movimientoJ));
                    }
                    noMas = true;
                }
            }
            else
                noMas = true;
        }
    }

    private boolean enRango(int movimientoI, int movimientoJ){
        return movimientoI >= 0 && movimientoI < tablero.getLadoMatriz() && movimientoJ >= 0 && movimientoJ < tablero.getLadoMatriz();
    }
}
