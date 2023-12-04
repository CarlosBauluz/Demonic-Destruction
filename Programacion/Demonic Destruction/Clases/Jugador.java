package Clases;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Jugador {
    private final Ventanajuego contenedor; // Contenedor principal del juego
    private JLabel etiquetaEscudo; // Etiqueta para el escudo
    private String nombre; // Nombre del jugador
    private boolean jugadorVivo; // Estado de vida del jugador
    private int health; // Salud del jugador
    private int damage; // Daño que el jugador puede infligir

    private int x; // Posición X del jugador
    private int y; // Posición Y del jugador

    private String direccionAtaque = "DERECHA"; // Dirección del ataque

    private final ImageIcon iconoNormal; // Imagen normal del personaje
    private JLabel etiquetaPersonaje; // Etiqueta del personaje

    private final ImageIcon iconoAtaque; // Imagen durante el ataque

    private boolean invulnerable = false; // Estado de invulnerabilidad

    private long ultimoAtaque = 0; // Tiempo del último ataque
    private long ultimoEscudo = 0; // Tiempo del último uso del escudo
    private final int cooldownEscudo = 15000; // Tiempo de recarga del escudo
    private final int cooldownAtaqueMasivo = 10000; // Tiempo de recarga del ataque masivo

    private long tiempoInicioInvulnerabilidad; // Tiempo de inicio de la invulnerabilidad
    private final long duracionInvulnerabilidad = 2000; // Duración de la invulnerabilidad en milisegundos

    private int velocidadMovimiento = 9;

    // Constructor del jugador
    public Jugador(Ventanajuego contenedor) {

        this.contenedor = contenedor;
        this.jugadorVivo = true;
        this.health = 500;
        this.damage = 50;
        iconoNormal = new ImageIcon("Personaje normal.png");
        iconoAtaque = new ImageIcon("Personaje atacando.png");
    }

    // Setters y Getters
    public void setEtiquetaPersonaje(JLabel etiqueta) {
        this.etiquetaPersonaje = etiqueta;
        this.etiquetaPersonaje.setIcon(iconoNormal);
    }

    public JLabel getEtiquetaPersonaje() {
        return etiquetaPersonaje;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isJugadorVivo() {
        return jugadorVivo;
    }

    public void setJugadorMuerto() {
        this.jugadorVivo = false;
        this.health = 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int vida) {
        this.health = vida;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int danio) {
        this.damage = danio;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



    // Método para obtener el valor actual del cooldown del escudo
    public int getValorCooldownEscudo() {
        long tiempoActual = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoActual - ultimoEscudo;
        return Math.min((int) (tiempoTranscurrido * 100 / cooldownEscudo), 100);
    }

    // Método para obtener el valor actual del cooldown del ataque masivo
    public int getValorCooldownAtaqueMasivo() {
        long tiempoActual = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoActual - ultimoAtaque;
        return Math.min((int) (tiempoTranscurrido * 100 / cooldownAtaqueMasivo), 100);
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Métodos de comportamiento del jugador

    // Método para actualizar y atacar a los enemigos
    public void actualizarYAtacarEnemigos() {
        List<Monstruo> enemigosActualizados = contenedor.obtenerEnemigosActuales();
        atacar(enemigosActualizados);
    }

    // Método para atacar a los enemigos
    public void atacar(List<Monstruo> enemigos) {

        synchronized (enemigos) {
            System.out.println("Atacando a " + enemigos.size() + " enemigos");
            ImageIcon iconoSpriteAtaque = new ImageIcon("ataque.png");
            JLabel spriteAtaque = new JLabel(iconoSpriteAtaque,Integer.valueOf(2));

            if (spriteAtaque.getIcon() == null) {
                System.out.println("Error: No se pudo cargar la imagen del sprite de ataque.");
                return;
            }

            Point posicionPersonaje = etiquetaPersonaje.getLocation();
            int offsetX, offsetY;
            if (direccionAtaque.equals("DERECHA")) {
                offsetX = posicionPersonaje.x + etiquetaPersonaje.getWidth();
                iconoSpriteAtaque = invertirImagen(iconoSpriteAtaque); // Invertir imagen si ataca a la derecha
            } else { // "IZQUIERDA"
                offsetX = posicionPersonaje.x - iconoSpriteAtaque.getIconWidth();
            }
            offsetY = posicionPersonaje.y + (etiquetaPersonaje.getHeight() - iconoSpriteAtaque.getIconHeight()) / 2;

            spriteAtaque.setIcon(iconoSpriteAtaque); // Establecer el icono invertido si es necesario
            spriteAtaque.setBounds(offsetX, offsetY, iconoSpriteAtaque.getIconWidth(), iconoSpriteAtaque.getIconHeight());
            contenedor.add(spriteAtaque, Integer.valueOf(1));

            contenedor.repaint();

            int areaDanioX1 = offsetX;
            int areaDanioY1 = offsetY;
            int areaDanioX2 = offsetX + iconoSpriteAtaque.getIconWidth();
            int areaDanioY2 = offsetY + iconoSpriteAtaque.getIconHeight();

            for (Monstruo enemigo : enemigos) {
                int enemigoX1 = enemigo.getX();
                int enemigoY1 = enemigo.getY();
                int enemigoX2 = enemigoX1 + enemigo.getAncho();
                int enemigoY2 = enemigoY1 + enemigo.getAlto();

                if (enemigoX1 < (offsetX + iconoSpriteAtaque.getIconWidth()) && enemigoX2 > offsetX &&
                        enemigoY1 < (offsetY + iconoSpriteAtaque.getIconHeight()) && enemigoY2 > offsetY) {
                    enemigo.recibirDanio(this.damage);
                    System.out.println("Enemigo golpeado por el sprite de ataque!");
                }
            }

            // Restablecer el icono del personaje al icono normal después del ataque
            new Timer(1000, e -> {
                etiquetaPersonaje.setIcon(iconoNormal);
                contenedor.remove(spriteAtaque);
                contenedor.repaint();
            }).start();
        }
    }

    // Método para cambiar la dirección del ataque
    public void cambiarDireccionAtaque(String nuevaDireccion) {
        this.direccionAtaque = nuevaDireccion;
    }

    // Método para ataque automático
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

    // Método para detectar colisiones
    private boolean hayColision(JLabel etiqueta1, JLabel etiqueta2) {
        Rectangle rect1 = etiqueta1.getBounds();
        Rectangle rect2 = etiqueta2.getBounds();
        boolean colision = rect1.intersects(rect2);
        if (colision) {
            System.out.println("Colisión detectada");
        }
        return colision;
    }

    // Método para recibir daño
        public void recibirDanio(int cantidad) {
            long tiempoActual = System.currentTimeMillis();
            if (!invulnerable) {
                health -= cantidad;
                System.out.println("Daño recibido: " + cantidad);
                if (health <= 0) {
                    jugadorVivo = false;
                    health = 0;
                    System.out.println("El jugador ha muerto.");
                } else {
                    // Activar invulnerabilidad
                    invulnerable = true;
                    tiempoInicioInvulnerabilidad = tiempoActual;

                    // Temporizador para desactivar la invulnerabilidad después de 2 segundos
                    new Timer(1000, e -> invulnerable = false).start();
                }
                // Actualizar la etiqueta de vida
                contenedor.actualizarEtiquetas();
            } else {
                System.out.println("Jugador invulnerable, no recibe daño");
            }
        }

    // Método para iniciar el temporizador de invulnerabilidad
    public void iniciarTemporizadorInvulnerabilidad() {
        // Implementación del temporizador de invulnerabilidad
    }

    // Método para verificar si el jugador puede recibir daño
    public boolean puedeRecibirDanio() {
        return !invulnerable;
    }

    // Método para invertir la imagen del personaje
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

    // Método para mover al jugador
    public void moverJugador(int dx, int dy) {
        this.x += dx * velocidadMovimiento;
        this.y += dy * velocidadMovimiento;
        setPosicion(this.x, this.y);
        etiquetaPersonaje.setLocation(this.x, this.y); // Asegurarse de que la etiqueta se mueva con el jugador
    }

    // Método para atacar a todos los enemigos
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

    // Método para verificar si un enemigo está en rango
    private boolean estaEnRango(Monstruo enemigo) {
        int distanciaX = Math.abs(this.x - enemigo.getX());
        int distanciaY = Math.abs(this.y - enemigo.getY());
        double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);

        System.out.println("Distancia al enemigo: " + distancia + " píxeles.");
        System.out.println("Verificando rango - Posición del jugador - X: " + this.x + ", Y: " + this.y);


        return distancia <= 100;
    }

    // Método para mostrar el área de ataque
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
        contenedor.add(areaAtaque, Integer.valueOf(2));
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

            // Temporizador para desactivar la invulnerabilidad después de un período (por ejemplo, 5 segundos)
            new Timer(5000, e -> {
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
        int radio = 30; // Radio del círculo del escudo
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

    // Método para recoger curación
    public void recogerCuracion(Curacion curacion) {
        health = Math.min(health + curacion.getValorCuracion(), 500); // No exceder 500 de vida
        // Eliminar la etiqueta de curación del contenedor
        contenedor.remove(curacion.getEtiquetaCuracion());
        contenedor.repaint();
    }

    // Método para aumentar el daño
    public void aumentarDanio(int cantidad) {
        this.damage += cantidad;
    }

    // Método para disminuir el daño
    public void disminuirDanio(int cantidad) {
        this.damage = Math.max(this.damage - cantidad, 0); // Asegura que el daño no sea negativo
    }

    public int getVelocidadMovimiento() {
        return velocidadMovimiento;
    }

    // Métodos para aumentar y disminuir la velocidad
    public void aumentarVelocidad(int cantidad) {
        this.velocidadMovimiento += cantidad;
    }

    public void disminuirVelocidad(int cantidad) {
        this.velocidadMovimiento = Math.max(this.velocidadMovimiento - cantidad, 9); // Asegura que la velocidad no sea menor que 3
    }

}
