package Clases;

import javax.swing.*;
import javax.swing.JLabel;


public abstract class PowerUp {
    protected int x; // Posición X del power-up
    protected int y; // Posición Y del power-up
    protected JLabel etiquetaPowerUp;

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

    public JLabel getEtiquetaPowerUp() {
        return etiquetaPowerUp;
    }

    public abstract void activar(Jugador jugador); // Método abstracto para activar el power-up
}
