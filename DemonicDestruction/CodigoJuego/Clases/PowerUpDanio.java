package Clases;

import javax.swing.Timer;

public class PowerUpDanio extends PowerUp {
    private final int aumentoDanio;
    private final int duracion; // Duración del efecto en milisegundos

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