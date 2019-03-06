package activity.damas1v1.LogicaDelJuego;

import android.graphics.Color;
import android.widget.LinearLayout;

public class MatrizTablero {
    private Celda[][] matriz;
    private int ladoCelda;
    private Juego juego;
    private int ladoMatriz;

    MatrizTablero(int ladoMat, int ladoCel, Juego ju){
        ladoMatriz = ladoMat;
        ladoCelda = ladoCel;
        juego = ju;
        matriz = new Celda[ladoMatriz][ladoMatriz];
        for (int i = 0; i < ladoMatriz; i++){
            for (int j = 0; j < ladoMatriz; j++)
                nuevaCelda(i, j);
        }
    }

    private void nuevaCelda(int i, int j){
        matriz[i][j] = new Celda(juego, i, j);
        if ((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)) {
            matriz[i][j].setBackgroundColor(Color.RED);
            matriz[i][j].setEnabled(false);
        }
        else
            deshabilitarCelda(i,j);
        matriz[i][j].setLayoutParams(new LinearLayout.LayoutParams(ladoCelda,ladoCelda));
    }

    public Celda getCelda(int i, int j){
        return matriz[i][j];
    }

    void habilitarCelda(int i, int j){
        matriz[i][j].setEnabled(true);
        matriz[i][j].setBackgroundColor(Color.BLACK);
    }
    void deshabilitarCelda(int i, int j){
        matriz[i][j].setEnabled(false);
        matriz[i][j].setBackgroundColor(Color.rgb(60,60,60));
    }

    public int getLadoMatriz(){ return ladoMatriz; }
}
