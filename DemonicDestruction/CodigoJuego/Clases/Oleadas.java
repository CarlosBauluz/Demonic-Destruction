package Clases;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;



public class Oleadas {
    private ArrayList<Monstruo> enemigos;


    private Random random;
    private final int MAX_ENEMIGOS = 100; // Máximo de enemigos en pantalla
    private final int DISTANCIA_GENERACION = 500; // Distancia desde el jugador para generar enemigos

    public Oleadas() {
        enemigos = new ArrayList<>();
        random = new Random();
    }

    public void generarEnemigos(int xJugador, int yJugador) {
        if (enemigos.size() < MAX_ENEMIGOS) {
            int xEnemigo = xJugador + random.nextInt(DISTANCIA_GENERACION) - DISTANCIA_GENERACION / 2;
            int yEnemigo = yJugador + random.nextInt(DISTANCIA_GENERACION) - DISTANCIA_GENERACION / 2;
            TipoMonstruos tipo = TipoMonstruos.values()[random.nextInt(TipoMonstruos.values().length)];
            Monstruo nuevoEnemigo = new Monstruo(tipo);
            nuevoEnemigo.multiplier(random.nextInt(5) + 1); // Nivel aleatorio entre 1 y 5
            enemigos.add(nuevoEnemigo);
        }
    }

    public void actualizarEstado(Jugador jugador) {
        jugador.atacar(enemigos); // El jugador ataca a los enemigos en su área
        eliminarEnemigosMuertos(); // Elimina enemigos que ya no están vivos
    }

    // Método para eliminar enemigos muertos de la lista
    public void eliminarEnemigosMuertos() {
        for (Iterator<Monstruo> it = enemigos.iterator(); it.hasNext();) {
            Monstruo enemigo = it.next();
            if (!enemigo.estaVivo()) { // Cambiar a estaVivo
                // Eliminar la etiqueta del enemigo y su etiqueta de vida de la interfaz gráfica
                it.remove();
            }
        }
    }




    public ArrayList<Monstruo> getEnemigos() {
        return enemigos;
    }

    public int getMAX_ENEMIGOS() {
        return MAX_ENEMIGOS;
    }

    public void actualizarJuego(Jugador jugador, int xJugador, int yJugador) {
        generarEnemigos(xJugador, yJugador);
        actualizarEstado(jugador);
    }


}