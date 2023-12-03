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
import javax.swing.JLabel;
import java.util.List;

public class Ventanajuego extends JPanel implements KeyListener, Observador {

        // Propiedades de la ventana de juego
        private JFrame frame;
        private Jugador jugador;
        private Oleadas oleadas;
        private Point ultimaPosicionPersonaje;
        private final ImageIcon iconoFondo;
        private final JLabel etiquetaFondo;
        private int xFondo = 0;
        private int yFondo = 0;
        private final int MOVIMIENTO_PASO = 3;
        private final ImageIcon iconoPersonaje;
        private final JLabel etiquetaPersonaje;
        private final ArrayList<JLabel> etiquetasEnemigos;
        private Random random;
        private JLabel etiquetaTiempo, etiquetaVida;
        private Timer timer;
        private int tiempo = 0;
        private Map<JLabel, Monstruo> mapaEtiquetaEnemigo;
        private final int ANCHO_MAPA = 5000; // Asumiendo el tamaño del mapa
        private final int ALTO_MAPA = 5000;
        private int MAX_ENEMIGOS = 40;
        private JLabel etiquetaOleadas;

        private JProgressBar barraVida;

        private JProgressBar barraCooldownEscudo;
        private JProgressBar barraCooldownAtaqueMasivo;
        private ArrayList<Curacion> curaciones;

        public void setMaxEnemigos(int maxEnemigos) {
                this.MAX_ENEMIGOS = maxEnemigos;
        }
        //Funcion que se dedica a establecer el funcionamiento del juego, estableciendo la interfaz grafica y llamando a los metodos
        //para el correcto funcionamiento.
        public Ventanajuego(JFrame frame) {
                //Inicializadores
                this.frame = frame;

                setLayout(null);
                addKeyListener(this);
                setFocusable(true);
                this.oleadas = new Oleadas();
                curaciones = new ArrayList<>();

                // Inicializar la última posición del personaje
                ultimaPosicionPersonaje = new Point(300, 200);

                // Inicializar el jugador en el centro del panel
                jugador = new Jugador(this);
                iconoPersonaje = new ImageIcon("/Users/eric/Desktop/Texturas/personaje_resized.png");
                etiquetaPersonaje = new JLabel(iconoPersonaje);

                // Calcular la posición central del panel
                int posicionCentralX = frame.getWidth() / 2 - iconoPersonaje.getIconWidth() / 2;
                int posicionCentralY = frame.getHeight() / 2 - iconoPersonaje.getIconHeight() / 2;
                etiquetaPersonaje.setBounds(posicionCentralX, posicionCentralY, iconoPersonaje.getIconWidth(), iconoPersonaje.getIconHeight());
                add(etiquetaPersonaje, Integer.valueOf(2)); // Personaje en una capa superior

                jugador.setEtiquetaPersonaje(etiquetaPersonaje);
                jugador.ataqueAutomatico(oleadas.getEnemigos());

                // Inicializar el mapa
                mapaEtiquetaEnemigo = new HashMap<JLabel, Monstruo>();

                // Inicializar enemigos
                etiquetasEnemigos = new ArrayList<>();
                random = new Random();
                generarOleada();

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
                                        generarEnemigos(2);
                                        actualizarEtiquetas();
                                        verificarOleada();
                                }
                                tiempo++;
                                actualizarEtiquetas();
                                eliminarEnemigosMuertos();

                                // Chequeo de la salud del jugador fuera del bloque anterior
                                if (jugador.getHealth() <= 0) {
                                        System.out.println("Jugador ha muerto. Mostrando fin de juego.");
                                        mostrarFinJuego();
                                        timer.stop(); // Detener el juego
                                }


                                repaint();
                        }
                });
                timer.start();

                // Configuración de la etiqueta de tiempo
                etiquetaTiempo = new JLabel("Tiempo: 0", SwingConstants.CENTER);
                etiquetaTiempo.setOpaque(true); // Hacer la etiqueta opaca
                etiquetaTiempo.setBackground(Color.WHITE); // Fondo blanco
                etiquetaTiempo.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Borde negro
                int anchoEtiquetaTiempo = 200;
                int altoEtiquetaTiempo = 30;
                etiquetaTiempo.setBounds((getWidth() - anchoEtiquetaTiempo) / 2, 10, anchoEtiquetaTiempo, altoEtiquetaTiempo);

                // Configuración de la barra de vida
                barraVida = new JProgressBar(0, 500);
                barraVida.setValue(100); // Inicializar con la vida completa
                barraVida.setForeground(Color.GREEN); // Color de la barra de vida
                int anchoBarraVida = 200;
                int altoBarraVida = 20;
                barraVida.setBounds((getWidth() - anchoBarraVida) / 2, 45, anchoBarraVida, altoBarraVida);
                ;

                // Configuración de las barras de progreso de cooldown
                barraCooldownEscudo = new JProgressBar(0, 100);
                barraCooldownAtaqueMasivo = new JProgressBar(0, 100);

                // Inicializar las barras de habilidades al 100%
                barraCooldownEscudo.setValue(100);
                barraCooldownAtaqueMasivo.setValue(100);
                int anchoBarraCooldown = 150; // Ancho más estrecho
                int altoBarraCooldown = 20; // Altura más estrecha

                // Posicionar las barras de cooldown
                barraCooldownEscudo.setBounds(10, getHeight() - 40, anchoBarraCooldown, altoBarraCooldown);
                barraCooldownAtaqueMasivo.setBounds(getWidth() - anchoBarraCooldown - 10, getHeight() - 40, anchoBarraCooldown, altoBarraCooldown);


                // Añadir componentes al panel
                add(etiquetaTiempo);
                add(barraVida);
                add(barraCooldownEscudo);
                add(barraCooldownAtaqueMasivo);

                //Etiqueta oleada
                etiquetaOleadas = new JLabel();
                etiquetaOleadas.setBounds(10, 50, 100, 20);
                add(etiquetaOleadas);


                // Cargar y configurar el fondo
                iconoFondo = new ImageIcon("/Users/eric/Desktop/Texturas/Mapajuego_2_2.pn");
                etiquetaFondo = new JLabel(iconoFondo);
                etiquetaFondo.setBounds(0, 0, iconoFondo.getIconWidth(), iconoFondo.getIconHeight());
                add(etiquetaFondo, Integer.valueOf(0)); // Fondo en la capa más baja

                curaciones = new ArrayList<>();
                generarCuraciones();
        }


        public void generarOleada() {

                for (int i = 0; i < MAX_ENEMIGOS; i++) {
                        // Seleccionar un tipo de monstruo al azar
                        TipoMonstruos tipoAleatorio = TipoMonstruos.values()[random.nextInt(TipoMonstruos.values().length)];
                        Monstruo enemigo = new Monstruo(tipoAleatorio);

                        // Seleccionar el icono basado en el tipo de monstruo
                        ImageIcon iconoEnemigo;
                        do {
                                tipoAleatorio = TipoMonstruos.values()[this.random.nextInt(TipoMonstruos.values().length)];

                                switch (tipoAleatorio) {
                                        case BESTIA:
                                                iconoEnemigo = new ImageIcon("/Users/eric/Desktop/Texturas/lobo.jpg");
                                                break;
                                        case NO_MUERTO:
                                                iconoEnemigo = new ImageIcon("/Users/eric/Desktop/Texturas/verde.jpg");
                                                break;
                                        case DEMONIO:
                                                iconoEnemigo = new ImageIcon("/Users/eric/Desktop/Texturas/rojo.jpg");
                                                break;
                                        case VAMPIRO:
                                                iconoEnemigo = new ImageIcon("/Users/eric/Desktop/Texturas/PHOTO-2023-10-29-23-37-35-veed-remove-background.png");
                                                break;
                                        default:
                                                iconoEnemigo = null; // Asignar null para forzar la repetición del bucle
                                                break;
                                }
                        } while (iconoEnemigo == null);

                        // Crear la etiqueta para el enemigo
                        JLabel etiquetaEnemigo = new JLabel(iconoEnemigo);
                        enemigo.setEtiquetaMonstruo(etiquetaEnemigo);

                       // Crear un nuevo enemigo
                        JLabel etiquetaVidaEnemigo;

                        // Suscribir Ventanajuego como observador del monstruo
                        enemigo.agregarObservador(this);

                        // Crear la etiqueta para el enemigo

                        etiquetaVidaEnemigo = new JLabel(String.valueOf(enemigo.getVida()));

                        // Generar posición aleatoria para el enemigo
                        Random random = new Random();
                        int x = random.nextInt(ANCHO_MAPA);
                        int y = random.nextInt(ALTO_MAPA);
                        etiquetaEnemigo.setBounds(x, y, iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());
                        etiquetaVidaEnemigo.setBounds(x, y - 20, 50, 20); // Ajusta según sea necesario

                        // Asignar la posición y tamaño al enemigo
                        enemigo.setPosicion(x, y);

                        enemigo.setTamano(iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());

                        // Asignar la etiqueta de vida al enemigo
                        enemigo.setEtiquetaVida(etiquetaVidaEnemigo);

                        // Añadir las etiquetas al panel y al mapa
                        add(etiquetaEnemigo, Integer.valueOf(1)); // Enemigos en una capa entre el fondo y el personaje
                        add(etiquetaVidaEnemigo, Integer.valueOf(1)); // Asegúrate de que la etiqueta de vida también se añade



                        // Añadir la etiqueta del enemigo a la lista
                        etiquetasEnemigos.add(etiquetaEnemigo);

                        // Añadir enemigo a la lista de oleadas
                        oleadas.agregarEnemigo(enemigo);

                        // Añadir enemigo al mapa de etiquetas
                        mapaEtiquetaEnemigo.put(etiquetaEnemigo, enemigo);


                }
        }


        private void configuraBarraProgreso(JProgressBar barra) {
                barra.setStringPainted(true); // Para mostrar el valor numérico del progreso
                barra.setForeground(Color.GREEN); // Color inicial de la barra
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Ajustar posiciones y tamaños de las barras de progreso según el tamaño actual del panel
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                // Ajustar la posición de la etiqueta de tiempo y la barra de vida
                int anchoEtiquetaTiempo = 200;
                int altoEtiquetaTiempo = 30;
                etiquetaTiempo.setBounds((panelWidth - anchoEtiquetaTiempo) / 2, 10, anchoEtiquetaTiempo, altoEtiquetaTiempo);

                int anchoBarraVida = 200;
                int altoBarraVida = 20;
                barraVida.setBounds((panelWidth - anchoBarraVida) / 2, 45, anchoBarraVida, altoBarraVida);

                // Ajustar la posición de las barras de cooldown
                int anchoBarraCooldown = 150; // Ancho más estrecho
                int altoBarraCooldown = 20; // Altura más estrecha
                barraCooldownEscudo.setBounds(10, panelHeight - 40, anchoBarraCooldown, altoBarraCooldown);
                barraCooldownAtaqueMasivo.setBounds(panelWidth - anchoBarraCooldown - 10, panelHeight - 40, anchoBarraCooldown, altoBarraCooldown);
        }



        public void actualizarEtiquetas() {
                // Actualizar la barra de vida
                barraVida.setValue(jugador.getHealth());

                // Actualizar etiqueta de tiempo
                etiquetaTiempo.setText("Tiempo: " + convertirTiempoAMinutosSegundos(tiempo));

                // Actualizar las barras de progreso de cooldown
                actualizarCooldowns(jugador.getValorCooldownEscudo(), jugador.getValorCooldownAtaqueMasivo());
        }


        // Método para actualizar el valor de las barras de progreso
        public void actualizarCooldowns(int valorCooldownEscudo, int valorCooldownAtaqueMasivo) {
                barraCooldownEscudo.setValue(valorCooldownEscudo);
                barraCooldownAtaqueMasivo.setValue(valorCooldownAtaqueMasivo);

                // Cambiar el color de la barra de cooldown del escudo
                if (valorCooldownEscudo == 100) {
                        barraCooldownEscudo.setForeground(Color.YELLOW); // Amarillo cuando esté disponible
                } else {
                        barraCooldownEscudo.setForeground(Color.GREEN); // Verde mientras se recarga
                }

                // Cambiar el color de la barra de cooldown del ataque masivo
                if (valorCooldownAtaqueMasivo == 100) {
                        barraCooldownAtaqueMasivo.setForeground(Color.RED); // Rojo cuando esté disponible
                } else {
                        barraCooldownAtaqueMasivo.setForeground(Color.GREEN); // Verde mientras se recarga
                }
        }



        private void verificarOleada() {
                if (oleadas.getEnemigos().isEmpty()) {
                        oleadas.nuevaOleada(this);
                        actualizarEtiquetas();
                }
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

                //habilidades
                if (e.getKeyCode() == KeyEvent.VK_E) {
                        jugador.atacarATodos(oleadas.getEnemigos());
                }

                if (e.getKeyCode() == KeyEvent.VK_Q) {
                        jugador.activarEscudo();
                }
        }

        private void actualizarPosicionPersonajeYFondo(int dx, int dy) {
                // Calcular la nueva posición propuesta para el fondo
                int nuevoXFondo = xFondo - dx;
                int nuevoYFondo = yFondo - dy;

                // Actualizar la posición del fondo
                etiquetaFondo.setLocation(nuevoXFondo, nuevoYFondo);

                // Actualizar la posición del jugador para mantenerlo en el centro
                int nuevaPosXPersonaje = getWidth() / 2 - etiquetaPersonaje.getWidth() / 2;
                int nuevaPosYPersonaje = getHeight() / 2 - etiquetaPersonaje.getHeight() / 2;

                jugador.setPosicion(nuevaPosXPersonaje, nuevaPosYPersonaje);
                etiquetaPersonaje.setLocation(nuevaPosXPersonaje, nuevaPosYPersonaje);
                // Mover los objetos de curación en la misma dirección que el fondo
                for (Curacion curacion : curaciones) {
                        JLabel etiquetaCuracion = curacion.getEtiquetaCuracion();
                        Point posicionCuracion = etiquetaCuracion.getLocation();
                        etiquetaCuracion.setLocation(posicionCuracion.x - dx, posicionCuracion.y - dy);
                }

                // Mover los enemigos en la misma dirección que el fondo
                for (JLabel etiquetaEnemigo : etiquetasEnemigos) {
                        Point posicionEnemigo = etiquetaEnemigo.getLocation();
                        etiquetaEnemigo.setLocation(posicionEnemigo.x - dx, posicionEnemigo.y - dy);
                }

                // Actualizar la última posición conocida del personaje
                ultimaPosicionPersonaje.setLocation(nuevaPosXPersonaje, nuevaPosYPersonaje);


        }


        private void moverEnemigosHaciaUltimaPosicion() {
                int velocidadEnemigo = MOVIMIENTO_PASO; // Velocidad de movimiento de los enemigos

                for (JLabel etiquetaEnemigo : new ArrayList<>(etiquetasEnemigos)) { // Usa una copia de la lista para evitar ConcurrentModificationException
                        Monstruo enemigo = mapaEtiquetaEnemigo.get(etiquetaEnemigo);
                        if (enemigo != null && enemigo.estaVivo()) {
                                enemigo.actualizarPosicionEtiqueta();

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

                                if (estaCerca(etiquetaEnemigo, etiquetaPersonaje)) {
                                        jugador.recibirDanio(10); // Aplica 10 de daño al jugador
                                        jugador.iniciarTemporizadorInvulnerabilidad(); // Inicia el temporizador de invulnerabilidad
                                }





                                // Actualizar las coordenadas del objeto Monstruo
                                Point nuevaPosicion = etiquetaEnemigo.getLocation();
                                enemigo.setPosicion(nuevaPosicion.x, nuevaPosicion.y);

                                // Verificar colisión con objetos de curación
                                verificarColisionConCuraciones();

                        }
                }
        }


        private boolean estaCerca(JLabel etiqueta1, JLabel etiqueta2) {
                Rectangle rect1 = etiqueta1.getBounds();
                Rectangle rect2 = etiqueta2.getBounds();

                // Expandir el rectángulo del jugador para verificar la proximidad
                Rectangle areaProximidad = rect2.getBounds();
                areaProximidad.grow(2, 2); // Aumenta el tamaño del rectángulo en 2 píxeles en todas las direcciones

                return rect1.intersects(areaProximidad);
        }

        // Método para obtener la lista actual de enemigos
        public List<Monstruo> obtenerEnemigosActuales() {
                return oleadas.getEnemigos();
        }

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}

        public static void main(String[] args) {
                JFrame frame = new JFrame();
                Ventanajuego juego = new Ventanajuego(frame); // Pasa 'frame' al constructor
                frame.add(juego);
                // Configuración adicional de 'frame'...
                frame.setVisible(true);
                frame.setSize(1000, 800);
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
                                JLabel etiquetaEnemigo = entry.getKey();
                                remove(etiquetaEnemigo); // Elimina la etiqueta del enemigo del panel
                                etiquetasEnemigos.remove(etiquetaEnemigo); // Elimina la etiqueta del enemigo de la lista
                                it.remove(); // Elimina el enemigo del mapa
                        }
                }
                repaint(); // Actualiza la interfaz gráfica
        }


        private void generarEnemigos(int numeroEnemigos) {
                ImageIcon iconoEnemigo = new ImageIcon("/Users/eric/Desktop/PHOTO-2023-10-29-23-37-35_resized.jpg");

                for (int i = 0; i < numeroEnemigos; i++) {
                        // Crear un nuevo enemigo
                        Monstruo enemigo = new Monstruo(TipoMonstruos.BESTIA); // Asegúrate de que esto crea un objeto Monstruo
                        JLabel etiquetaEnemigo = new JLabel(iconoEnemigo);// Aquí puedes variar el tipo de monstruo
                        JLabel etiquetaVidaEnemigo;

                        // Suscribir Ventanajuego como observador del monstruo
                        enemigo.agregarObservador(this);

                        // Crear la etiqueta para el enemigo

                        etiquetaVidaEnemigo = new JLabel(String.valueOf(enemigo.getVida()));
                        enemigo.setEtiquetaMonstruo(etiquetaEnemigo);

                        // Generar posición aleatoria para el enemigo
                        Random random = new Random();
                        int x = random.nextInt(ANCHO_MAPA);
                        int y = random.nextInt(ALTO_MAPA);
                        etiquetaEnemigo.setBounds(x, y, iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());
                        etiquetaVidaEnemigo.setBounds(x, y - 20, 50, 20); // Ajusta según sea necesario

                        // Asignar la posición y tamaño al enemigo
                        enemigo.setPosicion(x, y);
                        enemigo.setTamano(iconoEnemigo.getIconWidth(), iconoEnemigo.getIconHeight());

                        // Asignar la etiqueta de vida al enemigo
                        enemigo.setEtiquetaVida(etiquetaVidaEnemigo);

                        // Añadir las etiquetas al panel y al mapa
                        add(etiquetaEnemigo, Integer.valueOf(1)); // Enemigos en una capa entre el fondo y el personaje
                        add(etiquetaVidaEnemigo, Integer.valueOf(1)); // Asegúrate de que la etiqueta de vida también se añade



                        // Añadir la etiqueta del enemigo a la lista
                        etiquetasEnemigos.add(etiquetaEnemigo);

                        // Añadir enemigo a la lista de oleadas
                        oleadas.agregarEnemigo(enemigo);

                        // Añadir enemigo al mapa de etiquetas
                        mapaEtiquetaEnemigo.put(etiquetaEnemigo, enemigo);

                }

                revalidate();
                repaint();
        }

        @Override

        public void actualizar(Monstruo monstruo) {
                if (!monstruo.estaVivo()) {
                        JLabel etiquetaMonstruo = monstruo.getEtiquetaMonstruo();
                        JLabel etiquetaVida = monstruo.getEtiquetaVida();

                        if (etiquetaMonstruo != null) {
                                remove(etiquetaMonstruo); // Eliminar la etiqueta del monstruo
                                mapaEtiquetaEnemigo.remove(etiquetaMonstruo); // Eliminar del mapa
                        }

                        if (etiquetaVida != null) {
                                remove(etiquetaVida); // Eliminar la etiqueta de vida
                        }

                        etiquetasEnemigos.remove(etiquetaMonstruo); // Eliminar de la lista de enemigos
                        oleadas.eliminarEnemigo(monstruo); // Eliminar de la lista de oleadas

                        repaint(); // Repintar para actualizar la interfaz gráfica
                }
        }


        // Método para mostrar la ventana de fin de juego
        private void mostrarFinJuego() {
                FinJuegoVentana finJuegoVentana = new FinJuegoVentana(frame, tiempo, oleadas.getNumeroEnemigosEnfrentados());
                finJuegoVentana.setVisible(true);
        }

        private String convertirTiempoAMinutosSegundos(int tiempoTicks) {
                int totalSegundos = tiempoTicks / 10; // Convertir ticks a segundos
                int minutos = totalSegundos / 60;
                int segundos = totalSegundos % 60;
                return String.format("%02d:%02d", minutos, segundos);
        }

        private void generarCuraciones() {
                Random random = new Random();
                for (int i = 0; i < 5; i++) { // Genera 5 objetos de curación
                        int x = random.nextInt(ANCHO_MAPA);
                        int y = random.nextInt(ALTO_MAPA);
                        Curacion curacion = new Curacion(x, y);
                        curaciones.add(curacion);
                        add(curacion.getEtiquetaCuracion(), Integer.valueOf(1));
                }
        }

        private void verificarColisionConCuraciones() {
                Rectangle rectJugador = jugador.getEtiquetaPersonaje().getBounds();
                Iterator<Curacion> iterador = curaciones.iterator();

                while (iterador.hasNext()) {
                        Curacion curacion = iterador.next();
                        Rectangle rectCuracion = curacion.getEtiquetaCuracion().getBounds();

                        if (rectJugador.intersects(rectCuracion)) {
                                jugador.recogerCuracion(curacion);
                                iterador.remove(); // Eliminar la curación del mapa
                        }
                }
        }

}