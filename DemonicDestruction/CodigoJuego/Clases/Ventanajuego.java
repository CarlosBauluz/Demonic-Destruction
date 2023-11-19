package Clases;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Ventanajuego.
 */
public class Ventanajuego extends JPanel implements KeyListener {

        private Point ultimaPosicionPersonaje;
        private ImageIcon iconoFondo;
        private JLabel etiquetaFondo;
        private int xFondo = 0;
        private int yFondo = 0;
        private final int MOVIMIENTO_PASO = 5;

        private ImageIcon iconoPersonaje;
        private JLabel etiquetaPersonaje;
        private ArrayList<JLabel> etiquetasEnemigos;
        private Random random;

        private JLabel etiquetaTiempo, etiquetaVida;

        private Timer timer;
        private int tiempo = 0;
        private int vida = 100;

        /**
         * Instantiates a new Ventanajuego.
         */
        public Ventanajuego() {
                setLayout(null);
                addKeyListener(this);
                setFocusable(true);



                // Inicializar la última posición del personaje
                ultimaPosicionPersonaje = new Point(300, 200);

                // Cargar y configurar el fondo
                iconoFondo = new ImageIcon("/Users/eric/Desktop/a45ac4da3c58e873c3e49a021de8895a.jpg");
                etiquetaFondo = new JLabel(iconoFondo);
                etiquetaFondo.setBounds(0, 0, iconoFondo.getIconWidth(), iconoFondo.getIconHeight());
                add(etiquetaFondo, Integer.valueOf(0)); // Fondo en la capa más baja

                // Cargar y configurar el personaje
                iconoPersonaje = new ImageIcon("/Users/eric/Desktop/personaje_resized.png");
                etiquetaPersonaje = new JLabel(iconoPersonaje);
                etiquetaPersonaje.setBounds(300, 200, iconoPersonaje.getIconWidth(), iconoPersonaje.getIconHeight());
                add(etiquetaPersonaje, Integer.valueOf(2)); // Personaje en una capa superior

                // Inicializar enemigos
                etiquetasEnemigos = new ArrayList<>();
                random = new Random();
                generarEnemigos();

                // Timer para mover enemigos y actualizar tiempo y vida
                timer = new Timer(100, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                moverEnemigosHaciaUltimaPosicion();
                                tiempo++;
                                actualizarEtiquetas();
                        }
                });
                timer.start();

                // Etiquetas para tiempo y vida
                etiquetaTiempo = new JLabel("Tiempo: 0");
                etiquetaTiempo.setBounds(10, 10, 100, 20);
                add(etiquetaTiempo);

                etiquetaVida = new JLabel("Vida: 100");
                etiquetaVida.setBounds(10, 30, 100, 20);
                add(etiquetaVida);
        }


        private void generarEnemigos() {
                for (int i = 0; i < 10; i++) {
                        ImageIcon iconoEnemigo = new ImageIcon("/Users/eric/Desktop/PHOTO-2023-10-29-23-37-35-veed-remove-background.png");
                        JLabel etiquetaEnemigo = new JLabel(iconoEnemigo);
                        int x = random.nextInt(600);
                        int y = random.nextInt(400);
                        etiquetaEnemigo.setBounds(x, y, iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());
                        etiquetasEnemigos.add(etiquetaEnemigo);
                        add(etiquetaEnemigo, Integer.valueOf(2)); // Enemigos en la misma capa que el personaje
                }
        }
        private void moverEnemigos() {
                for (JLabel etiquetaEnemigo : etiquetasEnemigos) {
                        Point posicionEnemigo = etiquetaEnemigo.getLocation();
                        int dx = etiquetaPersonaje.getX() - posicionEnemigo.x;
                        int dy = etiquetaPersonaje.getY() - posicionEnemigo.y;
                        if (dx != 0) dx /= Math.abs(dx);
                        if (dy != 0) dy /= Math.abs(dy);
                        etiquetaEnemigo.setLocation(posicionEnemigo.x + dx, posicionEnemigo.y + dy);
                }
        }

        private void actualizarEtiquetas() {
                etiquetaTiempo.setText("Tiempo: " + tiempo);
                etiquetaVida.setText("Vida: " + vida);
        }


        @Override
        public void keyPressed(KeyEvent e) {
                int velocidadPersonaje = 10; // Velocidad de movimiento del personaje

                int dx = 0;
                int dy = 0;

                switch (e.getKeyCode()) {
                        case KeyEvent.VK_W:
                                dy = -velocidadPersonaje;
                                break;
                        case KeyEvent.VK_S:
                                dy = +velocidadPersonaje;
                                break;
                        case KeyEvent.VK_A:
                                dx = -velocidadPersonaje;
                                break;
                        case KeyEvent.VK_D:
                                dx = +velocidadPersonaje;
                                break;
                }

                // Actualizar la posición del personaje y del fondo
                actualizarPosicionPersonajeYFondo(dx, dy);
        }

        private void actualizarPosicionPersonajeYFondo(int dx, int dy) {
                // Mover el fondo en dirección opuesta a la entrada del teclado
                xFondo -= dx;
                yFondo -= dy;
                etiquetaFondo.setLocation(xFondo, yFondo);

                // Mover los enemigos en la misma dirección que el fondo
                for (JLabel etiquetaEnemigo : etiquetasEnemigos) {
                        Point posicionEnemigo = etiquetaEnemigo.getLocation();
                        etiquetaEnemigo.setLocation(posicionEnemigo.x - dx, posicionEnemigo.y - dy);
                }

                // Mantener el personaje en el centro de la pantalla
                etiquetaPersonaje.setLocation(getWidth() / 2 - etiquetaPersonaje.getWidth() / 2,
                        getHeight() / 2 - etiquetaPersonaje.getHeight() / 2);

                // Actualizar la última posición conocida del personaje
                ultimaPosicionPersonaje.setLocation(-xFondo + getWidth() / 2 - etiquetaPersonaje.getWidth() / 2,
                        -yFondo + getHeight() / 2 - etiquetaPersonaje.getHeight() / 2);
        }





        private void moverEnemigosHaciaUltimaPosicion() {
                int velocidadEnemigo = 5; // Velocidad de movimiento de los enemigos

                for (JLabel etiquetaEnemigo : etiquetasEnemigos) {
                        Point posicionEnemigo = etiquetaEnemigo.getLocation();
                        int dx = etiquetaPersonaje.getX() - posicionEnemigo.x;
                        int dy = etiquetaPersonaje.getY() - posicionEnemigo.y;
                        if (dx != 0) dx /= Math.abs(dx);
                        if (dy != 0) dy /= Math.abs(dy);
                        etiquetaEnemigo.setLocation(posicionEnemigo.x + dx * velocidadEnemigo, posicionEnemigo.y + dy * velocidadEnemigo);
                }
        }




        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}


        /**
         * The entry point of application.
         *
         * @param args the input arguments
         */
        public static void main(String[] args) {
                JFrame frame = new JFrame();
                Ventanajuego juego = new Ventanajuego();
                frame.add(juego);
                frame.setSize(600, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}
