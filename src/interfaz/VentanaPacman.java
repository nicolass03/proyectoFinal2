package interfaz;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import hilos.HiloFantasma;
import hilos.HiloPacMan;
import modelo.Comida;
import modelo.Fantasma;
import modelo.Juego;

public class VentanaPacman extends JFrame{

	private DialogoPrincipal v;
	private PanelJuego pJuego;
	private Juego j;
	
	private HiloPacMan hiloP;
	
	public VentanaPacman(DialogoPrincipal v, Juego juego) {
		
		this.v = v;
		j = juego;
		j.agregarJugador("Sisaaaas");
		setIconImage(new ImageIcon("./imagenes/icon.png").getImage());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(700,700));
		
		pJuego = new PanelJuego(this);
		hiloP = new HiloPacMan(j, this);
		hiloP.start();
		add(pJuego);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if(cerrarVentana()) {
					v.setVisible(true);
					setVisible(false);
					dispose();
				}
			}
			

			@Override
			public void windowClosed(WindowEvent e) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
		
		pack();
		centrar();
		
		iniciarFantasmas();
	}
	
	public void centrar() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = this.getSize();
		int x = (int) ((screenSize.width / 2) - (d.width / 2));
		int y = (int) ((screenSize.height / 2) - (d.height / 2));		
		setLocation(x,y);
	}

	public Juego darJuego() {
		return j;
	}
	
	public void cargarJuego(File f) {
		
	}

	public void setNombreJugador(String s) {
		
	}
	
//	public ArrayList<Comida> darComidas(){
//		return j.darArrayComidas(j.getPrimerComida(), new ArrayList<Comida>());
//	}
//
	public ArrayList<Fantasma> darFantasmas() {
		return j.darArrayFantasmas(j.getPrimerFantasma(), new ArrayList<Fantasma>());
	}
	
	public void iniciarFantasmas() {
		for(Fantasma f : darFantasmas()) {
			HiloFantasma h = new HiloFantasma(j, this, f);
			h.start();
		}
	}

	public void finDelJuego() {
		j.detenerJuego();
		JOptionPane.showMessageDialog(this, "Tu puntaje fue de: "+j.getJugador().getPuntos(),"Fin del juego", JOptionPane.INFORMATION_MESSAGE);
		j.aggPuntaje(j.getJugador().getPuntos(), j.getJugador().getNombre());
		j.serializarPuntaje();
		v.setVisible(true);
		setVisible(false);
		dispose();
		
	}
	
	public boolean cerrarVentana() {
		
		boolean cerro = true;
		j.detenerJuego();
		int seleccion = JOptionPane.showConfirmDialog(this, "Desea guardar el juego", "Terminar partida", JOptionPane.YES_NO_CANCEL_OPTION);
		
		if (seleccion == JOptionPane.YES_OPTION) {
			j.guardarPartida();
			j.aggPuntaje(j.getJugador().getPuntos(), j.getJugador().getNombre());
			j.serializarPuntaje();
		} else if (seleccion == JOptionPane.NO_OPTION) {
			j.aggPuntaje(j.getJugador().getPuntos(), j.getJugador().getNombre());	
			j.serializarPuntaje();
		} else {
			cerro = false;
			j.reanudarJuego();

		}

		return cerro;
	}
}
