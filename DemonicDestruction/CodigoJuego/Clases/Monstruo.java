package Clases;

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Monstruo {
    private int health;
    private int damage;
    private int level;

    private int alto;
    private boolean monstruoVivo;
    private TipoMonstruos tipo;

    private JLabel etiquetaVida;
    private int vida;

    private int x; // Posición X del monstruo
    private int y; // Posición Y del monstruo
    private int ancho; // Ancho del monstruo

    private JLabel etiqueta;



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
        		break;
        		case NO_MUERTO:
        			this.health = 200;
        			this.damage = 50;
        			this.tipo = TipoMonstruos.NO_MUERTO;
        		break;
        		case DEMONIO:
        			this.health = 350;
        			this.damage = 30;
        			this.tipo = TipoMonstruos.DEMONIO;
        		break;
        		case VAMPIRO:
        			this.health = 150;
        			this.damage = 100;
        			this.tipo = TipoMonstruos.VAMPIRO;
        		break;
        		default:

        		break;

        }
        // Inicializar y configurar la etiqueta de vida
        etiquetaVida = new JLabel(String.valueOf(health));
        etiquetaVida.setBounds(x, y + 20, 20, 20); // Ajusta según sea necesario
        // Configura el estilo de la etiqueta aquí si es necesario
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

    public void setTamaño(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    public void multiplier(int nivel) {
        this.health *= nivel;
        this.damage *= nivel * 0.25;
    }

   /* // Métodos para acceder a los atributos
    public String getNombre() {
        return nombre;
    }*/

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

// Método para recibir daño
    public void recibirDanio(int cantidad) {
        health -= cantidad;
        if (health <= 0) {
            monstruoVivo = false;
            health = 0;
            // Aquí puedes agregar la lógica para eliminar el monstruo si la vida es 0
            // Por ejemplo, eliminar la etiqueta del monstruo de la interfaz gráfica
        }
        etiquetaVida.setText(String.valueOf(health));
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


    // Método para inicializar la etiqueta
    public void inicializarEtiqueta(String rutaImagen) {
        ImageIcon icono = new ImageIcon(rutaImagen);
        this.etiqueta = new JLabel(icono);
        this.etiqueta.setBounds(x, y, icono.getIconWidth(), icono.getIconHeight());
    }

    // Método para obtener la etiqueta
    public JLabel getEtiqueta() {
        return etiqueta;
    }

    // Método para actualizar la posición de la etiqueta
    public void actualizarPosicionEtiqueta() {
        if (etiquetaVida != null) {
            etiquetaVida.setLocation(x, y + alto); // Ajusta la posición Y según sea necesario
        }
    }



}
