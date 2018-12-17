package clientgui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import clientcontroller.ClientController;

public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane contenedorDePaneles;
	private int anchura;
	private int altura;
	private PanelIniciarSesion panelIniciarSesion;
	public PanelUsuario panelUsuario;
	private ClientController controller;

	/**
	 * Create the frame.
	 */
	public ClientFrame(int anchura, int altura, ClientController controller) {

		// Antes de llamar a los métodos debemos asignar la anchura y altura al
		// JFrame:
		this.anchura = anchura;
		this.altura = altura;
		this.controller = controller;

		inicializar();
		componentes();
		añadirComponentes();
		eventos();
	}

	private void inicializar() {
		panelIniciarSesion = new PanelIniciarSesion(this, this.controller);
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

	// Método para cargar el panel de iniciar sesión en el scrollPane del JFrame
	// VentanaPrincipal:
	public void cargarPanelIniciarSesion() {
		// Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelIniciarSesion);
	}

	// Método para cargar el panel del usuario:
	public void cargarPanelUsuario() {
		// Inicializamos el panel:
		panelUsuario = new PanelUsuario(this.controller);
		// Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelUsuario);
		// Mostraremos todo cuando haya cargado:
		this.setVisible(true);
	}

}
