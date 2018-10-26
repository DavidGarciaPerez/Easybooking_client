package client;

import java.awt.EventQueue;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel;

import clientGUI.ClientFrame;
import server.IServer;

public class Client {

	private IServer server;

	public Client() {
		// EDT para ajustar el tema propio al JFrame creado para el cliente:
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel((LookAndFeel) new SubstanceRavenLookAndFeel());
					ClientFrame frame = new ClientFrame(525, 325, Client.this);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void connect2Server(String[] args) {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			String URL = "//" + args[0] + ":" + args[1] + "/" + args[2];
			setServer((IServer) Naming.lookup(URL));
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					" *# Error connecting to Easybooking Server: " + e.getMessage());
		}
	}

	public IServer getServer() {
		return server;
	}

	public void setServer(IServer server) {
		this.server = server;
	}

}
