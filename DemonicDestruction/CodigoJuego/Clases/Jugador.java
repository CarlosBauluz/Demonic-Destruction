    package Clases;
    import java.awt.Rectangle;
    import java.util.ArrayList;
    import java.util.List;


    import javax.swing.ImageIcon;
    import javax.swing.JLabel;

    import java.awt.Point;

    //para invertir la imagen al atacar:
    import java.awt.geom.AffineTransform;
    import java.awt.image.AffineTransformOp;
    import java.awt.image.BufferedImage;
    import java.awt.Graphics;
    import javax.swing.Timer;
    import java.awt.Color;



    import java.util.Iterator;


    import java.util.Map;
    import java.util.Map.Entry;

    public class Jugador {
        private final Ventanajuego contenedor;

        private JLabel etiquetaEscudo;
        private String nombre;
        private boolean jugadorVivo;
        private int health;
        private int damage;

        private int x; // Posición X del jugador
        private int y;

        private String direccionAtaque = "DERECHA";

        private final ImageIcon iconoNormal; // Imagen normal del personaje
        private final ImageIcon iconoAtaque; // Imagen durante el ataque
        private JLabel etiquetaPersonaje;

        private boolean invulnerable = false;

        private long ultimoAtaque = 0;

        private long ultimoEscudo = 0;
        private final long cooldownEscudo = 10000;

        private final long cooldownAtaqueMasivo = 10000;

        public Jugador(Ventanajuego contenedor) {
            this.contenedor = contenedor;
            this.jugadorVivo = true;
            this.health = 500;
            this.damage = 50;
            iconoNormal = new ImageIcon("/Users/eric/Desktop/Texturas/personaje_resized.png");
            iconoAtaque = new ImageIcon("/Users/eric/Desktop/Texturas/Personaje atacando.png");
        }



        public void setEtiquetaPersonaje(JLabel etiqueta) {
            this.etiquetaPersonaje = etiqueta;
            this.etiquetaPersonaje.setIcon(iconoNormal);
        }

        public boolean isJugadorVivo() {
            return jugadorVivo;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }



        public boolean isJugadorVivo(Jugador jugador) {
            if (jugador.gethealth() <= 0) {
                return false;
            }else {
                return true;
            }
        }

        public void setJugadorMuerto() {
            this.jugadorVivo = false;
            this.health = 0;
        }

        public int gethealth() {
            return health;
        }

        public void sethealth(int vida) {
            this.health = vida;
        }

        public int getDamage() {
            return damage;
        }

        public void setDamage(int danio) {
            this.damage = danio;
        }

        // Método para actualizar y atacar a los enemigos
        public void actualizarYAtacarEnemigos() {
            List<Monstruo> enemigosActualizados = contenedor.obtenerEnemigosActuales();
            atacar(enemigosActualizados);
        }

        // Método para obtener la lista actualizada de enemigos


        // Modificar el método atacar para que solo ataque horizontalmente

        public void atacar(List<Monstruo> enemigos) {

            synchronized (enemigos) {
                System.out.println("Atacando a " + enemigos.size() + " enemigos");
                ImageIcon iconoSpriteAtaque = new ImageIcon("/Users/eric/Desktop/Texturas/pixil-frame-0-2.png");
                JLabel spriteAtaque = new JLabel(iconoSpriteAtaque);

                if (spriteAtaque.getIcon() == null) {
                    System.out.println("Error: No se pudo cargar la imagen del sprite de ataque.");
                    return;
                }

                Point posicionPersonaje = etiquetaPersonaje.getLocation();
                int offsetX, offsetY;
                if (direccionAtaque.equals("DERECHA")) {
                    offsetX = posicionPersonaje.x + etiquetaPersonaje.getWidth();
                } else { // "IZQUIERDA"
                    offsetX = posicionPersonaje.x - iconoSpriteAtaque.getIconWidth();
                }
                offsetY = posicionPersonaje.y + (etiquetaPersonaje.getHeight() - iconoSpriteAtaque.getIconHeight()) / 2;

                spriteAtaque.setBounds(offsetX, offsetY, iconoSpriteAtaque.getIconWidth(), iconoSpriteAtaque.getIconHeight());
                contenedor.add(spriteAtaque, Integer.valueOf(1));
                contenedor.repaint();

                int areaDanioX1 = offsetX;
                int areaDanioY1 = offsetY;
                int areaDanioX2 = offsetX + iconoSpriteAtaque.getIconWidth();
                int areaDanioY2 = offsetY + iconoSpriteAtaque.getIconHeight();

                // Depuración: Imprimir las coordenadas y dimensiones del sprite de ataque
                System.out.println("Sprite Ataque - X1: " + offsetX + ", Y1: " + offsetY + ", X2: " + (offsetX + iconoSpriteAtaque.getIconWidth()) + ", Y2: " + (offsetY + iconoSpriteAtaque.getIconHeight()));
                System.out.println("Número de enemigos: " + enemigos.size());

                for (Monstruo enemigo : enemigos) {
                    int enemigoX1 = enemigo.getX();
                    int enemigoY1 = enemigo.getY();
                    int enemigoX2 = enemigoX1 + enemigo.getAncho();
                    int enemigoY2 = enemigoY1 + enemigo.getAlto();

                    // Depuración: Imprimir las coordenadas y dimensiones de cada enemigo
                    System.out.println("Enemigo - X1: " + enemigoX1 + ", Y1: " + enemigoY1 + ", X2: " + enemigoX2 + ", Y2: " + enemigoY2);
                    System.out.println("Enemigo Ancho: " + enemigo.getAncho() + ", Alto: " + enemigo.getAlto());

                    if (enemigoX1 < (offsetX + iconoSpriteAtaque.getIconWidth()) && enemigoX2 > offsetX &&
                            enemigoY1 < (offsetY + iconoSpriteAtaque.getIconHeight()) && enemigoY2 > offsetY) {
                        enemigo.recibirDanio(this.damage);
                        System.out.println("Enemigo golpeado por el sprite de ataque!");
                    }
                }

                new Timer(1000, e -> {
                    contenedor.remove(spriteAtaque);
                    contenedor.repaint();
                }).start();
            }
        }


        // Método para cambiar la dirección del ataque
        public void cambiarDireccionAtaque(String nuevaDireccion) {
            this.direccionAtaque = nuevaDireccion;
        }


        // Método para atacar automáticamente cada 2 segundos


        public void ataqueAutomatico(List<Monstruo> enemigos) {
            new Thread(() -> {
                while (jugadorVivo) {
                    try {
                        Thread.sleep(2000); // Espera 2 segundos
                        actualizarYAtacarEnemigos(); // Llama al método atacar
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }






        private boolean hayColision(JLabel etiqueta1, JLabel etiqueta2) {
            Rectangle rect1 = etiqueta1.getBounds();
            Rectangle rect2 = etiqueta2.getBounds();
            boolean colision = rect1.intersects(rect2);
            if (colision) {
                System.out.println("Colisión detectada");
            }
            return colision;
        }


        public void recibirDanio(int cantidad) {
            if (cantidad > 0) { // Si la cantidad es positiva, el jugador recibe daño
                if (!invulnerable) {
                    health -= cantidad;
                    System.out.println("Daño recibido: " + cantidad);
                    if (health <= 0) {
                        jugadorVivo = false;
                        health = 0;
                        System.out.println("El jugador ha muerto.");
                    }
                } else {
                    System.out.println("Jugador invulnerable, no recibe daño");
                }
            } else if (cantidad < 0) { // Si la cantidad es negativa, el jugador se cura
                health = Math.min(health - cantidad, 500); // No exceder 500 de vida
                System.out.println("Jugador curado: " + (-cantidad));
            }
        }

        public void iniciarTemporizadorInvulnerabilidad() {
            invulnerable = true;
            new Timer(2000, e -> invulnerable = false).start(); // Después de 2 segundos, el jugador puede recibir daño nuevamente
        }

        public boolean puedeRecibirDanio() {
            return !invulnerable;
        }


        public int getX() {
            return x; // Devuelve la posición X actual del jugador
        }

        public int getY() {
            return y; // Devuelve la posición Y actual del jugador
        }

        private ImageIcon invertirImagen(ImageIcon icono) {
            // Comprobar si el icono es nulo o si tiene tamaño cero
            if (icono == null || icono.getIconWidth() <= 0 || icono.getIconHeight() <= 0) {
                return icono; // Devuelve el icono original si no es válido para la transformación
            }

            BufferedImage imagen = new BufferedImage(
                    icono.getIconWidth(),
                    icono.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            // Dibujar la imagen en el buffer
            Graphics g = imagen.createGraphics();
            icono.paintIcon(null, g, 0, 0);
            g.dispose();

            // Crear la transformación para invertir la imagen
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-imagen.getWidth(), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

            // Aplicar la transformación
            BufferedImage imagenInvertida = op.filter(imagen, null);

            return new ImageIcon(imagenInvertida);
        }

        public JLabel getEtiquetaPersonaje() {
            return etiquetaPersonaje;
        }
        public void moverJugador(int dx, int dy) {
            this.x += dx;
            this.y += dy;
            setPosicion(this.x, this.y);
            etiquetaPersonaje.setLocation(this.x, this.y); // Asegurarse de que la etiqueta se mueva con el jugador
        }

        public void setPosicion(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void atacarATodos(ArrayList<Monstruo> enemigos) {
            long tiempoActual = System.currentTimeMillis();

            if (tiempoActual - ultimoAtaque < cooldownAtaqueMasivo) { // Cooldown de 10 segundos
                System.out.println("Habilidad en cooldown. Espera " + ((10000 - (tiempoActual - ultimoAtaque)) / 1000) + " segundos.");
                return;
            }

            ultimoAtaque = tiempoActual; // Actualizar el tiempo del último ataque
            mostrarAreaAtaque();
            Iterator<Monstruo> iterador = enemigos.iterator();
            while (iterador.hasNext()) {
                Monstruo enemigo = iterador.next();
                if (enemigo.estaVivo() && estaEnRango(enemigo)) {
                    enemigo.recibirDanio(this.damage);
                    System.out.println("Enemigo golpeado con ataque masivo!");

                    if (!enemigo.estaVivo()) {
                        iterador.remove(); // Correcto: Usar iterador para eliminar
                    }
                }
            }
        }

        // Método para obtener el tiempo restante del cooldown del ataque masivo
        public long getTiempoRestanteCooldownAtaqueMasivo() {
            long tiempoActual = System.currentTimeMillis();
            long tiempoRestante = cooldownAtaqueMasivo - (tiempoActual - ultimoAtaque);
            return tiempoRestante > 0 ? tiempoRestante : 0;
        }


        private boolean estaEnRango(Monstruo enemigo) {
            int distanciaX = Math.abs(this.x - enemigo.getX());
            int distanciaY = Math.abs(this.y - enemigo.getY());
            double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);

            System.out.println("Distancia al enemigo: " + distancia + " píxeles.");
            System.out.println("Verificando rango - Posición del jugador - X: " + this.x + ", Y: " + this.y);


            return distancia <= 100;
        }

        private void mostrarAreaAtaque() {
            int radio = 100; // Radio del círculo de ataque
            Point posicionPersonaje = etiquetaPersonaje.getLocation();
            int xCentro = posicionPersonaje.x + etiquetaPersonaje.getWidth() / 2;
            int yCentro = posicionPersonaje.y + etiquetaPersonaje.getHeight() / 2;

            BufferedImage areaAtaqueImagen = new BufferedImage(2 * radio, 2 * radio, BufferedImage.TYPE_INT_ARGB);
            Graphics g = areaAtaqueImagen.getGraphics();
            g.setColor(new Color(105, 224, 194, 128)); // Color rojo con transparencia
            g.fillOval(0, 0, 2 * radio, 2 * radio);
            g.dispose();

            JLabel areaAtaque = new JLabel(new ImageIcon(areaAtaqueImagen));
            areaAtaque.setBounds(xCentro - radio, yCentro - radio, 2 * radio, 2 * radio);
            contenedor.add(areaAtaque, Integer.valueOf(1));
            contenedor.repaint();

            // Eliminar el círculo después de un breve período
            new Timer(1000, e -> {
                contenedor.remove(areaAtaque);
                contenedor.repaint();
            }).start();
        }


        // Método para activar el escudo
        public void activarEscudo() {
            long tiempoActual = System.currentTimeMillis();
            if (tiempoActual - ultimoEscudo >= cooldownEscudo) {
                invulnerable = true;
                mostrarEscudo();
                ultimoEscudo = tiempoActual;
                new Timer(2000, e -> {
                    invulnerable = false;
                    ocultarEscudo();
                }).start();
            } else {
                System.out.println("Escudo en cooldown. Espera " + ((cooldownEscudo - (tiempoActual - ultimoEscudo)) / 1000) + " segundos.");
            }
        }

        // Método para obtener el tiempo restante del cooldown del escudo
        public long getTiempoRestanteCooldownEscudo() {
            long tiempoActual = System.currentTimeMillis();
            long tiempoRestante = cooldownEscudo - (tiempoActual - ultimoEscudo);
            return tiempoRestante > 0 ? tiempoRestante : 0;
        }

        // Método para mostrar el escudo
        private void mostrarEscudo() {
            int radio = 50; // Radio del círculo del escudo
            Point posicionPersonaje = etiquetaPersonaje.getLocation();
            int xCentro = posicionPersonaje.x + etiquetaPersonaje.getWidth() / 2;
            int yCentro = posicionPersonaje.y + etiquetaPersonaje.getHeight() / 2;

            BufferedImage imagenEscudo = new BufferedImage(2 * radio, 2 * radio, BufferedImage.TYPE_INT_ARGB);
            Graphics g = imagenEscudo.getGraphics();
            g.setColor(new Color(65, 231, 60, 128)); // Color del escudo con transparencia
            g.fillOval(0, 0, 2 * radio, 2 * radio);
            g.dispose();

            if (etiquetaEscudo == null) {
                etiquetaEscudo = new JLabel(new ImageIcon(imagenEscudo));
                contenedor.add(etiquetaEscudo, Integer.valueOf(2));
            } else {
                etiquetaEscudo.setIcon(new ImageIcon(imagenEscudo));
            }
            etiquetaEscudo.setBounds(xCentro - radio, yCentro - radio, 2 * radio, 2 * radio);
            etiquetaEscudo.setVisible(true);
        }

        // Método para ocultar el escudo
        private void ocultarEscudo() {
            if (etiquetaEscudo != null) {
                etiquetaEscudo.setVisible(false);
            }
        }
        public void recogerCuracion(Curacion curacion) {
            health = Math.min(health + curacion.getValorCuracion(), 500); // No exceder 500 de vida
            // Eliminar la etiqueta de curación del contenedor
            contenedor.remove(curacion.getEtiquetaCuracion());
            contenedor.repaint();
        }
    }

