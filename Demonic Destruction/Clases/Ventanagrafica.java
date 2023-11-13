package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class Ventanagrafica extends JFrame {
 
    /**
     * Constructor para inicializar la ventana grafica.
     */
    public Ventanagrafica() {
       
        super("Game Window");
        setSize(600, 400);
        
        setLayout(new FlowLayout());
 
        
        JButton startButton = new JButton("EMPEZAR JUEGO");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                //System.out.println("Game started!");
                Ventanajuego juego = new Ventanajuego();
                //SwingUtilities.invokeLater(() -> new Ventanajuego());
                //Jugador jugador = new Jugador();
                //JLabel contador = new JLabel();
                //Funcionesjuego.contadorDeTiempo(jugador);
                //Monstruo monstruo = new Monstruo(TipoMonstruo.VAMPIRO);
                //monstruo.mostrarInformacion();
                
            }
        });
 
        
        JButton closeButton = new JButton("CERRAR JUEGO");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                System.out.println("Game closed!");
                System.exit(0);
            }
        });
 
        // Añado los botones en pantalla
        add(startButton);
        add(closeButton);
 
        // Hago visible la pantalla
        
        setVisible(true);
    }
 
}