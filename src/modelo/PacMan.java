package modelo;

public class PacMan implements Movible{

	public final static String RUTA_PACMAN = "./imagenes/p.gif"; 
	public final static int ARRIBA = 0;
	public final static int ABAJO = 1;
	public final static int DERECHA = 2;
	public final static int IZQUIERDA = 3;
	public static final int MAX_Y = 0;
	public static final int MAX_X = 0;


	private int posX;
	private int posY;
	private boolean detenido;
	private boolean comer;
	private String ruta;
	private int direccion;

	public PacMan(int x, int y) {
		posX = x;
		posY = y;
		detenido = false;
		direccion = DERECHA;
		comer = false;
		ruta = RUTA_PACMAN;
	}

	public boolean isDetenido() {
		return detenido;
	}

	public void setDetenido(boolean detenido) {
		this.detenido = detenido;
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

	public boolean isComer() {
		return comer;
	}

	public void setComer(boolean comer) {
		this.comer = comer;
	}

	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		setDetenido(false);
		this.direccion = direccion;
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

	public void resetPosicion() {
		posX = 325;
		posY = 525;
	}

	public void validarBordes() {
		if(posX > 630) {
			posX = 630;		
		}
		if(posY > 615) {
			posY = 615;
		}
		if(posX < -10) {
			posX = -10;
		}
		if(posY < 0) {
			posY = 0;
		}
	}

	@Override
	public void mover() {
		switch(direccion) {
		case ARRIBA:
			posY--;
			ruta = "./imagenes/pArriba.gif";
			validarBordes();
			break;
		case ABAJO:
			posY++;
			ruta = "./imagenes/pAbajo.gif";
			validarBordes();
			break;
		case DERECHA:
			posX++;
			ruta = "./imagenes/p.gif";
			validarBordes();
			break;
		case IZQUIERDA:
			posX--;
			ruta = "./imagenes/pIzquierda.gif";
			validarBordes();
			break;
		}
	}

	public int revertirDireccion(int d) {
		int res = 0;
		switch(d) {
		case PacMan.ABAJO:
			res = PacMan.ARRIBA;
			break;
			
		case PacMan.ARRIBA:
			res = PacMan.ABAJO;
			break;
			
		case PacMan.DERECHA:
			res = PacMan.IZQUIERDA;
			break;
			
		case PacMan.IZQUIERDA:
			res = PacMan.DERECHA;
			break;
		}
		
		return res;
	}

}
