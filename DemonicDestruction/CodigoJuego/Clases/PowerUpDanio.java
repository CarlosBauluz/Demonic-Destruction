package Clases;

import javax.swing.Timer;

/**
 * The type Power up danio.
 */
public class PowerUpDanio extends PowerUp {
    private final int aumentoDanio;
    private final int duracion; // Duración del efecto en milisegundos

    /**
     * Instantiates a new Power up danio.
     *
     * @param x            the x
     * @param y            the y
     * @param rutaImagen   the ruta imagen
     * @param aumentoDanio the aumento danio
     * @param duracion     the duracion
     */
    public PowerUpDanio(int x, int y, String rutaImagen, int aumentoDanio, int duracion) {
        super(x, y, rutaImagen);
        this.aumentoDanio = aumentoDanio;
        this.duracion = duracion;
    }

    @Override
    public void activar(Jugador jugador) {
        jugador.aumentarDanio(aumentoDanio); // Aumenta el daño del jugador
        // Temporizador para revertir el efecto después de la duración
        new Timer(duracion, e -> jugador.disminuirDanio(aumentoDanio)).start();
    }
}