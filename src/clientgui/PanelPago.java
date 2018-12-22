package clientgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
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
import data.dto.CreditcardDTO;
import data.dto.PagoDTO;
import data.dto.PaypalDTO;
import data.dto.ReservaDTO;
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
	private JButton btnCancelarReservas;

	private JLabel lblElijaElMtodo;
	private JLabel lblTusReservasY;

	private TableModel tableModelReservasVuelos;

	private PaypalDTO myPaypalAccount = new PaypalDTO("PAYPAL_ACCOUNT_1");
	private PaypalDTO paypalAccountToPay = new PaypalDTO("PAYPAL_ACCOUNT_2");

	private CreditcardDTO myCreditCard = new CreditcardDTO("4921561267849990", 123, new Date());
	private CreditcardDTO creditCardToPay = new CreditcardDTO("4921561267849991", 345, new Date());

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
				for (int i = 0; i < VUELOS_RESERVA.size(); i++) {
					try {
						if (controller.realizarPagoPaypal(myPaypalAccount, paypalAccountToPay, NUM_PLAZAS.get(i) * 25,
								"Reservando vuelo " + VUELOS_RESERVA.get(i).getNumVuelo() + " para " + NUM_PLAZAS.get(i)
										+ " pasajeros, con un precio de : " + NUM_PLAZAS.get(i) * 25 + " EUROS")) {

							// Si el pago ha ido bien porque tiene suficientes fondos entonces podemos
							// realizar la reserva:
							PagoDTO pago = new PagoDTO(myPaypalAccount, myCreditCard, NUM_PLAZAS.get(i) * 25,
									"Reservando vuelo " + VUELOS_RESERVA.get(i).getNumVuelo() + " para "
											+ NUM_PLAZAS.get(i) + " pasajeros, con un precio de : "
											+ NUM_PLAZAS.get(i) * 25 + " EUROS");
							ReservaDTO reserva = new ReservaDTO(NUM_PLAZAS.get(i) * 25,
									String.valueOf(NUM_PLAZAS.get(i)), ClientFrame.getUser(), VUELOS_RESERVA.get(i),
									pago);

							controller.realizarReserva(reserva, NUM_PLAZAS.get(i), PASAJEROS.get(i));

						} else {
							JOptionPane.showMessageDialog(null, "¡SE HA RECHAZADO SU PAGO!", "PAYPAL ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnPagarConVisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < VUELOS_RESERVA.size(); i++) {
					try {
						if (controller.realizarPagoCreditCard(myCreditCard, creditCardToPay, NUM_PLAZAS.get(i) * 25,
								"Reservando vuelo " + VUELOS_RESERVA.get(i).getNumVuelo() + " para " + NUM_PLAZAS.get(i)
										+ " pasajeros, con un precio de : " + NUM_PLAZAS.get(i) * 25 + " EUROS")) {

							// Si el pago ha ido bien porque tiene suficientes fondos entonces podemos
							// realizar la reserva:
							PagoDTO pago = new PagoDTO(myPaypalAccount, myCreditCard, NUM_PLAZAS.get(i) * 25,
									"Reservando vuelo " + VUELOS_RESERVA.get(i).getNumVuelo() + " para "
											+ NUM_PLAZAS.get(i) + " pasajeros, con un precio de : "
											+ NUM_PLAZAS.get(i) * 25 + " EUROS");
							ReservaDTO reserva = new ReservaDTO(NUM_PLAZAS.get(i) * 25,
									String.valueOf(NUM_PLAZAS.get(i)), ClientFrame.getUser(), VUELOS_RESERVA.get(i),
									pago);

							controller.realizarReserva(reserva, NUM_PLAZAS.get(i), PASAJEROS.get(i));

						} else {
							JOptionPane.showMessageDialog(null, "¡SE HA RECHAZADO SU PAGO!", "VISA ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btnCancelarReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Volver atrás:
				frame.cargarPanelUsuario();
			}
		});
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
