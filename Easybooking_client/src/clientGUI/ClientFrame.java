package clientGUI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import client.Client;

public class ClientFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane contenedorDePaneles;
	private int anchura;
	private int altura;
	private PanelIniciarSesion panelIniciarSesion;
	private Client client;
	
	/**
	 * Create the frame.
	 */
	public ClientFrame(int anchura, int altura, Client cliente) {

		// Antes de llamar a los métodos debemos asignar la anchura y altura al
		// JFrame:
		this.anchura = anchura;
		this.altura = altura;
		this.client = cliente;

		inicializar();
		componentes();
		añadirComponentes();
		eventos();
	}

	private void inicializar() {
		panelIniciarSesion = new PanelIniciarSesion();
		contentPane = new JPanel();
		contenedorDePaneles = new JScrollPane();
	}

	private void componentes() {
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new LineBorder(Color.ORANGE, 2));

		// El contenedor se ajustará automáticamente a la anchura y altura
		// pasadas por parámetro:
		contenedorDePaneles.setBounds(0, 0, anchura - 6, altura - 35);
	}

	private void añadirComponentes() {
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(anchura, altura);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		setTitle("Client Frame");

		contentPane.setLayout(null);
		contentPane.add(contenedorDePaneles);

	}

	private void eventos() {

	}

}
