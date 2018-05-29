package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Juego {

	public final static int N_FANTASMAS = 4;

	private PacMan pacMan;
	private Jugador jugador;
	private Fantasma primerFantasma;
	private Fantasma ultimo;
	private Obstaculo primerMuro;
	private Comida primerComida;
	private Puntaje raizPuntaje;

	public Juego() {
		pacMan = new PacMan(40, 70);
		primerComida = null;


	}

	public void inicializarJuego() {
		agregarFantasmas();
		cargarObstaculos();
		cargarComida();
	}

	public void agregarJugador(String nombre) {
		jugador = new Jugador(nombre);
	}

	public PacMan darPacMan() {
		return pacMan;
	}

	// ACOMODAR POSICIONES
	public void agregarFantasmas() {

		primerFantasma	 = new Fantasma(325, 325, Fantasma.COLOR_AMARILLO,"./imagenes/amarillo.gif");
		Fantasma segun = new Fantasma(325, 325, Fantasma.COLOR_ROJO,"./imagenes/rojo.gif");
		Fantasma terce = new Fantasma(325, 325, Fantasma.COLOR_AZUL,"./imagenes/azul.gif");
		Fantasma cuar = new Fantasma(325, 325, Fantasma.COLOR_VERDE,"./imagenes/verde.gif");
		primerFantasma.setSig(segun);
		segun.setSig(terce);
		ultimo = cuar;
		terce.setSig(ultimo);
		ultimo.setSig(primerFantasma);
	}

	public void agregarComida(int x, int y, String r) {
		Comida comi = new Comida(x, y, r);
		Comida tmp = primerComida;
		if(primerComida == null) {
			primerComida = comi;
		}
		else {
			while(tmp.getSiguiente() != null) {
				tmp = tmp.getSiguiente();
			}
			tmp.setSiguiente(comi);
		}
	}

	public void agregarComidaEspecial(int x, int y, String r) {
		Comida comi = new ComidaEspecial(x,y,r);
		Comida tmp = primerComida;
		if(primerComida == null) {
			primerComida = comi;
		}
		else {
			while(tmp.getSiguiente() != null) {
				tmp = tmp.getSiguiente();
			}
			tmp.setSiguiente(comi);
		}
	}

	public void moverPacman() {
		pacMan.mover();
	}



	public void setFantasmasComibles(Fantasma f) {
		if(f == ultimo) {
			f.setModo(Fantasma.MODO_AGARRABLE);
		}
		else {
			f.setModo(Fantasma.MODO_AGARRABLE);
			setFantasmasComibles(f.getSig());
		}
	}

	public void setFantasmasNoComibles(Fantasma f) {
		if(f == ultimo) {
			f.setModo(Fantasma.MODO_NORMAL);
		}
		else {
			f.setModo(Fantasma.MODO_NORMAL);
			setFantasmasNoComibles(f.getSig());
		}
	}

	public void setModo(boolean puedeComer) {
		if(puedeComer) {
			pacMan.setComer(true);
			setFantasmasComibles(primerFantasma);
			TimerTask task = new TimerTask() {

				int segundos = 0;
				@Override
				public void run() {
					while(segundos < 10) {
						segundos++;
						if(segundos == 10) {
							setModo(false);
						}
					}
				}

			};

			Timer t = new Timer();
			t.schedule(task, 1000);
		}
		else {
			pacMan.setComer(false);
			setFantasmasNoComibles(primerFantasma);
		}
	}

	public Comida buscarComida(int posX, int posY, Comida comi) {
		if(comi == null) {
			return null;
		}
		else {
			int rango = 30;
			double distancia;
			distancia = Math.sqrt(Math.pow(comi.getPosX() - (posX+30), 2) + Math.pow(comi.getPosY() - (posY+30), 2));
			if(distancia < rango) {
				return comi;
			}
			else {
				return buscarComida(posX, posY, comi.getSiguiente());
			}
		}
	}

	public void comerFantasmas() {
		Fantasma aComer = buscarFantasma(pacMan.getPosX(), pacMan.getPosY(), primerFantasma);
		if(aComer != null) {
			if(aComer.getModo() == Fantasma.MODO_AGARRABLE) {
				jugador.aumentarPuntaje(50);
				aComer.resetPos();				
			}
			else {
				jugador.restarVida();
				pacMan.resetPosicion();
			}
		}
	}

	public void comerComida() {
		Comida aComer = buscarComida(pacMan.getPosX(), pacMan.getPosY(), primerComida);
		if(aComer != null && aComer.isComida() == false) {
			if(aComer instanceof ComidaEspecial) {
				setModo(true);
				aComer.setComida(true);
				jugador.aumentarPuntaje(aComer.getPuntos());

			}
			else {
				jugador.aumentarPuntaje(aComer.getPuntos());
				aComer.setComida(true);
			}
		}
	}

	public Fantasma buscarFantasma(int posX, int posY, Fantasma f) {
		int rango = 20;
		if(f == ultimo) {
			double distancia = Math.sqrt(Math.pow(f.getPosX() - posX, 2) + Math.pow(f.getPosY() - posY, 2));
			if(distancia < rango) {
				return f;
			}
			else {
				return null;
			}
		}
		else {
			double distancia = Math.sqrt(Math.pow(f.getPosX() - posX, 2) + Math.pow(f.getPosY() - posY, 2));
			if(distancia < rango) {
				return f;
			}
			else {
				return buscarFantasma(posX, posY, f.getSig());
			}
		}
	}

	public void eliminarComida(Comida aComer) {
		if(aComer == primerComida){
			primerComida = aComer.getSiguiente();
		}
		else{
			Comida anterior = darComidaAnterior(primerComida, aComer);
			anterior.setSiguiente(aComer.getSiguiente());			
		}
	}

	public Comida darComidaAnterior(Comida tmp, Comida buscada){
		if(tmp == null){
			return null;
		}
		else if(tmp.getSiguiente().equals(buscada)){
			return tmp;
		}
		else{
			return darComidaAnterior(tmp.getSiguiente(), buscada);
		}
	}

	public boolean verificarPosicionFantasma(Obstaculo o, Fantasma f) {
		if(o == null) {
			return false;
		}
		else {
			switch(f.getDireccion()) {
			case Fantasma.ARRIBA:
				//				
				if(((f.getPosX()) >= o.getPosX() &&  (f.getPosX()) <= (o.getPosX()+o.getWidth())) && ((f.getPosY()) >= o.getPosY() &&  f.getPosY() <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			case Fantasma.IZQUIERDA:
				//				
				if((f.getPosX() >= o.getPosX() &&  f.getPosX() <= (o.getPosX()+o.getWidth())) && ((f.getPosY()+40) >= o.getPosY() &&  f.getPosY()+40 <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			case Fantasma.DERECHA:
				//				
				if(((f.getPosX()+40) >= o.getPosX() &&  (f.getPosX()+40) <= (o.getPosX()+o.getWidth())) && ((f.getPosY()+40) >= o.getPosY() &&  f.getPosY()+40 <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			case Fantasma.ABAJO:
				//				
				if(((f.getPosX()+40) >= o.getPosX() &&  (f.getPosX()+40) <= (o.getPosX()+o.getWidth())) && ((f.getPosY()+40) >= o.getPosY() &&  f.getPosY()+40 <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			}

			return verificarPosicionFantasma(o.getSig(), f);

		}
	}

	public boolean verificarPosicion(Obstaculo o) {
		if(o == null) {
			return false;
		}
		else {
			switch(pacMan.getDireccion()) {
			case PacMan.ARRIBA:
				//				
				if(((pacMan.getPosX()) >= o.getPosX() &&  (pacMan.getPosX()) <= (o.getPosX()+o.getWidth())) && ((pacMan.getPosY()+40) >= o.getPosY() &&  pacMan.getPosY()+40 <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			case PacMan.IZQUIERDA:
				//				
				if((pacMan.getPosX() >= o.getPosX() &&  pacMan.getPosX() <= (o.getPosX()+o.getWidth())) && ((pacMan.getPosY()+40) >= o.getPosY() &&  pacMan.getPosY()+40 <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			case PacMan.DERECHA:
				//				
				if(((pacMan.getPosX()+40) >= o.getPosX() &&  (pacMan.getPosX()+40) <= (o.getPosX()+o.getWidth())) && ((pacMan.getPosY()+40) >= o.getPosY() &&  pacMan.getPosY()+40 <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			case PacMan.ABAJO:
				//				
				if(((pacMan.getPosX()+40) >= o.getPosX() &&  (pacMan.getPosX()+40) <= (o.getPosX()+o.getWidth())) && ((pacMan.getPosY()) >= o.getPosY() &&  pacMan.getPosY() <= (o.getPosY()+o.getHeight()))) {
					return true;
				}
				break;
			}

			return verificarPosicion(o.getSig());

		}
	}


	/**
	 * @return the jugador
	 */
	public Jugador getJugador() {
		return jugador;
	}

	/**
	 * @param jugador the jugador to set
	 */
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	/**
	 * @return the primerFantasma
	 */
	public Fantasma getPrimerFantasma() {
		return primerFantasma;
	}

	/**
	 * @param primerFantasma the primerFantasma to set
	 */

	public void setPrimerFantasma(Fantasma primerFantasma) {
		this.primerFantasma = primerFantasma;
	}

	//	public void agregarPuntaje(int puntos, String nombre) {
	//		Puntaje reco = raizPuntaje;
	//		Puntaje anterior = null;
	//		Puntaje nuevo = new Puntaje(puntos, nombre);
	//		agregarPuntaje(reco, anterior, nuevo);
	//	}
	//	
	// 
	//	public void agregarPuntaje(Puntaje reco, Puntaje anterior, Puntaje nuevo) {
	//		if(raizPuntaje == null) {
	//			raizPuntaje = nuevo;
	//		}else {
	//			if(reco == null) {
	//				if(anterior.getPuntos() < )
	//			}
	//		}
	//	}

	public PacMan getPacMan() {
		return pacMan;
	}

	public void setPacMan(PacMan pacMan) {
		this.pacMan = pacMan;
	}

	public Fantasma getUltimo() {
		return ultimo;
	}

	public void setUltimo(Fantasma ultimo) {
		this.ultimo = ultimo;
	}

	/**
	 * @return the primerMuro
	 */
	public Obstaculo getPrimerMuro() {
		return primerMuro;
	}

	/**
	 * @param primerMuro the primerMuro to set
	 */
	public void setPrimerMuro(Obstaculo primerMuro) {
		this.primerMuro = primerMuro;
	}

	/**
	 * @return the primerComida
	 */
	public Comida getPrimerComida() {
		return primerComida;
	}

	/**
	 * @param primerComida the primerComida to set
	 */
	public void setPrimerComida(Comida primerComida) {
		this.primerComida = primerComida;
	}

	//	public ArrayList<Comida> darArrayComidas(Comida tmp, ArrayList<Comida> a) {
	//		if(tmp == null){
	//			return a;
	//		}
	//		else{
	//			a.add(tmp);
	//			return darArrayComidas(tmp.getSiguiente(), a);
	//		}
	//	}	

	public ArrayList<Fantasma> darArrayFantasmas(Fantasma tmp, ArrayList<Fantasma> f){
		if(tmp == ultimo){
			f.add(tmp);
			return f;
		}
		else{
			f.add(tmp);
			return darArrayFantasmas(tmp.getSig(), f);
		}
	}

	public boolean validarFinDelJuego() {
		return(jugador.getVidas() == 0)?true:false;
	}

	public void moverFantasmas(Fantasma f) {
		f.mover();
	}

	public void cargarPartida(){
		try {
			File f = new File("./data/ultimaPartida.pacman");
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Jugador j = (Jugador) ois.readObject();
			jugador = j;
			ois.close();
			fis.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void guardarPartida(){
		try {
			File f = new File("./data/ultimaPartida.pacman");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(jugador);
			oos.close();
			fos.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void agregarObstaculos(Obstaculo obs) {
		if(primerMuro == null) {
			primerMuro = obs;
		}else {
			Obstaculo actual = primerMuro;
			while(actual.getSig() != null) {
				actual = actual.getSig();
			}
			actual.setSig(obs);
		}
	}

	public void cargarObstaculos() {
		try {
			File epa = new File("./data/muros.txt");
			FileReader fl = new FileReader(epa);
			BufferedReader bf = new BufferedReader(fl);
			String linea = bf.readLine();
			while(linea != null) {
				String[] spl = linea.split("\t");

				int x = Integer.parseInt(spl[0]);
				int y = Integer.parseInt(spl[1]);
				int w = Integer.parseInt(spl[2]);
				int h = Integer.parseInt(spl[3]);
				Obstaculo obs = new Obstaculo(x,y,w,h);
				agregarObstaculos(obs);
				linea = bf.readLine();
			}
			bf.close();
			fl.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Puntaje buscarPuntaje(Puntaje tmp, String buscado){

		if (tmp == null || tmp.getNombre().equals(buscado))
			return tmp;

		if (tmp.getNombre().compareTo(buscado) > 0)
			return buscarPuntaje(tmp.getDere(), buscado);

		else
			return buscarPuntaje(tmp.getIzq(), buscado);
	}

	//	public void insertarPuntaje(int pts, String nombre) {
	//		raizPuntaje = aggPuntaje(raizPuntaje, pts, nombre);
	//	}

	/* A recursive function to insert a new key in BST */
	public void aggPuntaje(int p, String n) {

		//		/* If the tree is empty, return a new node */
		//		if (tmp == null) {
		//			tmp = new Puntaje(n,p);
		//			return tmp;
		//		}
		//
		//		/* Otherwise, recur down the tree */
		//		if (p < tmp.getPuntos())
		//			tmp.setIzq(aggPuntaje(tmp.getIzq(), p, n));
		//		else if (p > tmp.getPuntos())
		//			tmp.setDere(aggPuntaje(tmp.getDere(), p, n));
		//
		//		/* return the (unchanged) node pointer */
		//		return tmp;

		Puntaje nuevo = new Puntaje(n,p);
		if(raizPuntaje == null){
			raizPuntaje = nuevo;
		}
		else{
			Puntaje anterior = null, tmp;
			tmp = raizPuntaje;
			while(tmp != null) {
				anterior = tmp;
				if(p < tmp.getPuntos()) {
					tmp = tmp.getIzq();
				}
				else {
					tmp = tmp.getDere();
				}

			}
			if(p < anterior.getPuntos()) {
				anterior.setIzq(nuevo);
			}
			else
				anterior.setDere(nuevo);

		}
	}

	public ArrayList<Puntaje> darListaPuntajes(){
		ArrayList<Puntaje> p = new ArrayList<Puntaje>();
		darListaPuntajesInorden(raizPuntaje, p);
		return p;
	}

	public ArrayList<Puntaje> darListaPuntajesInorden(Puntaje p, ArrayList<Puntaje> a){
		if(p != null){
			darListaPuntajesInorden(p.getIzq(), a);
			a.add(p);
			darListaPuntajesInorden(p.getDere(), a);
		}
		return a;
	}

	public int darPesoPuntajes(Puntaje p){
		if(p == null)
			return 0;
		else
			return 1 + darPesoPuntajes(p.getDere()) + darPesoPuntajes(p.getIzq());
	}

	public void detenerJuego(){
		pacMan.setDetenido(true);
		Fantasma f = primerFantasma;
		while(f != ultimo){
			f.setDetenido(true);
			f = f.getSig();
		}
		ultimo.setDetenido(true);
	}

	public void serializarPuntaje() {
		try {
			File f = new File("./data/puntaje.pacman");
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(raizPuntaje);
			oos.close();
			fos.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cargarPuntajes() {
		try {
			File f = new File("./data/puntaje.pacman");
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Puntaje p = (Puntaje) ois.readObject();
			raizPuntaje = p;
			ois.close();
			fis.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void reanudarJuego() {
		pacMan.setDetenido(false);
		Fantasma f = primerFantasma;
		while(f != ultimo){
			f.setDetenido(false);
			f = f.getSig();
		}
		ultimo.setDetenido(false);
	}
	public void cargarComida() {

		//		int x = 60;
		//		int y = 90;
		//		String ruta = "./imagenes/fruta.png";
		//		agregarComida(x, y, ruta);
		File epa = new File("./data/comida.txt");
		try {
			FileReader fl = new FileReader(epa);
			BufferedReader bf = new BufferedReader(fl);
			String linea = bf.readLine();
			while(linea != null && !linea.equals("")) {
				String[] spl = linea.split("\t");
				int x = Integer.parseInt(spl[0]);
				int y = Integer.parseInt(spl[1]);
				String ruta = "./imagenes/fruta.png";
				if(x == 360 || y == 540) {
					String ruta2 = "./imagenes/fruta3.png";
					agregarComidaEspecial(x,y,ruta2);
				}
				else
					agregarComida(x,y,ruta);

				linea = bf.readLine();
			}
			bf.close();
			fl.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * @return the raizPuntaje
	 */
	public Puntaje getRaizPuntaje() {
		return raizPuntaje;
	}

	/**
	 * @param raizPuntaje the raizPuntaje to set
	 */
	public void setRaizPuntaje(Puntaje raizPuntaje) {
		this.raizPuntaje = raizPuntaje;
	}

	public Puntaje busquedaBinaria(int b) {
		ArrayList<Puntaje> a = darListaPuntajesInorden(raizPuntaje, new ArrayList<Puntaje>());
		Puntaje encontrado = null;
		int inicio = 0;
		int fin = a.size()-1;
		while (inicio <= fin) {
			int medio = (inicio + fin ) / 2 ;
			System.out.println(a.get(medio).getPuntos());
			if (a.get(medio).getPuntos() == b) {
				encontrado = a.get(medio);
				break;
			} 
			else if (a.get(medio).getPuntos() > b) {
				fin = medio -1 ;
			} 
			else {
				inicio = medio + 1;
			}
		}
		if(encontrado == null) {

		}
		return encontrado;
	}

	public void burbuja(int arreglo[])
	{
		for(int i = 0; i < arreglo.length - 1; i++)
		{
			for(int j = 0; j < arreglo.length - 1; j++)
			{
				if (arreglo[j] < arreglo[j + 1])
				{
					int tmp = arreglo[j+1];
					arreglo[j+1] = arreglo[j];
					arreglo[j] = tmp;
				}
			}
		}
		for(int i = 0;i < arreglo.length; i++)
		{
			System.out.print(arreglo[i]+"\n");
		}
	}

	public static void seleccionPuntajes(int A[]) {
        int i, j, menor, pos, tmp;
        for (i = 0; i < A.length - 1; i++) { // tomamos como menor el primero
              menor = A[i]; // de los elementos que quedan por ordenar
              pos = i; // y guardamos su posición
              for (j = i + 1; j < A.length; j++){ // buscamos en el resto
                    if (A[j] < menor) { // del array algún elemento
                        menor = A[j]; // menor que el actual
                        pos = j;
                    }
              }
              if (pos != i){ // si hay alguno menor se intercambia
                  tmp = A[i];
                  A[i] = A[pos];
                  A[pos] = tmp;
              }
        }
}
}

