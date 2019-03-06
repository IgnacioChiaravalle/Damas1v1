package activity.damas1v1.Jugadores;

import java.io.Serializable;
import activity.damas1v1.ColoresDePiezas.ColorDePiezas;

public class Jugador implements Serializable {
    //Todos los valores deben ser 'doubles' para que la división funcione correctamente.
    private double partidasJugadas, victorias, derrotas, empates, promedioVictorias;
    private String nombre;
    private ColorDePiezas colorDePiezas;

    public Jugador(String nom){
        nombre = nom;
        partidasJugadas = 0;
        victorias = 0;
        derrotas = 0;
        empates = 0;
        promedioVictorias = 0;
    }

    public void sumarVictoria(){ victorias++; sumarValor(); }
    public void sumarDerrota(){ derrotas++; sumarValor(); }
    public void sumarEmpate(){ empates++; sumarValor(); }

    void quitarVictoria(){
        if (victorias > 0) { victorias--; restarValor(); }
    }
    void quitarDerrota(){
        if (derrotas > 0) { derrotas--; restarValor(); }
    }
    void quitarEmpate(){
        if (empates > 0) { empates--; restarValor(); }
    }

    private void sumarValor(){ partidasJugadas++; actualizarPromedio(); }
    private void restarValor(){ partidasJugadas--; actualizarPromedio(); }
    void actualizarPromedio() { promedioVictorias = (partidasJugadas > 0) ? victorias / partidasJugadas * 100 : 0; }

    int getPartidasJugadas(){ return (int) partidasJugadas; }
    int getVictorias(){ return (int) victorias; }
    int getDerrotas(){ return (int) derrotas; }
    int getEmpates(){ return (int) empates; }
    double getPromedioVictorias(){ return (int) promedioVictorias; }

    void setNombre(String n) { nombre = n; }
    public String getNombre() {return nombre; }

    void setPartidasJugadas(double pj) { partidasJugadas = pj; }
    void setVictorias(double v) { victorias = v; }
    void setDerrotas(double d) { derrotas = d; }
    void setEmpates(double e) { empates = e; }

    public void setColorDePiezas(ColorDePiezas c){ colorDePiezas = c; }
    public int getColorDePeones(){ return colorDePiezas.getPeon(); }
    public int getColorDeDamas(){ return colorDePiezas.getDama(); }

}
