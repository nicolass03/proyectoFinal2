package modelo;

import java.util.Random;

public class Fantasma implements Movible{
	
	public final static char MODO_NORMAL = 'N';
	public final static char MODO_AGARRABLE = 'A';
	public final static String RUTA_COMESTIBLE = "./imagenes/fantasmaComestible.gif";
	public final static String COLOR_ROJO = "ROJO";
	public final static String COLOR_AZUL = "AZUL";
	public final static String COLOR_AMARILLO = "AMARILLO";
	public final static String COLOR_VERDE = "VERDE";
	public final static int ARRIBA = 0;
	public final static int ABAJO = 1;
	public final static int DERECHA = 2;
	public final static int IZQUIERDA = 3;
	

	private String color;
	private char modo;
	private int posX;
	private int posY;
	private int direccion;
	private String ruta;
	private Fantasma ant;
	private Fantasma sig;
	private boolean detenido;
	


	public Fantasma(int x, int y, String colo, String ruta) {
		posX = x;
		posY = y;
		modo = MODO_NORMAL;
		color = colo;
		this.ruta = ruta;
		Random ran = new Random();
		direccion = ran.nextInt(3);
		ant = null;
		sig = null;
		
	}

	
	
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}



	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}



	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}



	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}



	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}



	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}



	/**
	 * @return the direccion
	 */
	public int getDireccion() {
		return direccion;
	}



	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}



	public Fantasma getAnt() {
		return ant;
	}

	public void setAnt(Fantasma izq) {
		this.ant = izq;
	}

	public Fantasma getSig() {
		return sig;
	}

	public void setSig(Fantasma dere) {
		this.sig = dere;
	}

	public char getModo() {
		return modo;
	}

	public void setModo(char modo) {
		this.modo = modo;
		setRuta(RUTA_COMESTIBLE);
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	public void resetPos() {
		posX = 325;
		posY = 325;
	}
	
	public void resetX() {
		posX = 0;
	}
	
	public void resetY() {
		posY = 0;
	}
	
	
	@Override
	public void validarBordes() {
		if(posX > 700) {
			posX = 0;
		}
		if(posX < -40) {
			posX = 700;
		}
		if(posY > 700) {
			posY = 50;
		}
		if(posY < 50) {
			posY = 700;
		}
	}

	/**
	 * @return the detenido
	 */
	public boolean isDetenido() {
		return detenido;
	}



	/**
	 * @param detenido the detenido to set
	 */
	public void setDetenido(boolean detenido) {
		this.detenido = detenido;
	}



	@Override
	public void mover() {
		switch(direccion) {
		case ARRIBA:
			posY--;
			validarBordes();
			break;
		case ABAJO:
			posY++;
			validarBordes();
			break;
		case DERECHA:
			posX++;
			validarBordes();
			break;
		case IZQUIERDA:
			posX--;
			validarBordes();
			break;
		}
	}
	
}
