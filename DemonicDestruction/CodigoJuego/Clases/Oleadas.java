package Clases;

import java.util.ArrayList;
import java.util.Random;


/**
 * The type Oleadas.
 */
public class Oleadas {
    private ArrayList<Monstruo> enemigos;


    private Random random;
    private final int MAX_ENEMIGOS = 100; // Máximo de enemigos en pantalla
    private final int DISTANCIA_GENERACION = 500; // Distancia desde el jugador para generar enemigos

    /**
     * Instantiates a new Oleadas.
     */
    public Oleadas() {
        enemigos = new ArrayList<>();
        random = new Random();
    }

    /**
     * Generar enemigos.
     *
     * @param xJugador the x jugador
     * @param yJugador the y jugador
     */
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

    /**
     * Actualizar estado.
     *
     * @param jugador the jugador
     */
    public void actualizarEstado(Jugador jugador) {
        jugador.atacar(enemigos); // El jugador ataca a los enemigos en su área
        eliminarEnemigosMuertos(); // Elimina enemigos que ya no están vivos
    }

    /**
     * Eliminar enemigos muertos.
     */
// Método para eliminar enemigos muertos de la lista
    public void eliminarEnemigosMuertos() {
        enemigos.removeIf(enemigo -> !enemigo.estaVivo());
    }

    /**
     * Gets enemigos.
     *
     * @return the enemigos
     */
    public ArrayList<Monstruo> getEnemigos() {
        return enemigos;
    }

    /**
     * Gets max enemigos.
     *
     * @return the max enemigos
     */
    public int getMAX_ENEMIGOS() {
        return MAX_ENEMIGOS;
    }
}