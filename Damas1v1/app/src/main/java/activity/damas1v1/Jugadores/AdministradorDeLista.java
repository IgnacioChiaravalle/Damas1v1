package activity.damas1v1.Jugadores;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import activity.damas1v1.TDAListaDE.BoundaryViolationException;
import activity.damas1v1.TDAListaDE.EmptyListException;
import activity.damas1v1.TDAListaDE.InvalidPositionException;
import activity.damas1v1.TDAListaDE.ListaDoblementeEnlazada;
import activity.damas1v1.TDAListaDE.Position;
import activity.damas1v1.TDAListaDE.PositionList;

public class AdministradorDeLista implements Serializable {
    private PositionList<Jugador> listadoJugadores;
    private PositionList<String> textoEnLista;
    private final String NOMBRE_DEL_ARCHIVO = "Listado de Jugadores.txt";

    public AdministradorDeLista(Context contexto) {
        listadoJugadores = new ListaDoblementeEnlazada<>();
        textoEnLista = new ListaDoblementeEnlazada<>();
        llenarTextoEnLista(contexto);
        llenarListaJugadores();
    }

        private void llenarTextoEnLista(Context contexto){
            FileInputStream fIS = null;
            try {
                fIS = contexto.openFileInput(NOMBRE_DEL_ARCHIVO);
                InputStreamReader iSR = new InputStreamReader(fIS);
                BufferedReader bR = new BufferedReader(iSR);
                String textoEnLinea;
                while ((textoEnLinea = bR.readLine()) != null)
                    textoEnLista.addLast(textoEnLinea);
            }
            catch (IOException exc) {
                System.out.println("Error al leer el archivo del Listado de Jugadores.");
                exc.printStackTrace();
            }
            finally {
                if (fIS != null) {
                    try { fIS.close(); }
                    catch (IOException e) { e.printStackTrace(); }
                }
            }
        }

        private void llenarListaJugadores(){
            try {
                Position<String> lineaActual = textoEnLista.isEmpty() ? null : textoEnLista.first();
                if (lineaActual != null) {
                    while(lineaActual != textoEnLista.last()) {
                        Jugador jugador = new Jugador(lineaActual.element());
                        lineaActual = textoEnLista.next(lineaActual);
                        int posicion = 0;
                        for (int i = 0; i < 4; i++)
                            posicion = leerUnNumero(lineaActual.element(), posicion, jugador, i);
                        jugador.actualizarPromedio();
                        listadoJugadores.addLast(jugador);
                        lineaActual = textoEnLista.next(lineaActual);
                    }
                }
            }
            catch(EmptyListException | InvalidPositionException | BoundaryViolationException exc) {
                System.out.println("Error al leer el archivo del Listado de Jugadores.");
                exc.printStackTrace();
            }
        }
            private int leerUnNumero(String linea, int posicion, Jugador jugador, int valor) {
                char caracter = linea.charAt(posicion);
                StringBuilder numero = new StringBuilder();
                while (caracter != ' '){
                    numero.append(caracter);
                    caracter = linea.charAt(++posicion);
                }
                switch (valor){
                    case 0:
                        jugador.setPartidasJugadas(Double.parseDouble(numero.toString()));
                        break;
                    case 1:
                        jugador.setVictorias(Double.parseDouble(numero.toString()));
                        break;
                    case 2:
                        jugador.setDerrotas(Double.parseDouble(numero.toString()));
                        break;
                    case 3:
                        jugador.setEmpates(Double.parseDouble(numero.toString()));
                        break;
                }
                return posicion + 1;
            }

    void quitarJugador(Position<Jugador> posJugador){
        try {
            listadoJugadores.remove(posJugador);
        } catch (InvalidPositionException e) {
            e.printStackTrace();
        }
    }

    public PositionList<Jugador> getListadoJugadores() { return listadoJugadores; }

    public void sobreescribirArchivo(Context contexto){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = contexto.openFileOutput(NOMBRE_DEL_ARCHIVO, Context.MODE_PRIVATE);
            StringBuilder stringBuilder = new StringBuilder();
            for (Position<Jugador> posJ : listadoJugadores.positions()) {
                Jugador jugador = posJ.element();
                String escritura = jugador.getNombre() + "\n" + jugador.getPartidasJugadas() + " " + jugador.getVictorias() + " "
                                   + jugador.getDerrotas() + " " + jugador.getEmpates() + " ";
                stringBuilder.append(escritura);
                stringBuilder.append("\n");
            }
            stringBuilder.append(" ");
            fileOutputStream.write(stringBuilder.toString().getBytes());
        }
        catch(IOException exc) {
            System.out.println("Error al sobreescribir el archivo del Listado de Jugadores.");
            exc.printStackTrace();
        }
        finally {
            if (fileOutputStream != null) {
                try { fileOutputStream.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }
}
