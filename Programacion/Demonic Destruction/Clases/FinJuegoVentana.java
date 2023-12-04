package Clases;

import javax.swing.*;
import java.awt.*;

public class FinJuegoVentana extends JDialog {
    private int tiempoVivido; // Asumo que esto está en ticks, donde cada tick es 1/10 de segundo
    private int enemigosEnfrentados;
    private Image backgroundImage;

    // Custom panel with background image
    private class ImagePanel extends JPanel {
        public ImagePanel() {
            setOpaque(false);
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public FinJuegoVentana(JFrame parent, int tiempoVivido, int enemigosEnfrentados) {
        super(parent, "Fin del Juego", true);
        this.tiempoVivido = tiempoVivido;
        this.enemigosEnfrentados = enemigosEnfrentados;

        // Load the background image
        backgroundImage = new ImageIcon("Fondo menu.png").getImage();

        ImagePanel panel = new ImagePanel();
        GridBagConstraints gbc = new GridBagConstraints();

        // Convertir tiempo a segundos primero (asumiendo que cada tick es 1/10 de segundo)
        int tiempoEnSegundos = tiempoVivido / 10;
        int minutos = tiempoEnSegundos / 60;
        int segundos = tiempoEnSegundos % 60;
        String tiempoFormateado = String.format("Has sobrevivido: %d minutos y %d segundos", minutos, segundos);

        JLabel labelTiempo = new JLabel(tiempoFormateado);
        labelTiempo.setOpaque(true);
        labelTiempo.setBackground(Color.WHITE);
        labelTiempo.setBorder(BorderFactory.createRaisedBevelBorder());
        labelTiempo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelEnemigos = new JLabel("Enemigos enfrentados: " + enemigosEnfrentados);
        labelEnemigos.setOpaque(true);
        labelEnemigos.setBackground(Color.WHITE);
        labelEnemigos.setBorder(BorderFactory.createRaisedBevelBorder());
        labelEnemigos.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnReiniciar = new JButton("Volver a jugar");
        btnReiniciar.addActionListener(e -> {
            parent.dispose();
            String[] args = {}; // Argumentos vacíos o lo que necesites
            Ventanajuego.main(args); // Reiniciar el juego
        });

        JButton btnSalir = new JButton("Abandonar partida");
        btnSalir.addActionListener(e -> System.exit(0));

        // Configuración de los componentes en el GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(labelTiempo, gbc);

        gbc.gridy = 1;
        panel.add(labelEnemigos, gbc);

        gbc.gridy = 2;
        panel.add(btnReiniciar, gbc);

        gbc.gridy = 3;
        panel.add(btnSalir, gbc);

        setContentPane(panel);
        setSize(1200, 900); // Tamaño más grande
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
    }
}
