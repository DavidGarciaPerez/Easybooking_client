package clientgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import clientcontroller.ClientController;
import utiles.SwingWorkerProgress;

public class PanelIniciarSesion extends JPanel {
	private static final long serialVersionUID = 1L;
	int anchuraPanel = 500;
	int alturaPanel = 300;
	private JTextField txtDavidgpopendeustoes;
	private JButton BotonAcceder;
	private ClientController controller; // Pasamos collector desde el "ClientFrame"
	private JRadioButton rdbtnConectaseConFacebook;
	private JRadioButton rdbtnConectarseConGoogle;

	/**
	 * Create the panel.
	 */
	public PanelIniciarSesion(ClientFrame frame, ClientController controller) {

		this.controller = controller;
		inicializar();
		componentes();
		añadirComponentes();
		eventos(frame);
	}

	private void inicializar() {
		txtDavidgpopendeustoes = new JTextField();
		BotonAcceder = new JButton("ACCEDER");
		rdbtnConectarseConGoogle = new JRadioButton("CONECTARSE CON GOOGLE");
		rdbtnConectaseConFacebook = new JRadioButton("CONECTASE CON FACEBOOK");
	}

	private void componentes() {

		txtDavidgpopendeustoes.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtDavidgpopendeustoes.setColumns(10);
		txtDavidgpopendeustoes.setBounds(71, 29, 361, 60);
		txtDavidgpopendeustoes.setText("david.g.p@opendeusto.es");
		BotonAcceder.setForeground(SystemColor.textHighlight);
		BotonAcceder.setFont(new Font("Tahoma", Font.BOLD, 20));
		BotonAcceder.setBackground(Color.DARK_GRAY);
		BotonAcceder.setBounds(154, 252, 193, 35);
		txtDavidgpopendeustoes.setBackground(Color.DARK_GRAY);
		txtDavidgpopendeustoes.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Introduzca email", TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		BotonAcceder.setBorder(new LineBorder(SystemColor.textHighlight, 2));
		txtDavidgpopendeustoes.setForeground(SystemColor.textHighlight);
		rdbtnConectarseConGoogle.setBounds(154, 114, 211, 25);
		rdbtnConectaseConFacebook.setBounds(154, 144, 211, 25);
	}

	private void añadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);
		add(txtDavidgpopendeustoes);
		add(BotonAcceder);
		add(rdbtnConectarseConGoogle);
		add(rdbtnConectaseConFacebook);
	}

	private String sistemaAutentificacion = null;

	private void eventos(ClientFrame frame) {
		BotonAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprobamos antes a ver que opción de login ha elegido el usuario:
				if (rdbtnConectarseConGoogle.isSelected() == true && rdbtnConectaseConFacebook.isSelected() == false) {
					// Nos conectamos con GOOGLE:
					sistemaAutentificacion = "GOOGLE";
					// Cargamos loader:
					if (comprobarCredenciales()) {
						new SwingWorkerProgress(e, frame, controller).setVisible(true);
					}
				} else if (rdbtnConectarseConGoogle.isSelected() == false
						&& rdbtnConectaseConFacebook.isSelected() == true) {
					// Nos conectamos con FACEBOOK:
					sistemaAutentificacion = "FACEBOOK";
					// Cargamos loader:
					if (comprobarCredenciales()) {
						new SwingWorkerProgress(e, frame, controller).setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "NINGUNA OPCIÓN DE AUTENTIFICACIÓN ESCOGIDA.", "¡ERROR!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private boolean comprobarCredenciales() {
		String usuario = txtDavidgpopendeustoes.getText();
		boolean dev = false;

		if (usuario.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN EMAIL.", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if (controller.login(txtDavidgpopendeustoes.getText(), sistemaAutentificacion)) {
					// Si está todo bien ya podemos acceder al panel principal:
					dev = true;
				} else {
					JOptionPane.showMessageDialog(null, "USUARIO & CONTRASEÑA INCORRECTOS!.", "¡ERROR!",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
			}
		}

		return dev;
	}
}
