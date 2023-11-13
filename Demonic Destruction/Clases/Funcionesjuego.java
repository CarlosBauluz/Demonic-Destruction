package Clases;

public class Funcionesjuego {
    public static void contadorDeTiempo(Jugador jugador) {
        int tiempo = 0;

        while (jugador.isJugadorVivo(jugador) == true) {
            
            tiempo++;
            System.out.println("Tiempo: " + tiempo + " segundos");

            
            if (tiempo >= 10) {
                jugador.setJugadorMuerto();
                System.out.println("El jugador ha muerto.");
            }

            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
                
            }           
        }
    }    
}

