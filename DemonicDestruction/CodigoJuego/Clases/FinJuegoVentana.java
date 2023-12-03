package Clases;

import javax.swing.*;
import java.awt.event.*;

public class FinJuegoVentana extends JDialog {
    private int tiempoVivido;
    private int enemigosEnfrentados;

    public FinJuegoVentana(JFrame parent, int tiempoVivido, int enemigosEnfrentados) {
        super(parent, "Fin del Juego", true);
        this.tiempoVivido = tiempoVivido;
        this.enemigosEnfrentados = enemigosEnfrentados;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(new JLabel("Has sobrevivido: " + tiempoVivido + " segundos"));
        add(new JLabel("Enemigos enfrentados: " + enemigosEnfrentados));

        JButton btnReiniciar = new JButton("Volver a jugar");
        btnReiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parent.dispose();
                String[] args = {}; // Argumentos vacíos o lo que necesites
                Ventanajuego.main(args); // Reiniciar el juego
            }
        });
        add(btnReiniciar);

        JButton btnSalir = new JButton("Abandonar partida");
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(btnSalir);

        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
    }
}
