package modelo;

import java.io.Serializable;

public class Jugador implements Serializable{
	
	private String nombre;
	private int puntos;
	private int vidas;
	
	public Jugador(String nomb) {
		nombre = nomb;
		puntos = 0;
		vidas = 3;
	}


	public void aumentarPuntaje(int punta) {
		puntos += punta;
	}

	public int darPuntos() {
		return puntos;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/**
	 * @return the puntos
	 */
	public int getPuntos() {
		return puntos;
	}


	/**
	 * @param puntos the puntos to set
	 */
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}


	/**
	 * @return the vidas
	 */
	public int getVidas() {
		return vidas;
	}


	/**
	 * @param vidas the vidas to set
	 */
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	
	public void setDatos(String nombre, int pts, int vid){
		this.nombre = nombre;
		puntos = pts;
		vidas = vid;
	}


	public void restarVida() {
		vidas--;
	}
}
