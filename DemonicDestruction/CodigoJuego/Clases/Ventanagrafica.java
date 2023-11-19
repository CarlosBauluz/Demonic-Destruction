    package Clases;
    import javax.swing.*;
    import java.awt.*;

    /**
     * The type Ventanagrafica.
     */
    public class Ventanagrafica extends JFrame {

        /**
         * Instantiates a new Ventanagrafica.
         */
        public Ventanagrafica() {
            super("Game Window");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel menuPanel = new JPanel();
            JButton startButton = new JButton("EMPEZAR JUEGO");
            startButton.addActionListener(e -> iniciarJuego());
            JButton closeButton = new JButton("CERRAR JUEGO");
            closeButton.addActionListener(e -> System.exit(0));

            menuPanel.add(startButton);
            menuPanel.add(closeButton);
            add(menuPanel, BorderLayout.CENTER);

            setVisible(true);
        }

        private void iniciarJuego() {
            getContentPane().removeAll();
            getContentPane().add(new Ventanajuego());
            revalidate();
            repaint();
        }

        /**
         * The entry point of application.
         *
         * @param args the input arguments
         */
        public static void main(String[] args) {
            new Ventanagrafica();
        }
    }
