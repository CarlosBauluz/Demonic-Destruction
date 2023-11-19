package Clases;

import java.awt.Rectangle;

/**
 * The type Monstruo.
 */
public class Monstruo {
    private int health;
    private int damage;
    private int level;
    private boolean monstruoVivo;
    private TipoMonstruos tipo;

    private int x; // Posición X del monstruo
    private int y; // Posición Y del monstruo
    private int ancho; // Ancho del monstruo
    private int alto;

    /**
     * Instantiates a new Monstruo.
     *
     * @param tipo the tipo
     */
// Constructor
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
     }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }


    /**
     * Sets posicion.
     *
     * @param x the x
     * @param y the y
     */
// Métodos para establecer la posición y tamaño, si es necesario
    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set tamaño.
     *
     * @param ancho the ancho
     * @param alto  the alto
     */
    public void setTamaño(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    /**
     * Multiplier.
     *
     * @param nivel the nivel
     */
    public void multiplier(int nivel) {
        this.health *= nivel;
        this.damage *= nivel * 0.25;
    }

   /* // Métodos para acceder a los atributos
    public String getNombre() {
        return nombre;
    }*/

    /**
     * Gets .
     *
     * @return the
     */
    public int gethealth() {
        return health;
    }

    /**
     * Gets damage.
     *
     * @return the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets tipo.
     *
     * @return the tipo
     */
    public TipoMonstruos getTipo() {
        return tipo;
    }

    /**
     * Mostrar informacion.
     */
// Método para mostrar información del monstruo
    public void mostrarInformacion() {
    	System.out.println("Tipo: " + tipo);
        System.out.println("Vida: " + health);
        System.out.println("Daño: " + damage);
    }

    /**
     * Esta vivo boolean.
     *
     * @return the boolean
     */
// Método para verificar si el monstruo está vivo
    public boolean estaVivo() {
        return health > 0;
    }

    /**
     * Recibir danio.
     *
     * @param cantidad the cantidad
     */
// Método para recibir daño
    public void recibirDanio(int cantidad) {
        health -= cantidad;
        if (health <= 0) {
            monstruoVivo = false;
            health = 0;
        }
    }

    /**
     * Gets rectangulo.
     *
     * @return the rectangulo
     */
    public Rectangle getRectangulo() {
        return new Rectangle(getX(), getY(), ancho, alto); // Ajustar según el tamaño del enemigo
    }
}
