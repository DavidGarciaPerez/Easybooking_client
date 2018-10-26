package clientMain;

import java.util.logging.Level;
import java.util.logging.Logger;

import client.Client;

public class ClientMain {
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("uso: java [policy] [codebase] cliente.Cliente [host] [port] [server]");
			System.exit(0);
		}

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			new Client().connect2Server(args);
		} catch (Exception e) {
			Logger.getLogger(Client.class.getName()).log(Level.WARNING,
					" *# Error connecting to Easybooking Server: " + e.getMessage());
		}
	}
}