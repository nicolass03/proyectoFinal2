package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.*;

import exception.ExceptionArchivoNoEncontrado;
import exception.ExceptionSinNombre;
import modelo.Juego;
import modelo.Puntaje;

public class DialogoPrincipal extends JFrame implements ActionListener{

	public static final String NJ = "Nuevo juego";
	public static final String CJ = "Cargar juego";
	public static final String VP = "Ver puntajes";
	public static final String SALIR = "Salir";

	private VentanaPacman v;

	private Juego juego;

	private JLabel banner;

	private JLabel nombre;
	private JTextField txtNombre;

	private JButton verPuntajes;
	private JButton cargarJuego;
	private JButton empezarJuego;
	private JButton salir;

	public DialogoPrincipal() {

		setIconImage(new ImageIcon("./imagenes/icon.png").getImage());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(640,360));
		setUndecorated(true);

		juego = new Juego();
		juego.inicializarJuego();

		this.getContentPane().setBackground(Color.BLACK);
		banner = new JLabel(new ImageIcon("./imagenes/banner.png"));
		verPuntajes = new JButton(VP);
		verPuntajes.setForeground(Color.YELLOW);
		verPuntajes.setBackground(Color.BLACK);
		verPuntajes.setActionCommand(VP);
		verPuntajes.addActionListener(this);
		cargarJuego = new JButton(CJ);
		cargarJuego.addActionListener(this);
		cargarJuego.setActionCommand(CJ);
		cargarJuego.setBackground(Color.BLACK);
		cargarJuego.setForeground(Color.YELLOW);
		empezarJuego = new JButton(NJ);
		empezarJuego.setActionCommand(NJ);
		empezarJuego.addActionListener(this);
		empezarJuego.setBackground(Color.BLACK);
		empezarJuego.setForeground(Color.YELLOW);
		salir = new JButton(SALIR);
		salir.addActionListener(this);
		salir.setActionCommand(SALIR);
		salir.setForeground(Color.YELLOW);
		salir.setBackground(Color.BLACK);

		nombre = new JLabel("Nombre", JLabel.RIGHT);
		nombre.setForeground(Color.YELLOW);
		nombre.setPreferredSize(new Dimension(100,25));
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre = new JTextField();
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.setPreferredSize(new Dimension(200,25));
		txtNombre.setForeground(Color.YELLOW);
		txtNombre.setBackground(Color.BLACK);

		JPanel aux = new JPanel(new FlowLayout());
		aux.setPreferredSize(new Dimension(640, 50));
		aux.setOpaque(false);
		aux.add(nombre);
		aux.add(txtNombre);

		add(banner);
		add(aux);
		add(empezarJuego);
		add(cargarJuego);
		add(verPuntajes);
		add(salir);
		pack();
		centrar();
	}

	public void exit() {
		System.exit(0);
	}

	public void centrar() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = this.getSize();
		int x = (int) ((screenSize.width / 2) - (d.width / 2));
		int y = (int) ((screenSize.height / 2) - (d.height / 2));		
		setLocation(x,y);
	}

	public void abrirVentanaJuego(boolean cargo) {
		try {
			if(cargo == false) {
				if(txtNombre.getText() == null || txtNombre.getText().equals("")) {
					throw new ExceptionSinNombre("");
				}
				else {
					String nombre = txtNombre.getText();
					v = new VentanaPacman(this, juego);
					v.setVisible(true);
					v.setNombreJugador(nombre);
					juego.agregarJugador(nombre);		
					this.setVisible(false);
					this.dispose();
				}				
			}
			else {
				JFileChooser fc = new JFileChooser();
				int seleccion = fc.showOpenDialog(this);
				
				File f = null;
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();
					String extension = "";

					int i = f.getName().lastIndexOf('.');
					if (i > 0) {
					    extension = f.getName().substring(i+1);
					}
					if(!extension.equals("pacman")) {
						throw new ExceptionArchivoNoEncontrado("Archivo invalido.");
					}
				}
				v = new VentanaPacman(this, juego);
				v.setVisible(true);
				v.cargarJuego(f);		
				this.setVisible(false);
				this.dispose();

			}
		} catch (ExceptionSinNombre e) {
			JOptionPane.showMessageDialog(this, "Escriba un nombre valido", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (ExceptionArchivoNoEncontrado e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void abrirDialogoPuntajes() {
		DialogoPuntajes d = new DialogoPuntajes(this);
		d.cargarPuntos(juego.darListaPuntajesInorden(juego.getRaizPuntaje(), new ArrayList<Puntaje>()));
		d.setVisible(true);
//		this.setVisible(false);
//		this.dispose();
	}

	public static void main(String[] args) {

		DialogoPrincipal d = new DialogoPrincipal();
		d.setVisible(true);
	}

	
	
	@Override
	public void actionPerformed(ActionEvent a) {
		String e = a.getActionCommand();
		if(e.equals(SALIR)) {
			exit();
		}
		else if(e.equals(NJ)) {
			abrirVentanaJuego(false);
			
		}
		else if(e.equals(CJ)) {
			abrirVentanaJuego(true);

		}
		else if(e.equals(VP)) {
			abrirDialogoPuntajes();
		}
	}
}
