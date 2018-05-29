package hilos;

import java.util.Random;

import interfaz.VentanaPacman;
import modelo.Fantasma;
import modelo.Juego;

public class HiloFantasma extends Thread{

	
	private Juego jue;
	private VentanaPacman princi;
	private Fantasma f;
//	private int direccion;
	
	public HiloFantasma(Juego juego, VentanaPacman venta, Fantasma fan) {
		jue = juego;
		princi = venta;
		f = fan;
	}
	
	public void run() {
		while(f.isDetenido() == false) {
			jue.moverFantasmas(f);
			if(jue.verificarPosicionFantasma(jue.getPrimerMuro(), f)){
				Random ran = new Random();
				int direccion = ran.nextInt(3);
				f.setDireccion(direccion);
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			princi.repaint();
		}
	}

}


