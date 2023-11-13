package Clases;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Movimientopj extends JPanel implements KeyListener{
	public ImageIcon p = new ImageIcon("personajeprueba.jpg");
	public JLabel pj = new JLabel (p);
	
	
	public Movimientopj() {
		addKeyListener(this);
		setFocusable(true);
		add(pj);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getExtendedKeyCode() == KeyEvent.VK_UP) {
			pj.setLocation(pj.getX(), pj.getY() - 5);
		}
		if (e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
			pj.setLocation(pj.getX(), pj.getY() + 5);
		}
		if (e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
			pj.setLocation(pj.getX() - 5, pj.getY());
		}
		if (e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
			pj.setLocation(pj.getX()+ 5, pj.getY() - 5);
		}

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'w'||e.getKeyChar() == 'W'|| e.getExtendedKeyCode() == KeyEvent.VK_UP) {
			pj.setLocation(pj.getX(), pj.getY() - 5);
		}
		if (e.getKeyChar() == 's'||e.getKeyChar() == 'S'|| e.getExtendedKeyCode() == KeyEvent.VK_DOWN) {
			pj.setLocation(pj.getX(), pj.getY() + 5);
		}
		if (e.getKeyChar() == 'a'||e.getKeyChar() == 'A'|| e.getExtendedKeyCode() == KeyEvent.VK_LEFT) {
			pj.setLocation(pj.getX() - 5, pj.getY());
		}
		if (e.getKeyChar() == 'd'||e.getKeyChar() == 'D'|| e.getExtendedKeyCode() == KeyEvent.VK_RIGHT) {
			pj.setLocation(pj.getX()+ 5, pj.getY());
		}

	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
