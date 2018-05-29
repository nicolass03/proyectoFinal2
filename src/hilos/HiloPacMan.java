package hilos;

import interfaz.VentanaPacman;
import modelo.Juego;
import modelo.PacMan;

public class HiloPacMan extends Thread{
	
	private Juego jue;
	private VentanaPacman princi;
	int x = 0;
	int y = 0;
	boolean tmp = false;
//	private int direccion;
	
	public HiloPacMan(Juego juego, VentanaPacman venta) {
		jue = juego;
		princi = venta;
//		direccion = juego.darPacMan().getDireccion();
	}
	
	public void run() {
		while(jue.darPacMan().isDetenido() == false) {
			jue.moverPacman();
			jue.comerComida();
			jue.comerFantasmas();
			if(jue.verificarPosicion(jue.getPrimerMuro())){
				jue.getPacMan().revertirDireccion(jue.getPacMan().getDireccion());
			}
			
			if(jue.validarFinDelJuego()){
				princi.finDelJuego();
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