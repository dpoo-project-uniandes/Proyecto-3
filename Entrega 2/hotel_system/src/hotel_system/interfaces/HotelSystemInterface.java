package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HotelSystemInterface extends JFrame {

	public static void main(String[] args) throws IOException  {
		HotelSystemInterface HotelSystemInterface = new HotelSystemInterface();
	}

	private Login login;
	private MenuRecepcionista menuRecepcionista;

	public HotelSystemInterface() throws IOException {
		configLogin();
		configMenuRecepcionista("Juan Rojas", "Menu Principal");
		componentsFrame();
	}

	private void configLogin() {
		// ACTIONS LISTENERS
		Function<Login, ActionListener> loginAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Boolean authenticated = login(
						panel.getUserInput().getInput().getText(),
						panel.getPasswordInput().getInput().getText()
					);
					if (!authenticated)
						panel.displayUnauthorizedWarning();
					else
						configMainFrame(configMenuRecepcionista("Juan Rojas", "Menu Principal"));
				}
			};
		};
		Function<Login, ActionListener> signUpAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("funciono");
				}
			};
		};
		this.login = new Login(loginAction, signUpAction);

		// LAYOUT CONFIGURATION
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(login);

		// SETTINGS
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(600, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Hotel System Management");
		this.setResizable(false);
	}

	private Boolean login(String user, String password) {
		return true;
	}

	private void configMainFrame(JPanel panel) {
		// CLEAN
		this.setContentPane(panel);

		// LAYOUT CONFIGURATION
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// SETTINGS
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(1416, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Hotel System Management");
		this.setResizable(false);
	}

	private MenuRecepcionista configMenuRecepcionista(String user, String title) {
		this.menuRecepcionista = new MenuRecepcionista(user, title);
		return this.menuRecepcionista;
	}

	private void componentsFrame() {

	}
}
