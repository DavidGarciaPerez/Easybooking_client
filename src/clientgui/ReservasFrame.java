package clientgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import data.dto.ReservaDTO;

public class ReservasFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private TableModel tableModel;
	private List<ReservaDTO> reservas = new ArrayList<ReservaDTO>();

	public ReservasFrame(List<ReservaDTO> reservas) {
		this.reservas = reservas;
		inicializar();
		componentes();
		añadirComponentes();
		mostrarReservas();
	}

	private void inicializar() {
		contentPane = new JPanel();
		scrollPane = new JScrollPane();
		table = new JTable();
	}

	private void componentes() {
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new LineBorder(Color.ORANGE, 2));
		contentPane.setLayout(null);
		scrollPane.setBounds(6, 6, 1262, 673);
	}

	private void añadirComponentes() {
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		setTitle("Reservas Frame");

		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
	}

	private void mostrarReservas() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				inicializarTabla();
				for (ReservaDTO reserva : reservas) {
					tableModel.addRow(new Object[] { reserva.getVuelo().getNumVuelo(), reserva.getPago().getImporte(),
							reserva.getUsuario().getNombre() });
				}
				// Introducimos el modelo en la tabla:
				table.setModel(tableModel);
			}
		});
	}

	private void inicializarTabla() {
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
