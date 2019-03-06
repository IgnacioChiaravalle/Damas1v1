package activity.damas1v1.Piezas;

import android.util.Pair;
import activity.damas1v1.LogicaDelJuego.MatrizTablero;
import activity.damas1v1.TDAListaDE.ListaDoblementeEnlazada;
import activity.damas1v1.TDAListaDE.PositionList;

class InteligenciaPeon extends InteligenciaPieza {
    /* En esta inteligencia no necesitaré chequear que no se salga del tablero en
     * getProximoI pues no habrá jamás un peón en el borde superior/inferior
     * del tablero y que necesite subir/bajar, sino sólo lo contrario.
     */
    InteligenciaPeon(Pieza pieza, MatrizTablero t){
        super(pieza, t);
        miPieza.setGrafico(miPieza.getJugador().getColorDePeones());
    }

    PositionList<Pair<Integer,Integer>> verDisponibles(){
        PositionList<Pair<Integer,Integer>> toReturn = new ListaDoblementeEnlazada<>();

        boolean[] arreglo = seteosBooleanos();
        boolean noExcedeSiguienteProximoI = arreglo[10];

        boolean noExcedeProximoJ = arreglo[0], hayPiezaAUnaCelda = arreglo[1], jugadoresDistintos = arreglo[2], noExcedeSiguienteProximoJ = arreglo[3], noHayPiezaADosCeldas = arreglo[4];
        if (noExcedeProximoJ) {
            if (!hayPiezaAUnaCelda)
                toReturn.addLast(new Pair<>(miPieza.getProximoI(),miPieza.getPosJ() - 1));
            else {
                if (jugadoresDistintos && noExcedeSiguienteProximoI && noExcedeSiguienteProximoJ && noHayPiezaADosCeldas)
                    toReturn.addLast(new Pair<>(miPieza.getSiguienteProximoI(),miPieza.getPosJ() - 2));
            }
        }

        noExcedeProximoJ = arreglo[5]; hayPiezaAUnaCelda = arreglo[6]; jugadoresDistintos = arreglo[7]; noExcedeSiguienteProximoJ = arreglo[8]; noHayPiezaADosCeldas = arreglo[9];
        if (noExcedeProximoJ) {
            if (!hayPiezaAUnaCelda)
                toReturn.addLast(new Pair<>(miPieza.getProximoI(),miPieza.getPosJ() + 1));
            else {
                if (jugadoresDistintos && noExcedeSiguienteProximoI && noExcedeSiguienteProximoJ && noHayPiezaADosCeldas)
                    toReturn.addLast(new Pair<>(miPieza.getSiguienteProximoI(),miPieza.getPosJ() + 2));
            }
        }

        return toReturn;
    }

    PositionList<Pair<Integer,Integer>> verProximaComida(){
        PositionList<Pair<Integer,Integer>> toReturn = new ListaDoblementeEnlazada<>();
        boolean[] arreglo = seteosBooleanos();
        boolean noExcedeSiguienteProximoI = arreglo[10];

        boolean noExcedeProximoJ = arreglo[0], hayPiezaAUnaCelda = arreglo[1], jugadoresDistintos = arreglo[2], noExcedeSiguienteProximoJ = arreglo[3], noHayPiezaADosCeldas = arreglo[4];
        if (noExcedeProximoJ && hayPiezaAUnaCelda && jugadoresDistintos && noExcedeSiguienteProximoI && noExcedeSiguienteProximoJ && noHayPiezaADosCeldas)
            toReturn.addLast(new Pair<>(miPieza.getSiguienteProximoI(),miPieza.getPosJ() - 2));

        noExcedeProximoJ = arreglo[5]; hayPiezaAUnaCelda = arreglo[6]; jugadoresDistintos = arreglo[7]; noExcedeSiguienteProximoJ = arreglo[8]; noHayPiezaADosCeldas = arreglo[9];
        if (noExcedeProximoJ && hayPiezaAUnaCelda && jugadoresDistintos && noExcedeSiguienteProximoI && noExcedeSiguienteProximoJ && noHayPiezaADosCeldas)
            toReturn.addLast(new Pair<>(miPieza.getSiguienteProximoI(),miPieza.getPosJ() + 2));

        return toReturn;
    }

    private boolean[] seteosBooleanos(){
        boolean[] arreglo = new boolean[11];
        arreglo[0] = miPieza.getPosJ() - 1 >= 0;

        if (arreglo[0] && enRangoVertical())
            arreglo[1] = tablero.getCelda(miPieza.getProximoI(),miPieza.getPosJ() - 1).getTienePieza();
        else
            arreglo[1] = false;

        if (arreglo[1])
            arreglo[2] = tablero.getCelda(miPieza.getProximoI(),miPieza.getPosJ() - 1).getPieza().getJugador() != miPieza.getJugador();
        else
            arreglo[2] = false;

        arreglo[3] = miPieza.getPosJ() - 2 >= 0;
        if (arreglo[3] && enRangoVerticalDoble())
            arreglo[4] = !tablero.getCelda(miPieza.getSiguienteProximoI(),miPieza.getPosJ() - 2).getTienePieza();
        else
            arreglo[4] = false;


        arreglo[5] = miPieza.getPosJ() + 1 < tablero.getLadoMatriz();
        if (arreglo[5] && enRangoVertical())
            arreglo[6] = tablero.getCelda(miPieza.getProximoI(),miPieza.getPosJ() + 1).getTienePieza();
        else
            arreglo[6] = false;

        if (arreglo[6])
            arreglo[7] = tablero.getCelda(miPieza.getProximoI(),miPieza.getPosJ() + 1).getPieza().getJugador() != miPieza.getJugador();
        else
            arreglo[7] = false;

        arreglo[8] = miPieza.getPosJ() + 2 < tablero.getLadoMatriz();
        if (arreglo[8] && enRangoVerticalDoble())
            arreglo[9] = !tablero.getCelda(miPieza.getSiguienteProximoI(),miPieza.getPosJ() + 2).getTienePieza();
        else
            arreglo[9] = false;


        arreglo[10] = (miPieza.getSiguienteProximoI() >= 0) && (miPieza.getSiguienteProximoI() < tablero.getLadoMatriz());
        return arreglo;
    }
        private boolean enRangoVertical(){
            return miPieza.getProximoI() >= 0 && miPieza.getProximoI() < tablero.getLadoMatriz();
        }
        private boolean enRangoVerticalDoble(){
            return miPieza.getSiguienteProximoI() >= 0 && miPieza.getSiguienteProximoI() < tablero.getLadoMatriz();
        }
}
