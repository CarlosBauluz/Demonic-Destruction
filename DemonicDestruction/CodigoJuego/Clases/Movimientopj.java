package Clases;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Movimientopj extends JPanel implements KeyListener{

	private Jugador jugador;
	private ImageIcon p = new ImageIcon("/Users/eric/Desktop/personaje.png");
	private JLabel pj = new JLabel(p);
	private final int MOVIMIENTO_PASO = 5;
	private final int LIMITE_SUPERIOR = 0;
	private final int LIMITE_INFERIOR = 395; // Ajustar según el tamaño del mapa
	private final int LIMITE_IZQUIERDO = 0;
	private final int LIMITE_DERECHO = 595; // Ajustar según el tamaño del mapa

	public Movimientopj() {
		this.jugador = jugador;
		addKeyListener(this);
		setFocusable(true);
		add(pj);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int x = pj.getX();
		int y = pj.getY();
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				if (y > LIMITE_SUPERIOR) pj.setLocation(x, y - MOVIMIENTO_PASO);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				if (y < LIMITE_INFERIOR) pj.setLocation(x, y + MOVIMIENTO_PASO);
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				if (x > LIMITE_IZQUIERDO) pj.setLocation(x - MOVIMIENTO_PASO, y);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				if (x < LIMITE_DERECHO) pj.setLocation(x + MOVIMIENTO_PASO, y);
				break;
		}
		// Actualizar la dirección del ataque del jugador
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				jugador.cambiarDireccionAtaque("IZQUIERDA");
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				jugador.cambiarDireccionAtaque("DERECHA");
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}