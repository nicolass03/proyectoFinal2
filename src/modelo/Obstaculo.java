package modelo;

public class Obstaculo {
	
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	public Obstaculo sig;
	
	public Obstaculo(int x, int y, int w, int h) {
		posX = x;
		posY = y;
		width = w;
		height = h;
		
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


	/**
	 * @return the big
	 */
	public Obstaculo getSig() {
		return sig;
	}


	/**
	 * @param sig the sig to set
	 */
	public void setSig(Obstaculo sig) {
		this.sig = sig;
	}


	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}


	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}


	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}


	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	

}
