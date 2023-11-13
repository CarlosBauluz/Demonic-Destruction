package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class Ventanajuego extends JFrame {

	public Ventanajuego() {
        // Establecer el título de la ventana
        
        super("Game Window");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(new BorderLayout());
        //Creo una variable de capas para poder introducir diversas imagenes como capas en la pantalla una sobre otra
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        //Creo la variable para darle imagen al fondo 
        ImageIcon icono = new ImageIcon("mapajuego (1).png");
        JLabel etiqueta = new JLabel(icono);
        etiqueta.setBounds(0, 0, icono.getIconWidth(), icono.getIconHeight());
        //Creo el personaje en pantalla ya con los movimientos integrados
        Movimientopj panelMovimiento = new Movimientopj();
        panelMovimiento.setBounds(0, 0, 100, 400); 
        //Añado las diferentes variables a diferentes capas
        layeredPane.add(etiqueta, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panelMovimiento.pj, JLayeredPane.PALETTE_LAYER);
    
        layeredPane.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
        getContentPane().add(layeredPane, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        //para que se muestre la ventana
        setVisible(true);
        
        
    }
}