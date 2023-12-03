package Clases;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Curacion {
    private int x; // Posición X de la curación
    private int y; // Posición Y de la curación
    private JLabel etiquetaCuracion;
    private final int valorCuracion = 200; // Cantidad de vida que restaura

    public Curacion(int x, int y) {
        this.x = x;
        this.y = y;
        ImageIcon iconoCuracion = new ImageIcon("/Users/eric/Desktop/Texturas/Curacion.png"); // Cambia a la ruta de tu imagen
        etiquetaCuracion = new JLabel(iconoCuracion);
        etiquetaCuracion.setBounds(x, y, iconoCuracion.getIconWidth(), iconoCuracion.getIconHeight());
    }

    public JLabel getEtiquetaCuracion() {
        return etiquetaCuracion;
    }

    public int getValorCuracion() {
        return valorCuracion;
    }


}
