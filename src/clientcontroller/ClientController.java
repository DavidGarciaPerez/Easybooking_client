package clientcontroller;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import clientgui.ClientFrame;
import clientremote.RMIServiceLocator;
import data.dto.CreditcardDTO;
import data.dto.PaypalDTO;
import data.dto.ReservaDTO;
import data.dto.VueloDTO;

public class ClientController {

	private RMIServiceLocator rsl = null;

	public ClientController(String[] args) throws RemoteException {

		this.rsl = new RMIServiceLocator(args);
		// EDT para ajustar el tema propio al JFrame creado para el cliente:
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				ClientFrame frame = new ClientFrame(525, 325, ClientController.this);
				frame.setVisible(true);
				frame.cargarPanelIniciarSesion();
			}
		});
	}

	public boolean registerUser(String nombre, String email, String sistemaAutentificacion) throws RemoteException {
		boolean dev = false;
		try {
			dev = this.rsl.getLoginService().registerUser(nombre, email, sistemaAutentificacion);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}

	public boolean login(String email, String sistemaAutentificacion) throws RemoteException {
		boolean dev = false;
		try {
			dev = this.rsl.getLoginService().login(email, sistemaAutentificacion);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}

	public boolean realizarPagoPaypal(PaypalDTO paypalOrigen, PaypalDTO paypalDestino, double importe, String concepto)
			throws RemoteException {
		boolean dev = false;
		try {
			dev = this.rsl.getPagoService().realizarPagoPaypal(paypalOrigen, paypalDestino, importe, concepto);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}

	public boolean realizarPagoCreditCard(CreditcardDTO creditcardOrigen, CreditcardDTO creditcardDestino,
			double importe, String concepto) throws RemoteException {
		boolean dev = false;
		try {
			dev = this.rsl.getPagoService().realizarPagoCreditCard(creditcardOrigen, creditcardDestino, importe,
					concepto);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}

	public boolean realizarReserva(ReservaDTO reservaARealizar, int nPlazas, String[] pasajeros)
			throws RemoteException {
		boolean dev = false;
		try {
			dev = this.rsl.getVueloService().realizarReserva(reservaARealizar, nPlazas, pasajeros);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dev;
	}

	public List<VueloDTO> buscarVuelos(String origen, String destino, int nPlazas) throws RemoteException {
		List<VueloDTO> vuelos = new ArrayList<>();
		try {
			vuelos = this.rsl.getVueloService().buscarVuelos(origen, destino, nPlazas);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vuelos;
	}

	public void exit() {
		System.exit(0);
	}

	public static void main(String[] args) throws RemoteException {
		try {
			new ClientController(args);
		} catch (Exception e1) {
			System.out.println(e1);
		}
	}

}
