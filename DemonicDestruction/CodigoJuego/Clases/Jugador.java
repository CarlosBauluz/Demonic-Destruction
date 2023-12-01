package Clases;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Point;
import java.util.Iterator;
//para invertir la imagen al atacar:
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Graphics;




public class Jugador {
    private Ventanajuego contenedor;
    private String nombre;
    private boolean jugadorVivo;
    private int health;
    private int damage;

    private int rangoDeAtaque=20;

    private int x; // Posición X del jugador
    private int y;

    private String direccionAtaque = "DERECHA";

    private ImageIcon iconoNormal; // Imagen normal del personaje
    private ImageIcon iconoAtaque; // Imagen durante el ataque
    private JLabel etiquetaPersonaje;

    public Jugador(Ventanajuego contenedor) {
        this.contenedor = contenedor;
        this.jugadorVivo = true;
        this.health = 500; 
        this.damage = 50;
        iconoNormal = new ImageIcon("/Users/eric/Desktop/Texturas/personaje_resized.png");
        iconoAtaque = new ImageIcon("/Users/eric/Desktop/Texturas/personaje_resized.png");
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

    // Modificar el método atacar para que solo ataque horizontalmente

    public void atacar(ArrayList<Monstruo> enemigos) {
        // Configuraciones del área de ataque
        int anchoAtaque = 100;
        int altoAtaque = 50;
        Rectangle areaAtaque;

        // Determinar la posición del área de ataque basado en la dirección
        if (direccionAtaque.equals("DERECHA")) {
            areaAtaque = new Rectangle(getX() + rangoDeAtaque, getY() - altoAtaque / 2, anchoAtaque, altoAtaque);
        } else { // "IZQUIERDA"
            areaAtaque = new Rectangle(getX() - anchoAtaque - rangoDeAtaque, getY() - altoAtaque / 2, anchoAtaque, altoAtaque);
        }

        // Crear y mostrar el sprite de ataque
        ImageIcon iconoSpriteAtaque = new ImageIcon("/Users/eric/Desktop/Texturas/pixil-frame-0-2.png");
        JLabel spriteAtaque = new JLabel(iconoSpriteAtaque);

         if (spriteAtaque.getIcon() == null) {
            System.out.println("Error: No se pudo cargar la imagen del sprite de ataque.");
            return;
        }

        // Invertir la imagen del sprite de ataque si la dirección es DERECHA
        if (direccionAtaque.equals("DERECHA")) {
            spriteAtaque.setIcon(invertirImagen(iconoSpriteAtaque));
        }



        ImageIcon iconoPersonaje = (ImageIcon) etiquetaPersonaje.getIcon();
        int anchoIconoPersonaje = iconoPersonaje.getIconWidth();

        int offsetX, offsetY;

        // Obtener la posición actual de la etiqueta del personaje
        Point posicionPersonaje = etiquetaPersonaje.getLocation();

        // Ajustar la posición del sprite de ataque basándose en la posición de la etiqueta del personaje
        if (direccionAtaque.equals("DERECHA")) {
            offsetX = posicionPersonaje.x + etiquetaPersonaje.getWidth(); // A la derecha del personaje
        } else { // "IZQUIERDA"
            offsetX = posicionPersonaje.x - spriteAtaque.getPreferredSize().width; // A la izquierda del personaje
        }

        offsetY = posicionPersonaje.y + (etiquetaPersonaje.getHeight() - spriteAtaque.getPreferredSize().height) / 2; // Centrado verticalmente con el personaje

        spriteAtaque.setBounds(offsetX, offsetY, spriteAtaque.getPreferredSize().width, spriteAtaque.getPreferredSize().height);
        contenedor.add(spriteAtaque,Integer.valueOf(1));
        contenedor.repaint();

        // Atacar a los enemigos en el área
        for (Iterator<Monstruo> it = enemigos.iterator(); it.hasNext();) {
            Monstruo enemigo = it.next();
            if (areaAtaque.intersects(enemigo.getRectangulo())) {
                enemigo.recibirDanio(getDamage());
                if (!enemigo.estaVivo()) {
                    contenedor.remove(enemigo.getEtiqueta()); // Elimina la representación gráfica del monstruo
                    it.remove(); // Elimina el monstruo de la lista
                }
            }
        }


        // Cambiar a imagen de ataque del personaje
        if (etiquetaPersonaje != null) {

                etiquetaPersonaje.setIcon(iconoAtaque);

        }

        // Eliminar o esconder el sprite de ataque y cambiar de vuelta a la imagen normal
        new Thread(() -> {
            try {
                Thread.sleep(500); // Duración de la animación de ataque
                contenedor.remove(spriteAtaque);
                contenedor.repaint();
                if (etiquetaPersonaje != null) {
                    etiquetaPersonaje.setIcon(iconoNormal);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    // Método para cambiar la dirección del ataque
    // Método para cambiar la dirección del ataque
    public void cambiarDireccionAtaque(String nuevaDireccion) {
        this.direccionAtaque = nuevaDireccion;
    }


    // Método para atacar automáticamente cada 2 segundos
    public void ataqueAutomatico(ArrayList<Monstruo> enemigos) {
        new Thread(() -> {
            while (jugadorVivo) {
                try {
                    Thread.sleep(2000); // Espera 2 segundos
                    atacar(enemigos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    // Método para recibir daño
    public void recibirDanio(int cantidad) {
        health -= cantidad;
        if (health <= 0) {
            jugadorVivo = false;
            health = 0;
        }
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

}

