package Clases;


public class Monstruo {
    private int health;
    private int damage;
    private int level;
    private boolean monstruoVivo;
    private TipoMonstruo tipo;

    // Constructor
    public Monstruo(TipoMonstruo tipo) {
        	switch(tipo) {
        		case BESTIA: 
        			this.health = 200;
        			this.damage = 50;
        			this.tipo = TipoMonstruo.BESTIA;
        		break;
        		case NO_MUERTO:
        			this.health = 200;
        			this.damage = 50;
        			this.tipo = TipoMonstruo.NO_MUERTO;
        		break;
        		case DEMONIO:
        			this.health = 350;
        			this.damage = 30;
        			this.tipo = TipoMonstruo.DEMONIO;
        		break;
        		case VAMPIRO:
        			this.health = 150;
        			this.damage = 100;
        			this.tipo = TipoMonstruo.VAMPIRO;
        		break;
        		default:
        			
        		break;

        }
     }
     
    public void multiplier(int nivel) {
    	this.health = health*nivel;
    	this.damage = damage*(nivel*(1/4));
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

    public TipoMonstruo getTipo() {
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
        if (health < 0) {
            health = 0;
            monstruoVivo = false;
        }else monstruoVivo = true;
    }

    
}
