package clientgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import clientcontroller.ClientController;
import data.dto.VueloDTO;

public class PanelPago extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientController controller;
	private ClientFrame frame;

	// Para guardar la lista de los vuelos que están siendo reservados:
	private List<VueloDTO> VUELOS_RESERVA = new ArrayList<VueloDTO>();
	// Para guardar las plazas y los nombres;
	private List<Integer> NUM_PLAZAS = new ArrayList<Integer>();
	private List<String[]> PASAJEROS = new ArrayList<String[]>();
	private JTable tableReservas;
	private JScrollPane scrollPaneReservas;
	private JButton btnPagarConVisa;
	private JButton btnPagarConPaypal;
	private JLabel lblElijaElMtodo;
	private JLabel lblTusReservasY;
	private JButton btnCancelarReservas;
	private TableModel tableModelReservasVuelos;

	public PanelPago(ClientFrame frame, ClientController controller, List<VueloDTO> vuelosReserva, List<Integer> plazas,
			List<String[]> pasajeros) {
		super();
		this.frame = frame;
		this.controller = controller;
		this.VUELOS_RESERVA = vuelosReserva;
		this.NUM_PLAZAS = plazas;
		this.PASAJEROS = pasajeros;
		this.inicializar();
		this.componentes();
		this.añadir();
		this.eventos();
		actualizarTablaVuelosReserva();
	}

	private void inicializar() {
		scrollPaneReservas = new JScrollPane();
		lblTusReservasY = new JLabel("TUS RESERVAS Y COSTE DE CADA UNA");
		lblElijaElMtodo = new JLabel("ELIJA EL M\u00C9TODO DE PAGO");
		btnPagarConPaypal = new JButton("PAGAR CON PAYPAL");
		btnPagarConVisa = new JButton("PAGAR CON VISA");
		tableReservas = new JTable();
		btnCancelarReservas = new JButton("CANCELAR RESERVAS");

	}

	private void componentes() {
		scrollPaneReservas.setBounds(12, 40, 1256, 492);
		lblTusReservasY.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTusReservasY.setForeground(Color.RED);
		lblTusReservasY.setBounds(10, 13, 621, 28);
		lblElijaElMtodo.setForeground(Color.GREEN);
		lblElijaElMtodo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblElijaElMtodo.setBounds(504, 545, 263, 34);
		btnPagarConPaypal.setBounds(558, 592, 162, 25);
		btnPagarConVisa.setBounds(558, 630, 162, 25);
		btnCancelarReservas.setBounds(558, 668, 162, 25);
	}

	private void añadir() {
		setSize(1280, 720);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);
		add(scrollPaneReservas);
		add(lblTusReservasY);
		add(lblElijaElMtodo);
		add(btnPagarConPaypal);
		add(btnPagarConVisa);
		add(btnCancelarReservas);
		scrollPaneReservas.setViewportView(tableReservas);

	}

	private void eventos() {
		btnPagarConPaypal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarReservas();
			}
		});
		btnPagarConVisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCancelarReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Volver atrás:
				frame.cargarPanelUsuario();
			}
		});
	}

	private void realizarReservas() {
		// Por cada vuelo que hay en la lista hay que hacer una reserva:
		int i = 0;
		for (VueloDTO vuelo : VUELOS_RESERVA) {
			try {
				if (controller.realizarReserva(vuelo, NUM_PLAZAS.get(i), PASAJEROS.get(i))) {
					JOptionPane.showMessageDialog(null, "LA RESERVA SE HA REALIZADO CON ÉXITO");
				} else {
					JOptionPane.showMessageDialog(null, "ERROR AL REALIZAR RESERVA", "ERROR RESERVA",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			i++;
		}
	}

	// Cada vez que se añade o se quite uno de los vuelos de la lista de vuelos
	// reserva tenemos que acutalizar de nuevo la tabla:
	private void actualizarTablaVuelosReserva() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inicializarTablaModelReservasVuelos();
				int reserva = 0;
				for (VueloDTO vuelo : VUELOS_RESERVA) {
					tableModelReservasVuelos.addRow(new Object[] { reserva, vuelo.getAerolinea().getNombreAerolinea(),
							vuelo.getNumVuelo(), vuelo.getHoraSalida(), vuelo.getHoraLlegada(), NUM_PLAZAS.get(reserva),
							NUM_PLAZAS.get(reserva) * 25 });
					reserva++;
				}
				// Introducimos el modelo en la tabla:
				tableReservas.setModel(tableModelReservasVuelos);
			}
		});
	}

	private void inicializarTablaModelReservasVuelos() {
		// Creacion de las columnas de la tabla:
		this.tableModelReservasVuelos = new TableModel();
	}

	// Clase interna Para agregar columnas:
	class TableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		public TableModel() {
			addColumn("RESERVA");
			addColumn("AEROLINEA");
			addColumn("NUM VUELO");
			addColumn("HORA SALIDA");
			addColumn("HOLA LLEGADA");
			addColumn("NUM PASAJEROS");
			addColumn("PRECIO EN €");
		}
	}

}
