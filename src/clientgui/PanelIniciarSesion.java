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
import data.dto.UsuarioDTO;
import utiles.SwingWorkerProgress;

public class PanelIniciarSesion extends JPanel {
	private static final long serialVersionUID = 1L;

	private int anchuraPanel = 500;
	private int alturaPanel = 300;
	
	private String sistemaAutentificacion = null;

	private JTextField txtTextField;

	private JButton BotonAcceder;

	private JRadioButton rdbtnConectaseConFacebook;
	private JRadioButton rdbtnConectarseConGoogle;

	private ClientController controller; // Pasamos collector desde el "ClientFrame"

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
		txtTextField = new JTextField();
		BotonAcceder = new JButton("ACCEDER");
		rdbtnConectarseConGoogle = new JRadioButton("CONECTARSE CON GOOGLE");
		rdbtnConectaseConFacebook = new JRadioButton("CONECTASE CON FACEBOOK");
	}

	private void componentes() {
		txtTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtTextField.setColumns(10);
		txtTextField.setBounds(71, 29, 361, 60);
		txtTextField.setText("david.g.p@opendeusto.es");
		txtTextField.setBackground(Color.DARK_GRAY);
		txtTextField.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Introduzca email",
				TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		txtTextField.setForeground(SystemColor.textHighlight);

		BotonAcceder.setForeground(SystemColor.textHighlight);
		BotonAcceder.setFont(new Font("Tahoma", Font.BOLD, 20));
		BotonAcceder.setBackground(Color.DARK_GRAY);
		BotonAcceder.setBounds(154, 252, 193, 35);
		BotonAcceder.setBorder(new LineBorder(SystemColor.textHighlight, 2));

		rdbtnConectarseConGoogle.setBounds(154, 114, 211, 25);
		rdbtnConectaseConFacebook.setBounds(154, 144, 211, 25);
	}

	private void añadirComponentes() {
		setSize(anchuraPanel, alturaPanel);
		setBackground(Color.DARK_GRAY);
		setLayout(null);
		setBorder(null);
		add(txtTextField);
		add(BotonAcceder);
		add(rdbtnConectarseConGoogle);
		add(rdbtnConectaseConFacebook);
	}

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
		String usuario = txtTextField.getText();
		boolean dev = false;

		if (usuario.equals("")) {
			JOptionPane.showMessageDialog(null, "NO HAS INTRODUCIDO NINGUN EMAIL.", "¡ERROR!",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				if (controller.login(txtTextField.getText(), sistemaAutentificacion)) {
					// Si está todo bien ya podemos acceder al panel principal:
					dev = true;
					// Guardamos el usuario:
					ClientFrame.setUser(new UsuarioDTO("NANE_" + txtTextField.getText(), txtTextField.getText(),
							sistemaAutentificacion));
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
