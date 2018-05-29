package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modelo.Fantasma;
import modelo.Juego;
import modelo.Obstaculo;
import modelo.PacMan;

class PacManTest {
	
	private Juego pacMan;
	
	private void escenario() {
		pacMan = new Juego();
		pacMan.agregarJugador("Santiago");
	}

	//Verifica que la comida se agrege a la lista de comida.
	@Test
	public void testAgregarComida() {
		escenario();
		int x = 200;
		int y = 300;
		String ruta = "./imagenes/fruta.png";
		pacMan.agregarComida(x, y, ruta);
		// Verifica que se agrega al primero elemento de comida, para empezar la lista;
		assertNotNull(pacMan.getPrimerComida());
		int x1 = 200;
		int y1 = 300;
		String ruta1 = "./imagenes/fruta.png";
		pacMan.agregarComida(x1, y1, ruta1);
		// Verifica que se agrege la comida despues de tener la lista con un objeto
		assertNotNull(pacMan.getPrimerComida().getSiguiente());
	}
	
	// Verifica que los muros se vayan agregando a la lista de muros
	@Test
	public void testAgregarMuros() {
		escenario();
		int x = 300;
		int y = 500;
		int w = 120;
		int h = 60;
		Obstaculo obs = new Obstaculo(x,y,w,h);
		pacMan.agregarObstaculos(obs);
		// Verifica que se agrega al primero elemento de muros, para empezar la lista;
		assertNotNull(pacMan.getPrimerMuro());
		int x1 = 500;
		int y1 = 600;
		int w1 = 20;
		int h1 = 60;
		Obstaculo obs1 = new Obstaculo(x1,y1,w1,h1);
		pacMan.agregarObstaculos(obs1);
		// Verifica que se agrege un muro despues de tener la lista con un objeto
		assertNotNull(pacMan.getPrimerMuro().getSig());
	}
	
	//Verifica que el pacMan se mueva en todas las direcciones
	@Test
	public void testMoverPacMan() {
		escenario();
		PacMan pac = pacMan.getPacMan();
		pac.setPosX(1);
		pac.setPosY(1);
		pac.setDireccion(PacMan.ARRIBA);
		pac.mover();
		// Verifica que el pacMan mueva hacia arriba
		assertEquals(0, pac.getPosY());
		pac.setDireccion(PacMan.ABAJO);
		pac.mover();
		//Verifica que el pacMan mueva hacia abajo
		assertEquals(1, pac.getPosY());
		pac.setDireccion(PacMan.DERECHA);
		pac.mover();
		//Verifica que el pacMan mueva hacia la derecha
		assertEquals(2, pac.getPosX());
		pac.setDireccion(PacMan.IZQUIERDA);
		pac.mover();
		//Verifica que el pacMan mueva hacia la izquiera
		assertEquals(1, pac.getPosX());
		
	}
	
	//Verifica que todos los fantasmas se pongan en modo comestible
	@Test
	public void testFantasmasComestibles() {
		escenario();
		pacMan.agregarFantasmas();
		pacMan.setFantasmasComibles(pacMan.getPrimerFantasma());
		Fantasma actual = pacMan.getPrimerFantasma();
		while(actual != pacMan.getUltimo()) {
		assertEquals(Fantasma.MODO_AGARRABLE, actual.getModo());
		actual = actual.getSig();
		}
		assertEquals(Fantasma.MODO_AGARRABLE, actual.getModo());

	}
	
	//Verifica que todos los fantasmas se pongan en modo NO comestible
	@Test
	public void testFantasmasNoComestibles() {
		escenario();
		pacMan.agregarFantasmas();
		//Primero los vuelvos fantasmas comestibles
		pacMan.setFantasmasComibles(pacMan.getPrimerFantasma());
		//Luego a fantasmas en modo normal;
		pacMan.setFantasmasNoComibles(pacMan.getPrimerFantasma());
		Fantasma actual = pacMan.getPrimerFantasma();
		while(actual != pacMan.getUltimo()) {
		assertEquals(Fantasma.MODO_NORMAL, actual.getModo());
		actual = actual.getSig();
		}
		assertEquals(Fantasma.MODO_NORMAL, actual.getModo());
	}
	
	//Verifica que la cuando la posicion del pacMan y la comida san iguales, el pacMan coma.
	@Test
	public void testComerComida() {
		escenario();
		int x = 2;
		int y = 2;
		String ruta = "./imagenes/fruta.png";
		pacMan.agregarComida(x, y, ruta);
		pacMan.getPacMan().setPosX(-20);
		pacMan.getPacMan().setPosY(-20);
		pacMan.comerComida();
		assertEquals(10, pacMan.getJugador().darPuntos());
	}
	
	@Test
	public void testComerFantasma() {
		escenario();
		pacMan.agregarFantasmas();
		pacMan.getPacMan().setPosX(2);
		pacMan.getPacMan().setPosY(2);
		pacMan.getPrimerFantasma().setPosX(2);
		pacMan.getPrimerFantasma().setPosY(2);
		pacMan.comerFantasmas();
		// Verifica que cuando el fantasma este en modo normal las vidas se resten
		assertEquals(2, pacMan.getJugador().getVidas());
		pacMan.setFantasmasComibles(pacMan.getPrimerFantasma());
		pacMan.getPacMan().setPosX(2);
		pacMan.getPacMan().setPosY(2);
		pacMan.comerFantasmas();
		//Verifica que cuando el fantasma este en modo comstible y se come los puntos aumentan en 50
		assertEquals(50, pacMan.getJugador().getPuntos());
		
	}
	
	//Verifica que el metodo buscarFantasma busque el fantasma correcto;
	@Test
	public void testBuscarFantasma() {
		escenario();
		pacMan.agregarFantasmas();
		Fantasma fan2 = pacMan.getPrimerFantasma().getSig();
		fan2.setPosX(2);
		fan2.setPosY(2);
		//Busca que es segundo fantasma sea el encontrado
		assertEquals(fan2, pacMan.buscarFantasma(2, 2, pacMan.getPrimerFantasma()));
	}
	
	//Verifica que el metodo busque la comida correspondiente
	@Test
	public void testBuscarComida() {
		escenario();
		int x = 2;
		int y = 2;
		String ruta = "./imagenes/fruta.png";
		pacMan.agregarComida(x, y, ruta);
		assertEquals(pacMan.getPrimerComida(), pacMan.buscarComida(-20, -20, pacMan.getPrimerComida()));
	}
	
	

}
