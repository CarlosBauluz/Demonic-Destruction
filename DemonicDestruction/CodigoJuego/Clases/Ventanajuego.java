package Clases;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.*;
import javax.swing.Timer;


public class Ventanajuego extends JPanel implements KeyListener {

        private Jugador jugador;
        private Point ultimaPosicionPersonaje;
        private ImageIcon iconoFondo;
        private JLabel etiquetaFondo;
        private int xFondo = 0;
        private int yFondo = 0;
        private int MOVIMIENTO_PASO = 3;

        private ImageIcon iconoPersonaje;
        private JLabel etiquetaPersonaje;
        private ArrayList<JLabel> etiquetasEnemigos;
        private Random random;

        private JLabel etiquetaTiempo, etiquetaVida;

        private Timer timer;
        private int tiempo = 0;
        private int vida = 100;
        private Map<JLabel, Monstruo> mapaEtiquetaEnemigo;

        private final int ANCHO_MAPA = 5000; // Asumiendo el tamaño del mapa
        private final int ALTO_MAPA = 5000;
        private final int DISTANCIA_GENERACION_ENEMIGOS = 10;

        private final int MAX_ENEMIGOS = 40;

        public Ventanajuego() {
                Oleadas oleadas = new Oleadas();
                setLayout(null);
                addKeyListener(this);
                setFocusable(true);

                // Inicializar la última posición del personaje
                ultimaPosicionPersonaje = new Point(300, 200);

                // Inicializar el jugador
                jugador = new Jugador(this);
                iconoPersonaje = new ImageIcon("/Users/eric/Desktop/Texturas/personaje_resized.png");
                etiquetaPersonaje = new JLabel(iconoPersonaje);
                etiquetaPersonaje.setBounds(300, 200, iconoPersonaje.getIconWidth(), iconoPersonaje.getIconHeight());
                add(etiquetaPersonaje, Integer.valueOf(2)); // Personaje en una capa superior

                jugador.setEtiquetaPersonaje(etiquetaPersonaje);
                jugador.ataqueAutomatico(oleadas.getEnemigos());

                // Inicializar el mapa
                mapaEtiquetaEnemigo = new HashMap<JLabel, Monstruo>();

                // Inicializar enemigos
                etiquetasEnemigos = new ArrayList<>();
                random = new Random();
                generarEnemigos();

                mapaEtiquetaEnemigo = new HashMap<>();

                // Timer para mover enemigos y actualizar tiempo y vida
                timer = new Timer(100, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                moverEnemigosHaciaUltimaPosicion();
                                if (etiquetasEnemigos.size() < MAX_ENEMIGOS) {
                                        moverEnemigosHaciaUltimaPosicion();
                                        jugador.atacar(oleadas.getEnemigos());
                                        eliminarEnemigosMuertos(); // Elimina enemigos que ya no están vivos
                                        tiempo++;
                                        actualizarEtiquetas();

                                }
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

                // Cargar y configurar el fondo
                iconoFondo = new ImageIcon("/Users/eric/Desktop/Texturas/Mapajuego_2_2.pn");
                etiquetaFondo = new JLabel(iconoFondo);
                etiquetaFondo.setBounds(0, 0, iconoFondo.getIconWidth(), iconoFondo.getIconHeight());
                add(etiquetaFondo, Integer.valueOf(0)); // Fondo en la capa más baja
        }

        private void generarEnemigos() {
                // Generar enemigos fuera de la pantalla
                ImageIcon iconoEnemigo = new ImageIcon("/Users/eric/Desktop/Texturas/PHOTO-2023-10-29-23-37-35-veed-remove-background.png");
                JLabel etiquetaEnemigo, etiquetaVidaEnemigo;
                Monstruo enemigo;

                for (int i = 0; i < MAX_ENEMIGOS; i++) {
                        // Crear un nuevo enemigo
                        enemigo = new Monstruo(TipoMonstruos.BESTIA); // Aquí puedes variar el tipo de monstruo

                        // Crear la etiqueta para el enemigo
                        etiquetaEnemigo = new JLabel(iconoEnemigo);
                        etiquetaVidaEnemigo = new JLabel(String.valueOf(enemigo.getVida()));

                        int x = random.nextInt(ANCHO_MAPA + 2 * DISTANCIA_GENERACION_ENEMIGOS) - DISTANCIA_GENERACION_ENEMIGOS;
                        int y = random.nextInt(ALTO_MAPA + 2 * DISTANCIA_GENERACION_ENEMIGOS) - DISTANCIA_GENERACION_ENEMIGOS;
                        etiquetaEnemigo.setBounds(x, y, iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());
                        etiquetaVidaEnemigo.setBounds(x, y + iconoEnemigo.getIconHeight(), 50, 20);

                        // Asignar la posición al enemigo
                        enemigo.setPosicion(x, y);
                        enemigo.setTamaño(iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());

                        // Asignar la etiqueta de vida al enemigo
                        enemigo.setEtiquetaVida(etiquetaVidaEnemigo);

                        // Añadir las etiquetas al panel y al mapa
                        add(etiquetaEnemigo, Integer.valueOf(1)); // Enemigos en una capa entre el fondo y el personaje
                        add(etiquetaVidaEnemigo, Integer.valueOf(1)); // Asegúrate de que la etiqueta de vida también se añade
                        mapaEtiquetaEnemigo.put(etiquetaEnemigo, enemigo);

                        // Añadir la etiqueta del enemigo a la lista
                        etiquetasEnemigos.add(etiquetaEnemigo);
                }
        }


        private void actualizarEtiquetas() {
                etiquetaTiempo.setText("Tiempo: " + tiempo);
                etiquetaVida.setText("Vida: " + vida);
        }

        @Override
        public void keyPressed(KeyEvent e) {
                int velocidadPersonaje = MOVIMIENTO_PASO * 3; // Velocidad de movimiento del personaje

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
                                jugador.cambiarDireccionAtaque("IZQUIERDA");
                                dx = -velocidadPersonaje;
                                break;
                        case KeyEvent.VK_D:
                                jugador.cambiarDireccionAtaque("DERECHA");
                                dx = +velocidadPersonaje;
                                break;
                }

                // Actualizar la posición del personaje y del fondo
                actualizarPosicionPersonajeYFondo(dx, dy);
        }

        private void actualizarPosicionPersonajeYFondo(int dx, int dy) {
                // Calcular la nueva posición propuesta para el fondo
                int nuevoXFondo = xFondo - dx;
                int nuevoYFondo = yFondo - dy;

                // Calcular la nueva posición propuesta para el personaje
                int nuevaPosXPersonaje = -nuevoXFondo + getWidth() / 2 - etiquetaPersonaje.getWidth() / 2;
                int nuevaPosYPersonaje = -nuevoYFondo + getHeight() / 2 - etiquetaPersonaje.getHeight() / 2;

                // Verificar si la nueva posición está dentro de los límites del mapa
                if (nuevaPosXPersonaje >= 0 && nuevaPosXPersonaje <= ANCHO_MAPA - etiquetaPersonaje.getWidth()) {
                        xFondo = nuevoXFondo;
                }
                if (nuevaPosYPersonaje >= 0 && nuevaPosYPersonaje <= ALTO_MAPA - etiquetaPersonaje.getHeight()) {
                        yFondo = nuevoYFondo;
                }

                // Actualizar la posición del fondo
                etiquetaFondo.setLocation(xFondo, yFondo);

                // Mover los enemigos en la misma dirección que el fondo
                for (JLabel etiquetaEnemigo : etiquetasEnemigos) {
                        Point posicionEnemigo = etiquetaEnemigo.getLocation();
                        etiquetaEnemigo.setLocation(posicionEnemigo.x - dx, posicionEnemigo.y - dy);
                }

                // Actualizar la última posición conocida del personaje
                ultimaPosicionPersonaje.setLocation(-xFondo + getWidth() / 2 - etiquetaPersonaje.getWidth() / 2,
                        -yFondo + getHeight() / 2 - etiquetaPersonaje.getHeight() / 2);
        }


        private void moverEnemigosHaciaUltimaPosicion() {
                int velocidadEnemigo = MOVIMIENTO_PASO; // Velocidad de movimiento de los enemigos

                for (JLabel etiquetaEnemigo : etiquetasEnemigos) {
                        Monstruo enemigo = mapaEtiquetaEnemigo.get(etiquetaEnemigo);
                        if (enemigo != null) {
                                enemigo.actualizarPosicionEtiqueta();
                        }
                        Point posicionEnemigo = etiquetaEnemigo.getLocation();
                        int dx = etiquetaPersonaje.getX() - posicionEnemigo.x;
                        int dy = etiquetaPersonaje.getY() - posicionEnemigo.y;
                        if (dx != 0) dx /= Math.abs(dx);
                        if (dy != 0) dy /= Math.abs(dy);

                        etiquetaEnemigo.setLocation(posicionEnemigo.x + dx * velocidadEnemigo, posicionEnemigo.y + dy * velocidadEnemigo);

                        // Verificar colisiones con otros enemigos y ajustar la posición
                        for (JLabel otroEnemigo : etiquetasEnemigos) {
                                if (otroEnemigo != etiquetaEnemigo && hayColision(etiquetaEnemigo, otroEnemigo)) {
                                        ajustarPosicionParaEvitarColision(etiquetaEnemigo, otroEnemigo);
                                }
                        }

                        // Verificar colisión con el personaje y ajustar la posición
                        if (hayColision(etiquetaEnemigo, etiquetaPersonaje)) {
                                ajustarPosicionParaEvitarColision(etiquetaEnemigo, etiquetaPersonaje);
                        }


                }
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}

        public static void main(String[] args) {
                JFrame frame = new JFrame();
                Ventanajuego juego = new Ventanajuego();
                frame.add(juego);
                frame.setSize(600, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }

        private boolean hayColision(JLabel etiqueta1, JLabel etiqueta2) {
                Rectangle rect1 = etiqueta1.getBounds();
                Rectangle rect2 = etiqueta2.getBounds();
                return rect1.intersects(rect2);
        }

        private void ajustarPosicionParaEvitarColision(JLabel etiquetaMovil, JLabel etiquetaEstatica) {
                Rectangle rectMovil = etiquetaMovil.getBounds();
                Rectangle rectEstatica = etiquetaEstatica.getBounds();

                if (rectMovil.intersects(rectEstatica)) {
                        // Ajustar la posición de etiquetaMovil para evitar la superposición completa
                        // Puedes ajustar esto según la lógica que prefieras
                        if (rectMovil.x < rectEstatica.x) {
                                etiquetaMovil.setLocation(rectEstatica.x - rectMovil.width, rectMovil.y);
                        } else {
                                etiquetaMovil.setLocation(rectEstatica.x + rectEstatica.width, rectMovil.y);
                        }
                }
        }

        private void eliminarEnemigosMuertos() {
                Iterator<Map.Entry<JLabel, Monstruo>> it = mapaEtiquetaEnemigo.entrySet().iterator();
                while (it.hasNext()) {
                        Map.Entry<JLabel, Monstruo> entry = it.next();
                        Monstruo enemigo = entry.getValue();
                        if (!enemigo.estaVivo()) {
                                remove(entry.getKey()); // Elimina la etiqueta del enemigo
                                it.remove(); // Elimina el enemigo del mapa
                        }
                }
                repaint(); // Actualiza la interfaz gráfica
        }


}
