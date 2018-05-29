package modelo;

public class Comida {
	
	private int puntos;
	private int posX;
	private int posY;
	private Comida siguiente;
	private String ruta;
	private boolean isComida;
	
	public Comida(int x, int y, String ruta) {
		puntos = 10;
		posX = x;
		posY = y;
		siguiente = null;
		this.ruta = ruta;
		isComida = false;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Comida getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Comida siguiente) {
		this.siguiente = siguiente;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	/**
	 * @return the isComida
	 */
	public boolean isComida() {
		return isComida;
	}

	/**
	 * @param isComida the isComida to set
	 */
	public void setComida(boolean isComida) {
		this.isComida = isComida;
	}
	
	
}
