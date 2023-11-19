package Clases;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The type Jugador.
 */
public class Jugador {
    private String nombre;
    private boolean jugadorVivo;
    private int health;
    private int damage;

    private int x; // Posición X del jugador
    private int y;

    /**
     * Instantiates a new Jugador.
     */
    public Jugador() {
        this.jugadorVivo = true;
        this.health = 500; 
        this.damage = 50; 
    }

    /**
     * Is jugador vivo boolean.
     *
     * @return the boolean
     */
    public boolean isJugadorVivo() {
        return jugadorVivo;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Is jugador vivo boolean.
     *
     * @param jugador the jugador
     * @return the boolean
     */
    public boolean isJugadorVivo(Jugador jugador) {
        if (jugador.gethealth() <= 0) {
        	return false;
        }else {
        	return true;
        }
    }

    /**
     * Sets jugador muerto.
     */
    public void setJugadorMuerto() {
        this.jugadorVivo = false;
        this.health = 0;
    }

    /**
     * Gets .
     *
     * @return the
     */
    public int gethealth() {
        return health;
    }

    /**
     * Sets .
     *
     * @param vida the vida
     */
    public void sethealth(int vida) {
        this.health = vida;
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
     * Sets damage.
     *
     * @param danio the danio
     */
    public void setDamage(int danio) {
        this.damage = danio;
    }

    /**
     * Atacar.
     *
     * @param enemigos the enemigos
     */
    public void atacar(ArrayList<Monstruo> enemigos) {
        Rectangle areaAtaque = new Rectangle(getX() - 50, getY() - 50, 100, 100);
        for (Monstruo enemigo : enemigos) {
            if (areaAtaque.intersects(enemigo.getRectangulo())) {
                enemigo.recibirDanio(getDamage());
            }
        }
    }
    private int getX() {
        return x; // Devuelve la posición X actual del jugador
    }

    private int getY() {
        return y; // Devuelve la posición Y actual del jugador
    }


}

