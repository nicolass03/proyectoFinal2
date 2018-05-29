package modelo;

import java.io.Serializable;

public class Puntaje implements Serializable{

	private int puntos;
	private String nombre;
	private Puntaje izq;
	private Puntaje dere;
	
	public Puntaje(String nom, int pun) {
		puntos = pun;
		nombre = nom;
		izq = null;
		dere = null;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Puntaje getIzq() {
		return izq;
	}

	public void setIzq(Puntaje izq) {
		this.izq = izq;
	}

	public Puntaje getDere() {
		return dere;
	}

	public void setDere(Puntaje dere) {
		this.dere = dere;
	}
	
	public void agregarPuntaje(){
		
	}
	
	public void eliminarPuntaje(String nombre){
		
	}

}
