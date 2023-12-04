package Clases;

import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.List;

import java.util.Collections;


/**
 * The type Oleadas.
 */
public class Oleadas {
    private List<Monstruo> enemigos;

    private Random random;
    private final int MAX_ENEMIGOS = 4; // Máximo de enemigos en pantalla
    private final int DISTANCIA_GENERACION = 40; // Distancia desde el jugador para generar enemigos

    private int numeroEnemigosEnfrentados=0;

    private int oleadaActual = 0;
    private final int ENEMIGOS_POR_OLEADA = 5;


    /**
     * Instantiates a new Oleadas.
     */
    public Oleadas() {
        enemigos = Collections.synchronizedList(new ArrayList<>());

        // Añadir enemigos a la lista
    }

    /**
     * Nueva oleada.
     *
     * @param juego the juego
     */
    public void nuevaOleada(Ventanajuego juego) {
        oleadaActual++;
        juego.setMaxEnemigos(oleadaActual + 4); // Incrementa el número máximo de enemigos con cada oleada
        juego.generarOleada(); // Llama al método para generar la oleada en Ventanajuego
    }

    /**
     * Gets oleada actual.
     *
     * @return the oleada actual
     */
    public int getOleadaActual() {
        return oleadaActual;
    }

    /**
     * Agregar enemigo.
     *
     * @param enemigo the enemigo
     */
    public void agregarEnemigo(Monstruo enemigo) {
        enemigos.add(enemigo);
        System.out.println("Enemigo agregado a Oleadas: " + enemigo);
    }

    /**
     * Eliminar enemigo.
     *
     * @param enemigo the enemigo
     */
    public void eliminarEnemigo(Monstruo enemigo) {
        enemigos.remove(enemigo);
        System.out.println("Enemigo eliminado de Oleadas: " + enemigo);
        numeroEnemigosEnfrentados=numeroEnemigosEnfrentados+1;
    }

    /**
     * Gets numero enemigos enfrentados.
     *
     * @return the numero enemigos enfrentados
     */
    public int getNumeroEnemigosEnfrentados() {
        // Retorna el número de enemigos enfrentados
        // Debes tener algún mecanismo para contar los enemigos enfrentados
        return numeroEnemigosEnfrentados; // Asegúrate de que esta variable exista y se actualice adecuadamente
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
        // Crear una copia de la lista para evitar ConcurrentModificationException
        List<Monstruo> copiaEnemigos = new ArrayList<>(enemigos);
        jugador.atacar(copiaEnemigos); // El jugador ataca a los enemigos en su área
        eliminarEnemigosMuertos(); // Elimina enemigos que ya no están vivos
    }

    /**
     * Eliminar enemigos muertos.
     */
// Método para eliminar enemigos muertos de la lista
    public void eliminarEnemigosMuertos() {
        // Usar un iterador para eliminar elementos mientras se itera
        for (Iterator<Monstruo> it = enemigos.iterator(); it.hasNext();) {
            Monstruo enemigo = it.next();
            if (!enemigo.estaVivo()) {
                it.remove(); // Eliminar de la lista original
            }
        }
    }

    /**
     * Gets enemigos.
     *
     * @return the enemigos
     */
    public ArrayList<Monstruo> getEnemigos() {
        return new ArrayList<>(enemigos);
    }


    /**
     * Gets max enemigos.
     *
     * @return the max enemigos
     */
    public int getMAX_ENEMIGOS() {
        return MAX_ENEMIGOS;
    }

    /**
     * Actualizar juego.
     *
     * @param jugador  the jugador
     * @param xJugador the x jugador
     * @param yJugador the y jugador
     */
    public void actualizarJuego(Jugador jugador, int xJugador, int yJugador) {
        generarEnemigos(xJugador, yJugador);
        actualizarEstado(jugador);
    }


}