package clientcontroller;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import clientgui.ClientFrame;
import clientremote.RMIServiceLocator;
import data.dto.ReservaDTO;
import data.dto.VueloDTO;

public class ClientController {

	private RMIServiceLocator rsl = null;

	public ClientController(String[] args) throws RemoteException {

		this.rsl = new RMIServiceLocator();
		this.rsl.setService(args);

		// EDT para ajustar el tema propio al JFrame creado para el cliente:
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
					ClientFrame frame = new ClientFrame(525, 325, ClientController.this);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
