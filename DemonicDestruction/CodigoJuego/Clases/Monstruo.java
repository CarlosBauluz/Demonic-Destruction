package Clases;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


import java.util.ArrayList;
import java.util.List;


public class Monstruo implements Sujeto {
    private int health;
    private int damage;
    private int level;
    private int alto;
    private int ancho;
    private boolean monstruoVivo;
    private TipoMonstruos tipo;
    private JLabel etiquetaVida;
    private int vida;
    private int x; // Posición X del monstruo
    private int y; // Posición Y del monstruo
    private JLabel etiqueta;

    private JLabel etiquetaMonstruo;

    private List<Observador> observadores = new ArrayList<>();



// Constructor
    public int getVida() {
        return health;
    }
    public Monstruo(TipoMonstruos tipo) {
        switch(tipo) {
            case BESTIA:
                this.health = 200;
                this.damage = 50;
                this.tipo = TipoMonstruos.BESTIA;
                inicializarEtiqueta("/Users/eric/Desktop/Texturas/lobo.jpg");
                break;
            case NO_MUERTO:
                this.health = 250;
                this.damage = 40;
                this.tipo = TipoMonstruos.NO_MUERTO;
                inicializarEtiqueta("/Users/eric/Desktop/Texturas/verde.jpg");
                break;
            case DEMONIO:
                this.health = 300;
                this.damage = 60;
                this.tipo = TipoMonstruos.DEMONIO;
                inicializarEtiqueta("/Users/eric/Desktop/Texturas/rojo.jpg");
                break;
            case VAMPIRO:
                this.health = 180;
                this.damage = 70;
                this.tipo = TipoMonstruos.VAMPIRO;
                inicializarEtiqueta("/Users/eric/Desktop/Texturas/PHOTO-2023-10-29-23-37-35-veed-remove-background.png");
                break;
            default:
                // Manejar un caso por defecto si es necesario
                break;
        }
        // Inicializar y configurar la etiqueta de vida
        etiquetaVida = new JLabel(String.valueOf(health));
        etiquetaVida.setBounds(x, y + 20, 20, 20); // Ajusta según sea necesario
    }





    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Métodos para establecer la posición y tamaño, si es necesario
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void setTamano(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public void multiplier(int nivel) {
        this.health *= nivel;
        this.damage *= nivel * 0.25;
    }

    public int gethealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public TipoMonstruos getTipo() {
        return tipo;
    }

    // Método para mostrar información del monstruo
    public void mostrarInformacion() {
    	System.out.println("Tipo: " + tipo);
        System.out.println("Vida: " + health);
        System.out.println("Daño: " + damage);
    }

// Método para verificar si el monstruo está vivo
    public boolean estaVivo() {
        return health > 0;
    }

    @Override
    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    @Override
    public void eliminarObservador(Observador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores() {
        for (Observador observador : observadores) {
            observador.actualizar(this);
        }
    }

    // Modifica el método recibirDaño para notificar a los observadores
    public void recibirDanio(int cantidad) {
        health -= cantidad;
        if (health <= 0) {
            monstruoVivo = false;
            health = 0;
            morir();
        }
        etiquetaVida.setText(String.valueOf(health)); // Actualizar la etiqueta de vida
        notificarObservadores(); // Notificar a los observadores del cambio
        System.out.println("Monstruo recibió daño, salud restante: " + health);
    }

    private void morir() {
        monstruoVivo = false;
        notificarObservadores(); // Notificar que el monstruo ha muerto
        System.out.println("Monstruo ha muerto");
    }




    // Método para establecer la etiqueta de vida
    public void setEtiquetaVida(JLabel etiquetaVida) {
        this.etiquetaVida = etiquetaVida;
    }
    public JLabel getEtiquetaVida() {
        return etiquetaVida;
    }

    public Rectangle getRectangulo() {
        return new Rectangle(getX(), getY(), ancho, alto); // Ajustar según el tamaño del enemigo
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }


    public void inicializarEtiqueta(String rutaImagen) {
        ImageIcon icono = new ImageIcon(rutaImagen);
        this.etiqueta = new JLabel(icono);
        this.etiqueta.setBounds(x, y, icono.getIconWidth(), icono.getIconHeight());
    }

    public void setEtiquetaMonstruo(JLabel etiqueta) {
        this.etiquetaMonstruo = etiqueta;
    }


    // Método para obtener la etiqueta
    public JLabel getEtiqueta() {
        return etiqueta;
    }

    // Método para actualizar la posición de la etiqueta
    public void actualizarPosicionEtiqueta() {
        if (etiqueta != null) {
            etiqueta.setLocation(x, y);
        }
        if (etiquetaVida != null) {
            etiquetaVida.setLocation(x, y + alto);
        }

        // Actualizar las coordenadas del monstruo
        this.x = etiqueta.getX();
        this.y = etiqueta.getY();
    }






    // En la clase Monstruo
    public void actualizarPosicion(int nuevaX, int nuevaY) {
        this.x = nuevaX;
        this.y = nuevaY;

        // Actualizar la hitbox si es necesario
        actualizarHitbox();
    }



    private void actualizarHitbox() {
        if (etiquetaMonstruo != null) {
            Rectangle hitbox = new Rectangle(x, y, etiquetaMonstruo.getWidth(), etiquetaMonstruo.getHeight());
            // Aquí puedes hacer lo que necesites con la hitbox, como comprobar colisiones, etc.
        }
    }

    public JLabel getEtiquetaMonstruo() {
        return this.etiquetaMonstruo;
    }

}
