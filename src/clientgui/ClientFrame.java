package clientgui;

import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import clientcontroller.ClientController;
import data.dto.UsuarioDTO;
import data.dto.VueloDTO;

public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UsuarioDTO user;
	private static int fondosUsuario = 5000;

	private JPanel contentPane;

	private JScrollPane contenedorDePaneles;

	private int anchura;
	private int altura;

	private PanelIniciarSesion panelIniciarSesion;
	private PanelUsuario panelUsuario;
	private PanelPago panelPago;

	private ClientController controller;

	/**
	 * Create the frame.
	 */
	public ClientFrame(int anchura, int altura, ClientController controller) {

		// Antes de llamar a los m�todos debemos asignar la anchura y altura al
		// JFrame:
		this.anchura = anchura;
		this.altura = altura;
		this.controller = controller;

		inicializar();
		componentes();
		a�adirComponentes();
		eventos();
	}

	private void inicializar() {
		contentPane = new JPanel();
		contenedorDePaneles = new JScrollPane();
	}

	private void componentes() {
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new LineBorder(Color.ORANGE, 2));

		// El contenedor se ajustar� autom�ticamente a la anchura y altura
		// pasadas por par�metro:
		contenedorDePaneles.setBounds(0, 0, anchura - 6, altura - 35);
	}

	private void a�adirComponentes() {
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

	// M�todo para cargar el panel de iniciar sesi�n en el scrollPane del JFrame
	// VentanaPrincipal:
	public void cargarPanelIniciarSesion() {
		panelIniciarSesion = new PanelIniciarSesion(this, this.controller);
		// Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelIniciarSesion);
	}

	// M�todo para cargar el panel del usuario:
	public void cargarPanelUsuario() {
		panelUsuario = new PanelUsuario(this, this.controller);
		// Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelUsuario);
		// Mostraremos todo cuando haya cargado:
		this.setVisible(true);
	}

	// M�todo para cargar el panel de pagos:
	public void cargarPanelPagos(List<VueloDTO> vuelosReserva, List<Integer> plazas, List<String[]> pasajeros) {
		panelPago = new PanelPago(this, this.controller, vuelosReserva, plazas, pasajeros);
		// Cargamos el panel en el scrollPane: contenedorDePaneles
		contenedorDePaneles.setViewportView(panelPago);
		// Mostraremos todo cuando haya cargado:
		this.setVisible(true);
	}

	public static UsuarioDTO getUser() {
		return user;
	}

	public static void setUser(UsuarioDTO user) {
		ClientFrame.user = user;
	}

	public static int getFondosUsuario() {
		return fondosUsuario;
	}

	public static void setFondosUsuario(int fondosUsuario) {
		ClientFrame.fondosUsuario = fondosUsuario;
	}

}
