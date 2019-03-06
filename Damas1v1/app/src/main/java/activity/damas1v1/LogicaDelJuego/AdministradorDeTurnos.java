package activity.damas1v1.LogicaDelJuego;

import activity.damas1v1.Jugadores.Jugador;

class AdministradorDeTurnos {
    private Jugador jugadorDeTurno, jugador1, jugador2;
    private Juego juego;

    AdministradorDeTurnos(Juego jue, Jugador j1, Jugador j2){
        juego = jue;
        jugador1 = j1;
        jugador2 = j2;
    }

    void setearTurno(Jugador jugador){
        jugadorDeTurno = jugador;
        juego.habilitarJugador(jugadorDeTurno);
    }

    void cambiarTurno(){
        if (jugadorDeTurno == jugador1)
            setearTurno(jugador2);
        else
            setearTurno(jugador1);
    }
}
