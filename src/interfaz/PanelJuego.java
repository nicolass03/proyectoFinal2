package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import modelo.Comida;
import modelo.Fantasma;
import modelo.Jugador;
import modelo.Obstaculo;
import modelo.PacMan;


public class PanelJuego extends JPanel implements KeyListener{

	private VentanaPacman ven;



	public PanelJuego(VentanaPacman v) {
		ven = v;

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
		setBackground(Color.BLACK);
	}

	@Override
	protected void paintComponent(Graphics g2) {
		removeAll();
		super.paintComponent(g2);
		Graphics2D g = (Graphics2D) g2;
		ImageIcon pacman = new ImageIcon(ven.darJuego().darPacMan().getRuta());
		g.drawImage(pacman.getImage(), ven.darJuego().darPacMan().getPosX(), ven.darJuego().darPacMan().getPosY(), 60, 60, this);
		ImageIcon puntaje = new ImageIcon("./imagenes/SCORE.png");
		g.drawImage(puntaje.getImage(), 500,17, 50, 11, this);
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.setColor(Color.WHITE);
		g.drawString(String.valueOf(ven.darJuego().getJugador().darPuntos()), 520, 45);
		ImageIcon vida = new ImageIcon("./imagenes/1UP.png"); 
		g.drawImage(vida.getImage(), 100,17, 50, 11, this);

		paintObstaculos(g2);
		paintFantasmas(g2);
		paintComida(g2);
		paintVidas(g);
	}
	
	public void paintVidas(Graphics g) {
		switch(ven.darJuego().getJugador().getVidas()) {
		case 3:
			g.drawImage(new ImageIcon("./imagenes/3UP.png").getImage(), 100, 17,50,11, this);
			break;
		case 2:
			g.drawImage(new ImageIcon("./imagenes/2UP.png").getImage(), 100, 17,50,11, this);
			break;
		case 1:
			g.drawImage(new ImageIcon("./imagenes/1UP.png").getImage(), 100, 17,50,11, this);
			break;
		}
	}

	public void paintObstaculos(Graphics g) {
		Obstaculo ob = ven.darJuego().getPrimerMuro();
		while(ob != null) {
			g.setColor(new Color(255,140,0));
			g.fillRect(ob.getPosX(), ob.getPosY(), ob.getWidth(), ob.getHeight());
			ob = ob.getSig();
	
		}
	}

	public void paintFantasmas(Graphics g) {
		Fantasma f = ven.darJuego().getPrimerFantasma();
		boolean detenido = false;
		while(detenido == false) {
			g.drawImage(new ImageIcon(f.getRuta()).getImage(), f.getPosX(), f.getPosY(),40, 40, this);
			if(f == ven.darJuego().getUltimo()) {
				detenido = true;
			}
			f = f.getSig();
		}
		
	}

	public void paintComida(Graphics g) {
		Comida c = ven.darJuego().getPrimerComida();
		while(c != null) {
			if(c.isComida() == false) {
				g.drawImage(new ImageIcon(c.getRuta()).getImage(), c.getPosX(), c.getPosY(), 15, 15, this);				
			}
			c = c.getSiguiente();
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		if(k.getKeyCode() == 39) {
			
			ven.darJuego().darPacMan().setDireccion(PacMan.DERECHA);
		}
		if(k.getKeyCode() == 38) {
			ven.darJuego().darPacMan().setDireccion(PacMan.ARRIBA);

		}
		if(k.getKeyCode() == 37) {
			ven.darJuego().darPacMan().setDireccion(PacMan.IZQUIERDA);

		}
		if(k.getKeyCode() == 40) {
			ven.darJuego().darPacMan().setDireccion(PacMan.ABAJO);

		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub
		if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
			ven.darJuego().darPacMan().setDireccion(PacMan.DERECHA);
		}
		if(k.getKeyCode() == KeyEvent.VK_UP) {
			ven.darJuego().darPacMan().setDireccion(PacMan.ARRIBA);

		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT) {
			ven.darJuego().darPacMan().setDireccion(PacMan.IZQUIERDA);

		}
		if(k.getKeyCode() == KeyEvent.VK_DOWN) {
			ven.darJuego().darPacMan().setDireccion(PacMan.ABAJO);

		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub
		if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
			ven.darJuego().darPacMan().setDireccion(PacMan.DERECHA);
		}
		if(k.getKeyCode() == KeyEvent.VK_UP) {
			ven.darJuego().darPacMan().setDireccion(PacMan.ARRIBA);

		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT) {
			ven.darJuego().darPacMan().setDireccion(PacMan.IZQUIERDA);

		}
		if(k.getKeyCode() == KeyEvent.VK_DOWN) {
			ven.darJuego().darPacMan().setDireccion(PacMan.ABAJO);

		}
	}
}
