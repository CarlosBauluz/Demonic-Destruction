package Clases;

public class Jugador {
    private String nombre;
    private boolean jugadorVivo;
    private int health;
    private int damage;

    public Jugador() {
        this.jugadorVivo = true;
        this.health = 500; 
        this.damage = 50; 
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
    

    
}

