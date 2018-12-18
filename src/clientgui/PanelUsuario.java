package clientgui;

import java.awt.Color;

import javax.swing.JPanel;

import clientcontroller.ClientController;
import data.dto.AerolineaDTO;
import data.dto.AeropuertoDTO;
import data.dto.VueloDTO;

import javax.swing.JComboBox;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class PanelUsuario extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientController controller;
	private ClientFrame frame;
	private JComboBox<Integer> comboBoxNumPlazas;
	private JComboBox<String> comboBoxDestino;
	private JComboBox<String> comboBoxOrigen;
	private JTable tableVuelos;
	private JScrollPane scrollPaneVuelos;
	private JButton btnBuscarVuelos;
	private JLabel lblEscojaUn;
	private JTable tableVuelosReserva;
	private JScrollPane scrollPaneVuelosReserva;
	private JLabel lblTablaDeVuelos;
	private JLabel lblEscojaUn_1;
	private JButton btnQuitarVueloDe;
	private JButton btnAadirVueloA;
	private JButton btnReservarVuelosYa;
	private TableModel tableModel;
	private TableModel tableModelReservasVuelos;

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
		tableVuelos = new JTable();
		lblEscojaUn = new JLabel("* ESCOJA UN VUELO PARA A\u00D1ADIR A SU RESERVA");
		lblTablaDeVuelos = new JLabel(
				"TABLA DE VUELOS DISPONIBLES ACTUALMENTE POR LAS COMPA\u00D1\u00CDAS VUELING Y RYANAIR");
		scrollPaneVuelosReserva = new JScrollPane();
		tableVuelosReserva = new JTable();
		btnAadirVueloA = new JButton("A\u00D1ADIR VUELO A LA RESERVA");
		btnQuitarVueloDe = new JButton("QUITAR VUELO DE MI RESERVA");
		btnReservarVuelosYa = new JButton("RESERVAR VUELOS YA MISMO");
		lblEscojaUn_1 = new JLabel("* ESCOJA UN VUELO PARA QUITARLO DE SU RESERVA");
		scrollPaneVuelos = new JScrollPane();
	}

	private void componentes() {
		comboBoxOrigen.setBounds(12, 13, 363, 36);
		comboBoxDestino.setBounds(387, 13, 363, 36);
		comboBoxNumPlazas.setBounds(762, 13, 260, 36);
		btnBuscarVuelos.setBounds(1034, 13, 234, 36);
		scrollPaneVuelos.setBounds(12, 96, 1256, 402);
		lblEscojaUn.setForeground(Color.RED);
		lblEscojaUn.setBounds(12, 504, 317, 23);
		lblTablaDeVuelos.setForeground(Color.GREEN);
		lblTablaDeVuelos.setBounds(12, 60, 535, 36);
		scrollPaneVuelosReserva.setBounds(12, 540, 1256, 94);
		btnAadirVueloA.setBounds(320, 503, 284, 25);
		lblEscojaUn_1.setForeground(Color.RED);
		lblEscojaUn_1.setBounds(12, 641, 317, 23);
		btnQuitarVueloDe.setBounds(341, 640, 284, 25);
		btnReservarVuelosYa.setForeground(Color.GREEN);
		btnReservarVuelosYa.setBounds(12, 677, 1256, 30);
		btnBuscarVuelos.setFont(new Font("Tahoma", Font.PLAIN, 15));
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
		add(scrollPaneVuelos);
		add(lblEscojaUn);
		add(lblTablaDeVuelos);
		add(scrollPaneVuelosReserva);
		add(btnAadirVueloA);
		add(lblEscojaUn_1);
		add(btnQuitarVueloDe);
		add(btnReservarVuelosYa);

		scrollPaneVuelos.setViewportView(tableVuelos);
		scrollPaneVuelosReserva.setViewportView(tableVuelosReserva);
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
		btnAadirVueloA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Primero tenemos que obtener qué objeto VueloDTO está siendo seleccionado:
				// Ahora podemos procesarlo para añadirlo a la tabla de abajo:
				// Añadimos el vuelo si no está ya en la lista:
				if (VUELOS_RESERVA.isEmpty() == false) {
					boolean existe = false;
					for (VueloDTO v : VUELOS_RESERVA) {
						if (v.getNumVuelo() == VUELOS.get(tableVuelos.getSelectedRow()).getNumVuelo()) {
							existe = true;
							break;
						}
					}
					if (existe) {
						JOptionPane.showMessageDialog(null, "YA TIENES EL VUELO : "
								+ VUELOS.get(tableVuelos.getSelectedRow()).getNumVuelo() + " EN LA TABLA DE RESERVAS",
								"ERROR!", JOptionPane.ERROR_MESSAGE);
					} else {
						// Podemos guardar el vuelo:
						anyadirVueloAListaVuelosReserva(VUELOS.get(tableVuelos.getSelectedRow()));
					}
				} else {
					// No hace falta comprobar si existe porque no hay ninguno:
					// Podemos guardar el vuelo:
					anyadirVueloAListaVuelosReserva(VUELOS.get(tableVuelos.getSelectedRow()));
				}
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
						tableModel.addRow(new Object[] { vuelo.getAeropuertoOrigen().getUbicacion(),
								vuelo.getAeropuertoDestino().getUbicacion(), vuelo.getAeropuertoOrigen().getNombre(),
								vuelo.getAeropuertoDestino().getNombre(), vuelo.getAerolinea().getNombreAerolinea(),
								vuelo.getNumVuelo(), vuelo.getAsientosLibres(), vuelo.getHoraSalida(),
								vuelo.getHoraLlegada() });
					}
					// Introducimos el modelo en la tabla:
					tableVuelos.setModel(tableModel);
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
		this.tableModel = new TableModel();
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
