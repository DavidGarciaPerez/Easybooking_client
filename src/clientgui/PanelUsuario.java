package clientgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import clientcontroller.ClientController;
import data.dto.VueloDTO;
import javax.swing.UIManager;

public class PanelUsuario extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox<Integer> comboBoxNumPlazas;
	private JComboBox<String> comboBoxDestino;
	private JComboBox<String> comboBoxOrigen;

	private JTable tableVuelosRyanair;
	private JTable tableVuelosReserva;
	private JTable tableVuelosVueling;

	private JScrollPane scrollPaneRyanair;
	private JScrollPane scrollPaneVuelosReserva;
	private JScrollPane scrollPaneVueling;

	private JButton btnBuscarVuelos;
	private JButton buttonAñadirVueloVueling;
	private JButton btnQuitarVueloDe;
	private JButton btnAñadirVueloRyanair;
	private JButton btnReservarVuelosYa;
	private JButton btnMostrarMisReservas;
	private JButton btnQuitarTodosLos;

	private JLabel lblTablaDeVuelos;
	private JLabel lblEscojaUn_1;
	private JLabel lblNombreDelUsuario;
	private JLabel lblNombre;
	private JLabel lblFondos;
	private JLabel lblFondosActuales;
	private JLabel lblTablaConMis;
	private JLabel label;
	private JLabel lblTablaDeVuelos_1;
	private JLabel lblEscojaUn;

	private TableModel tableModelRyanair;
	private TableModel tableModelVueling;
	private TableModel tableModelReservasVuelos;

	private ClientController controller;

	private ClientFrame frame;

	// Guardamos la lista siempre para poder luego buscar el vuelo a reservar!
	private List<VueloDTO> VUELOS;
	// Para guardar la lista de los vuelos que están siendo reservados:
	private List<VueloDTO> VUELOS_RESERVA = new ArrayList<VueloDTO>();
	// Para guardar las plazas y los nombres;
	private List<Integer> NUM_PLAZAS = new ArrayList<Integer>();
	private List<String[]> PASAJEROS = new ArrayList<String[]>();

	String[] ubicacionAeropuertos = { "Berlín", "Berlín", "Berlín", "Bremen", "Brunswick", "Colonia, Bonn", "Dortmund",
			"Dresde", "Düsseldorf", "Weeze", "Erfurt", "Fráncfort del Meno", "Lautzenhausen", "Friedrichshafen",
			"Hamburgo", "Langenhagen", "Rastatt", "Altenburgo", "Leipzig, Halle (Saale)", "Lübeck", "Mannheim",
			"Memmingerberg", "Münster, Osnabrück", "Erding", "Núremberg", "Paderborn, Lippstadt", "Rostock",
			"Saarbrücken", "Stuttgart", "Sylt" };

	public PanelUsuario(ClientFrame frame, ClientController controller) {
		super();
		this.frame = frame;
		this.controller = controller;
		this.inicializar();
		this.componentes();
		this.añadir();
		this.eventos();
		this.añadirCamposAlComboBox();
	}

	private void inicializar() {
		comboBoxOrigen = new JComboBox<String>();
		comboBoxDestino = new JComboBox<String>();
		comboBoxNumPlazas = new JComboBox<Integer>();

		btnBuscarVuelos = new JButton("BUSCAR VUELOS");
		btnAñadirVueloRyanair = new JButton("A\u00D1ADIR VUELO A LA RESERVA");
		btnQuitarVueloDe = new JButton("QUITAR VUELO DE MI RESERVA");
		btnReservarVuelosYa = new JButton("RESERVAR VUELOS YA MISMO");
		buttonAñadirVueloVueling = new JButton("A\u00D1ADIR VUELO A LA RESERVA");
		btnQuitarTodosLos = new JButton("QUITAR TODOS LOS VUELOS DE MI RESERVA");
		btnMostrarMisReservas = new JButton("MOSTRAR MIS RESERVAS HASTA HOY");

		tableVuelosRyanair = new JTable();
		tableVuelosReserva = new JTable();
		tableVuelosVueling = new JTable();

		lblEscojaUn = new JLabel("* ESCOJA UN VUELO PARA A\u00D1ADIR A SU RESERVA");
		lblTablaDeVuelos = new JLabel("TABLA DE VUELOS DISPONIBLES POR RYANAIR");
		lblEscojaUn_1 = new JLabel("* ESCOJA UN VUELO PARA QUITARLO DE SU RESERVA");
		lblTablaConMis = new JLabel("RESERVAS DE VUELOS PENDIENTES DE PAGO");
		lblTablaDeVuelos_1 = new JLabel("TABLA DE VUELOS DISPONIBLES POR VUELING\r\n");
		label = new JLabel("* ESCOJA UN VUELO PARA A\u00D1ADIR A SU RESERVA");
		lblNombreDelUsuario = new JLabel("NOMBRE DEL USUARIO :");
		lblNombre = new JLabel(ClientFrame.getUser().getNombre());
		lblFondos = new JLabel("FONDOS :");
		lblFondosActuales = new JLabel(String.valueOf(ClientFrame.getFondosUsuario()));

		scrollPaneVuelosReserva = new JScrollPane();
		scrollPaneRyanair = new JScrollPane();
		scrollPaneVueling = new JScrollPane();
	}

	private void componentes() {
		comboBoxOrigen.setBounds(12, 31, 363, 58);
		comboBoxOrigen.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "SELECCIONE UN ORIGEN",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 0, 0)));

		comboBoxDestino.setBounds(387, 31, 363, 58);
		comboBoxDestino.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "SELECCIONE UN DESTINO",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 0, 0)));

		comboBoxNumPlazas.setBounds(762, 31, 260, 58);
		comboBoxNumPlazas.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"SELECCIONE NUM PASAJEROS", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 0, 0)));

		btnBuscarVuelos.setBounds(1034, 31, 234, 58);
		btnBuscarVuelos.setFont(new Font("Tahoma", Font.PLAIN, 15));

		scrollPaneRyanair.setBounds(12, 151, 613, 263);

		lblEscojaUn.setForeground(Color.RED);
		lblEscojaUn.setBounds(12, 417, 301, 43);
		lblEscojaUn.setHorizontalAlignment(SwingConstants.CENTER);

		lblTablaDeVuelos.setForeground(Color.GREEN);
		lblTablaDeVuelos.setBounds(12, 102, 613, 36);
		lblTablaDeVuelos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTablaDeVuelos.setHorizontalAlignment(SwingConstants.CENTER);

		scrollPaneVuelosReserva.setBounds(12, 506, 1256, 94);

		btnAñadirVueloRyanair.setBounds(315, 417, 310, 43);

		lblEscojaUn_1.setForeground(Color.RED);
		lblEscojaUn_1.setBounds(12, 603, 325, 43);
		lblEscojaUn_1.setHorizontalAlignment(SwingConstants.CENTER);

		btnQuitarVueloDe.setBounds(341, 603, 284, 43);

		btnReservarVuelosYa.setForeground(new Color(0, 128, 0));
		btnReservarVuelosYa.setBounds(984, 603, 284, 43);
		btnReservarVuelosYa.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblTablaConMis.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTablaConMis.setHorizontalAlignment(SwingConstants.CENTER);
		lblTablaConMis.setForeground(Color.GREEN);
		lblTablaConMis.setBounds(12, 466, 1256, 36);

		scrollPaneVueling.setBounds(637, 152, 631, 262);

		lblTablaDeVuelos_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTablaDeVuelos_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTablaDeVuelos_1.setForeground(Color.GREEN);
		lblTablaDeVuelos_1.setBounds(639, 102, 629, 36);

		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setBounds(637, 417, 317, 43);

		buttonAñadirVueloVueling.setBounds(958, 417, 310, 43);

		btnQuitarTodosLos.setBounds(637, 603, 335, 43);

		lblNombreDelUsuario.setForeground(new Color(255, 140, 0));
		lblNombreDelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNombreDelUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreDelUsuario.setBounds(12, 659, 228, 48);

		lblNombre.setForeground(new Color(0, 250, 154));
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNombre.setBounds(252, 659, 285, 48);

		lblFondos.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondos.setForeground(new Color(255, 140, 0));
		lblFondos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFondos.setBounds(549, 659, 125, 48);

		lblFondosActuales.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondosActuales.setForeground(new Color(0, 250, 154));
		lblFondosActuales.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblFondosActuales.setBounds(668, 659, 125, 48);

		btnMostrarMisReservas.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnMostrarMisReservas.setBounds(805, 659, 463, 48);
	}

	private void añadir() {
		setSize(1280, 720);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);

		add(comboBoxOrigen);
		add(comboBoxDestino);
		add(comboBoxNumPlazas);
		add(btnBuscarVuelos);
		add(scrollPaneRyanair);
		add(lblEscojaUn);
		add(lblTablaDeVuelos);
		add(scrollPaneVuelosReserva);
		add(btnAñadirVueloRyanair);
		add(lblEscojaUn_1);
		add(btnQuitarVueloDe);
		add(btnReservarVuelosYa);
		add(lblTablaConMis);
		add(scrollPaneVueling);
		add(lblTablaDeVuelos_1);
		add(label);
		add(buttonAñadirVueloVueling);
		add(btnQuitarTodosLos);
		add(lblNombreDelUsuario);
		add(lblNombre);
		add(lblFondos);
		add(lblFondosActuales);
		add(btnMostrarMisReservas);

		scrollPaneRyanair.setViewportView(tableVuelosRyanair);
		scrollPaneVuelosReserva.setViewportView(tableVuelosReserva);
		scrollPaneVueling.setViewportView(tableVuelosVueling);
	}

	private void eventos() {
		btnBuscarVuelos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Solo podemos mostrar los vuelos de las aerolíneas si los campos son distintos
				// uno del otro!:
				if ((String) comboBoxOrigen.getSelectedItem() != (String) comboBoxDestino.getSelectedItem()) {
					// Ya podemos obtener los vuelos del gateway:
					buscarVuelos();
				}
			}
		});
		btnAñadirVueloRyanair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				anyadirVueloAListaVuelosReserva(tableVuelosRyanair);
			}
		});
		buttonAñadirVueloVueling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				anyadirVueloAListaVuelosReserva(tableVuelosVueling);
			}
		});
		btnQuitarTodosLos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < tableVuelosReserva.getRowCount(); i++) {
					quitarVueloDeListaVuelosReserva(VUELOS_RESERVA.get(0));
				}
			}
		});
		btnMostrarMisReservas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnQuitarVueloDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Primero tenemos que obtener qué objeto VueloDTO está siendo seleccionado:
				// Ahora podemos procesarlo para añadirlo a la tabla de abajo:
				quitarVueloDeListaVuelosReserva(VUELOS_RESERVA.get(tableVuelosReserva.getSelectedRow()));
			}
		});
		btnReservarVuelosYa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Antes de realizar las reservas hay que pagar:
				frame.cargarPanelPagos(VUELOS_RESERVA, NUM_PLAZAS, PASAJEROS);
			}
		});
	}

	private void añadirCamposAlComboBox() {
		for (String campo : ubicacionAeropuertos) {
			this.comboBoxOrigen.addItem(campo);
			this.comboBoxDestino.addItem(campo);
		}
		for (int i = 0; i < 100; i++) {
			this.comboBoxNumPlazas.addItem(i);
		}
	}

	// Obtenemos los vuelos que nos pasa el servidor y los mostramos en una tabla de
	// vuelos:
	private void buscarVuelos() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inicializarTablaModelVuelos();
				VUELOS = new ArrayList<VueloDTO>();
				try {
					VUELOS = controller.buscarVuelos((String) comboBoxOrigen.getSelectedItem(),
							(String) comboBoxDestino.getSelectedItem(), (Integer) comboBoxNumPlazas.getSelectedItem());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (VUELOS.isEmpty() == false) {
					for (VueloDTO vuelo : VUELOS) {
						if (vuelo.getAerolinea().getNombreAerolinea().equalsIgnoreCase("RYANAIR")) {
							tableModelRyanair.addRow(new Object[] { vuelo.getAeropuertoOrigen().getUbicacion(),
									vuelo.getAeropuertoDestino().getUbicacion(),
									vuelo.getAeropuertoOrigen().getNombre(), vuelo.getAeropuertoDestino().getNombre(),
									vuelo.getAerolinea().getNombreAerolinea(), vuelo.getNumVuelo(),
									vuelo.getAsientosLibres(), vuelo.getHoraSalida(), vuelo.getHoraLlegada() });
						} else {
							tableModelVueling.addRow(new Object[] { vuelo.getAeropuertoOrigen().getUbicacion(),
									vuelo.getAeropuertoDestino().getUbicacion(),
									vuelo.getAeropuertoOrigen().getNombre(), vuelo.getAeropuertoDestino().getNombre(),
									vuelo.getAerolinea().getNombreAerolinea(), vuelo.getNumVuelo(),
									vuelo.getAsientosLibres(), vuelo.getHoraSalida(), vuelo.getHoraLlegada() });
						}
					}
					// Introducimos el modelo en la tabla:
					tableVuelosRyanair.setModel(tableModelRyanair);
					// Introducimos el modelo en la tabla:
					tableVuelosVueling.setModel(tableModelVueling);
				} else {
					JOptionPane.showMessageDialog(null, "NINGUN VUELO ENCONTRADO", "N/A",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}

	// Método para guardar el vuelo que el usuario elige en la tabla de vuelos
	// principal para
	// luego realizar una reserva de varios vuelos:
	private void anyadirVueloAListaVuelosReserva(VueloDTO vuelo) {
		// Añadimos el vuelo:
		this.VUELOS_RESERVA.add(vuelo);
		// Añadimos las plazas del vuelo:
		this.NUM_PLAZAS.add((Integer) this.comboBoxNumPlazas.getSelectedItem());
		// Añadimos los pasajeros del vuelo:
		// Creamos tantos pasajeros aleatorios como numero de plazas se ha reservado:
		String[] pasajeros = new String[(Integer) this.comboBoxNumPlazas.getSelectedItem()];
		for (int i = 0; i < (Integer) this.comboBoxNumPlazas.getSelectedItem(); i++) {
			pasajeros[i] = "PASAJERO_" + (i + 1);
		}
		this.PASAJEROS.add(pasajeros);
		// Actualizamos tabla:
		actualizarTablaVuelosReserva();
	}

	private void anyadirVueloAListaVuelosReserva(JTable tablaAerolinea) {
		// Primero tenemos que obtener qué objeto VueloDTO está siendo seleccionado:
		// Ahora podemos procesarlo para añadirlo a la tabla de abajo:
		// Añadimos el vuelo si no está ya en la lista:
		if (VUELOS_RESERVA.isEmpty() == false) {
			boolean existe = false;
			for (VueloDTO v : VUELOS_RESERVA) {
				if (v.getNumVuelo() == VUELOS
						.get(getIndex((int) tablaAerolinea.getValueAt(tablaAerolinea.getSelectedRow(), 5)))
						.getNumVuelo()) {
					existe = true;
					break;
				}
			}
			if (existe) {
				JOptionPane.showMessageDialog(null,
						"YA TIENES EL VUELO : " + VUELOS
								.get(getIndex((int) tablaAerolinea.getValueAt(tablaAerolinea.getSelectedRow(), 5)))
								.getNumVuelo() + " EN LA TABLA DE RESERVAS",
						"ERROR!", JOptionPane.ERROR_MESSAGE);
			} else {
				// Podemos guardar el vuelo:
				anyadirVueloAListaVuelosReserva(
						VUELOS.get(getIndex((int) tablaAerolinea.getValueAt(tablaAerolinea.getSelectedRow(), 5))));
			}
		} else {
			// No hace falta comprobar si existe porque no hay ninguno:
			// Podemos guardar el vuelo:
			anyadirVueloAListaVuelosReserva(
					VUELOS.get(getIndex((int) tablaAerolinea.getValueAt(tablaAerolinea.getSelectedRow(), 5))));
		}
	}

	private void quitarVueloDeListaVuelosReserva(VueloDTO vuelo) {
		for (int i = 0; i < this.VUELOS_RESERVA.size(); i++) {
			if (vuelo.getNumVuelo() == VUELOS_RESERVA.get(i).getNumVuelo()) {
				// Lo quitamos de la lista VUELOS_RESERVA:
				this.VUELOS_RESERVA.remove(i);
				break;
			}
		}
		// Actualizamos tabla:
		actualizarTablaVuelosReserva();
	}

	// Cada vez que se añade o se quite uno de los vuelos de la lista de vuelos
	// reserva tenemos que acutalizar de nuevo la tabla:
	private void actualizarTablaVuelosReserva() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inicializarTablaModelReservasVuelos();
				for (VueloDTO vuelo : VUELOS_RESERVA) {
					tableModelReservasVuelos.addRow(new Object[] { vuelo.getAeropuertoOrigen().getUbicacion(),
							vuelo.getAeropuertoDestino().getUbicacion(), vuelo.getAeropuertoOrigen().getNombre(),
							vuelo.getAeropuertoDestino().getNombre(), vuelo.getAerolinea().getNombreAerolinea(),
							vuelo.getNumVuelo(), vuelo.getAsientosLibres(), vuelo.getHoraSalida(),
							vuelo.getHoraLlegada() });
				}
				// Introducimos el modelo en la tabla:
				tableVuelosReserva.setModel(tableModelReservasVuelos);
			}
		});
	}

	private void inicializarTablaModelReservasVuelos() {
		// Creacion de las columnas de la tabla:
		this.tableModelReservasVuelos = new TableModel();
	}

	private void inicializarTablaModelVuelos() {
		// Creacion de las columnas de la tabla:
		this.tableModelRyanair = new TableModel();
		// Creacion de las columnas de la tabla:
		this.tableModelVueling = new TableModel();
	}

	private int getIndex(int vuelo) {
		int dev = 0;
		for (int i = 0; i < VUELOS.size(); i++) {
			if (VUELOS.get(i).getNumVuelo() == vuelo) {
				dev = i;
				break;
			}
		}
		return dev;
	}

	// Clase interna Para agregar columnas:
	class TableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		public TableModel() {
			addColumn("ORIGEN");
			addColumn("DESTINO");
			addColumn("AEROPUERTO ORIGEN");
			addColumn("AEROPUERTO DESTINO");
			addColumn("AEROLINEA");
			addColumn("NUM VUELO");
			addColumn("ASIENTOS LIBRES");
			addColumn("HORA SALIDA");
			addColumn("HOLA LLEGADA");
		}
	}
}
