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
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        
        ImageIcon icono = new ImageIcon("mapajuego (1).png");
        JLabel etiqueta = new JLabel(icono);
        etiqueta.setBounds(0, 0, icono.getIconWidth(), icono.getIconHeight());

        Movimientopj panelMovimiento = new Movimientopj();
        panelMovimiento.setBounds(0, 0, 100, 400); // Ajusta ancho_pj y alto_pj según tus necesidades

        layeredPane.add(etiqueta, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panelMovimiento.pj, JLayeredPane.PALETTE_LAYER);

        layeredPane.setPreferredSize(new Dimension(icono.getIconWidth(), icono.getIconHeight()));
        getContentPane().add(layeredPane, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        
        setVisible(true);
        
        
    }
}