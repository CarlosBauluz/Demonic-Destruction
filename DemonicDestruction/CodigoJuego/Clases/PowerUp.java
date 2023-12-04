package Clases;

import javax.swing.*;
import javax.swing.JLabel;


/**
 * The type Power up.
 */
public abstract class PowerUp {
    /**
     * The X.
     */
    protected int x; // Posición X del power-up
    /**
     * The Y.
     */
    protected int y; // Posición Y del power-up
    /**
     * The Etiqueta power up.
     */
    protected JLabel etiquetaPowerUp;

    /**
     * Instantiates a new Power up.
     *
     * @param x          the x
     * @param y          the y
     * @param rutaImagen the ruta imagen
     */
    public PowerUp(int x, int y, String rutaImagen) {
        this.x = x;
        this.y = y;
        inicializarEtiqueta(rutaImagen);
    }

    private void inicializarEtiqueta(String rutaImagen) {
        ImageIcon iconoPowerUp = new ImageIcon(rutaImagen);
        etiquetaPowerUp = new JLabel(iconoPowerUp);
        etiquetaPowerUp.setBounds(x, y, iconoPowerUp.getIconWidth(), iconoPowerUp.getIconHeight());
    }

    /**
     * Gets etiqueta power up.
     *
     * @return the etiqueta power up
     */
    public JLabel getEtiquetaPowerUp() {
        return etiquetaPowerUp;
    }

    /**
     * Activar.
     *
     * @param jugador the jugador
     */
    public abstract void activar(Jugador jugador); // Método abstracto para activar el power-up
}
