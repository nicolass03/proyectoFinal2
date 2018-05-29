package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Jugador;
import modelo.Puntaje;

public class DialogoPuntajes extends JDialog{

	private JButton ordenarBurbuja;
	private JButton buscar;
	private JPanel aux;
	private DialogoPrincipal v;

	public DialogoPuntajes(DialogoPrincipal i) {
		v = i;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosed(WindowEvent e)
			{
//				i.closedDialog();
				setVisible(false);
				dispose();
			}

			public void windowClosing(WindowEvent e)
			{
//				i.closedDialog();
				setVisible(false);
				dispose();
			}
		});

		setTitle("Puntajes");
		setLayout(new BorderLayout()); 
		setResizable(false);

//		ordNombre = new JButton("Ordenar por nombre");
//		ordNombre.setActionCommand(NOMBRE);
//		ordNombre.addActionListener(this);
//		ordNombre.setEnabled(false);
//		ordPuntaje = new JButton("Ordenar por puntaje");
//		ordPuntaje.setActionCommand(PUNTAJE);
//		ordPuntaje.addActionListener(this);
//		ordPuntaje.setEnabled(false);
//		buscar = new JButton("Buscar");
//		buscar.setActionCommand(BUSCAR);
//		buscar.addActionListener(this);
//		buscar.setEnabled(false);
		aux = new JPanel(new GridLayout(20,2));

//		JPanel auxi = new JPanel(new FlowLayout());
//		auxi.add(ordNombre);
//		auxi.add(ordPuntaje);
//		auxi.add(buscar);

//		if(cargarPuntos() == false) {
//			ordPuntaje.setEnabled(true);
//			ordNombre.setEnabled(true);
//			buscar.setEnabled(true);
//		}
		add(aux, BorderLayout.CENTER);
//		add(auxi, BorderLayout.SOUTH);

		pack();
		centrar();
	}
	
	public void centrar() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension d = this.getSize();
		int x = (int) ((screenSize.width / 2) - (d.width / 2));
		int y = (int) ((screenSize.height / 2) - (d.height / 2));		
		setLocation(x,y);
	}

	public void cargarPuntos(ArrayList<Puntaje> a) {
		aux.removeAll();
		for (Puntaje p : a) {
			if(p != null) {
				JLabel nombre = new JLabel(p.getNombre(), JLabel.CENTER);
				JLabel puntos = new JLabel(p.getPuntos()+"", JLabel.CENTER);
				aux.add(nombre);
				aux.add(puntos);
				pack();
			}
		}
	}
}
