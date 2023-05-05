package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
<<<<<<< Updated upstream
=======
import javax.swing.JTextField;
>>>>>>> Stashed changes

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.models.Usuario;

public class HotelSystemInterface extends JFrame {

	public static void main(String[] args) throws IOException  {
		HotelSystemInterface HotelSystemInterface = new HotelSystemInterface();
<<<<<<< Updated upstream
	}

=======
	}  
	private HotelManagementSystem pms;
	private MenuAdmin menuAdmin;
>>>>>>> Stashed changes
	private Login login;
	private MenuRecepcionista menuRecepcionista;
	private BookingManagement bookingManagement;
	private String user;

	public HotelSystemInterface() throws IOException {
<<<<<<< Updated upstream
		this.user = "Juan Rojas";
=======
		this.pms = new HotelManagementSystem();
>>>>>>> Stashed changes
		configLogin();
	}
<<<<<<< Updated upstream

	private void configLogin() {
=======
	 
	private void configSingUp(JPanel panel) {
		// LAYOUT CONFIGURATION
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(menuAdmin);
		  
		// SETTINGS
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(600, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Hotel System Management");
		this.setResizable(false);
	
		
	}

	private void configAdminOptions(JPanel panel) {
		// LAYOUT CONFIGURATION
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(panel);
		  
		// SETTINGS
		getContentPane().setBackground(Color.WHITE);
		setSize(600, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Hotel System Management");
		setResizable(false);
		
	}

	private void configRecepcionistaOptions() {
		// TODO Auto-generated method stub
		
	}

	private void configLogin() {  
>>>>>>> Stashed changes
		// ACTIONS LISTENERS
		Function<Login, ActionListener> loginAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
<<<<<<< Updated upstream
					Boolean authenticated = login(
						panel.getUserInput().getInput().getText(),
						panel.getPasswordInput().getInput().getText()
					);
					if (!authenticated)
						panel.displayUnauthorizedWarning();
					else
						configMenuRecepcionista(user);
=======
					Usuario authenticated = getLogin(
						panel.getUserInput().getInput().getText(), 
						panel.getPasswordInput().getInput().getText()
					);
					if (authenticated == null) 
						panel.displayUnauthorizedWarning();
					else {
						String nombreUsuario = authenticated.getLogin();
						String rol = authenticated.getRol().toString();
						if (rol == "RECEPCIONISTA") {
							
						}else {
							MenuAdmin adminn = new MenuAdmin(nombreUsuario);
							configAdminOptions(adminn);
							remove(login);
							setContentPane(adminn);
							revalidate();
							repaint();
						}
						
					}
>>>>>>> Stashed changes
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
		
		// INITIALIZE
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
<<<<<<< Updated upstream

	private Boolean login(String user, String password) {
		return true;
=======
	
	private Usuario getLogin(String user, String password) {
		return pms.getUsuario(user, password);
>>>>>>> Stashed changes
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

	private void configMenuRecepcionista(String user) {
		// ACTIONS LISTENERS
		Function<MenuRecepcionista, ActionListener> bookingAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configBookingManagement(user);
				}
			};
		};
		Function<MenuRecepcionista, ActionListener> staysAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		
		Function<MenuRecepcionista, ActionListener> consumiblesAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		
		// INITIALIZE
		this.menuRecepcionista = new MenuRecepcionista(user, bookingAction, staysAction, consumiblesAction);
		configMainFrame(this.menuRecepcionista);
	}
	
	private void configBookingManagement(String user) {
		// ACTIONS LISTENERS
		Function<Finder, ActionListener> findAction = (btn) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("btn buscar presionado");
				}
			};
		};
		
		// INITIALIZE
		this.bookingManagement = new BookingManagement(user, findAction);
		configMainFrame(bookingManagement);
	}
}
