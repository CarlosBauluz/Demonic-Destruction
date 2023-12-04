package Clases;

import javax.swing.Timer;

public class PowerUpVelocidad extends PowerUp {
    private final int aumentoVelocidad;
    private final int duracion; // Duración del efecto en milisegundos

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

