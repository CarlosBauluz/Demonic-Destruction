package Clases;
import javax.swing.*;
import java.awt.*;

public class Ventanagrafica extends JFrame {

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

    public Ventanagrafica() {
        super("Game Window");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Load the background image
        backgroundImage = new ImageIcon("/Users/eric/Desktop/Texturas/Fondo menu.png").getImage();

        ImagePanel menuPanel = new ImagePanel();
        GridBagConstraints gbc = new GridBagConstraints();

        JButton startButton = new JButton("EMPEZAR JUEGO");
        startButton.addActionListener(e -> iniciarJuego());
        JButton closeButton = new JButton("CERRAR JUEGO");
        closeButton.addActionListener(e -> System.exit(0));

        // Set up the layout constraints for the buttons
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        menuPanel.add(startButton, gbc);

        gbc.gridy = 1;
        menuPanel.add(closeButton, gbc);

        add(menuPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void iniciarJuego() {
        getContentPane().removeAll();
        JFrame frame = new JFrame();
        Ventanajuego juego = new Ventanajuego(frame);
        getContentPane().add(juego);
        juego.requestFocusInWindow(); // Solicitar el foco aquí
        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        new Ventanagrafica();
    }
}
