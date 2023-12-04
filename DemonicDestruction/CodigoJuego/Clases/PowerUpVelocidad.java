package Clases;

import javax.swing.Timer;

/**
 * The type Power up velocidad.
 */
public class PowerUpVelocidad extends PowerUp {
    private final int aumentoVelocidad;
    private final int duracion; // Duración del efecto en milisegundos

    /**
     * Instantiates a new Power up velocidad.
     *
     * @param x                the x
     * @param y                the y
     * @param rutaImagen       the ruta imagen
     * @param aumentoVelocidad the aumento velocidad
     * @param duracion         the duracion
     */
    public PowerUpVelocidad(int x, int y, String rutaImagen, int aumentoVelocidad, int duracion) {
        super(x, y, rutaImagen);
        this.aumentoVelocidad = aumentoVelocidad;
        this.duracion = duracion;
    }

    @Override
    public void activar(Jugador jugador) {
        jugador.aumentarVelocidad(aumentoVelocidad); // Aumenta la velocidad del jugador
        // Temporizador para revertir el efecto después de la duración
        new Timer(duracion, e -> jugador.disminuirVelocidad(aumentoVelocidad)).start();
    }
}

