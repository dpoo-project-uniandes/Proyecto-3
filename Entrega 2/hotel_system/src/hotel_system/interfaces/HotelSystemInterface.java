package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JTextField;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.models.Rol;
import hotel_system.models.Usuario;

public class HotelSystemInterface extends JFrame {

	public static void main(String[] args) throws IOException  {
		HotelSystemInterface HotelSystemInterface = new HotelSystemInterface();
	}  

	private HotelManagementSystem pms;
	private Login login;
	private MenuRecepcionista menuRecepcionista;
	private BookingManagement bookingManagement;
	private String user;

	public HotelSystemInterface() throws IOException {
		this.user = "";
		this.pms = new HotelManagementSystem();
		configLogin();
	}

	private void configLogin() {  
		// ACTIONS LISTENERS
		Function<Login, ActionListener> loginAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					Usuario authenticated = getUser(
//							panel.getUserInput().getInput().getText(), 
//							panel.getPasswordInput().getInput().getText()
//					);
					Usuario authenticated = getUser(
							"juan", 
							"juan"
					);
					if (authenticated == null) 
						panel.displayUnauthorizedWarning();
					else 
						login(authenticated);	
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

	private Usuario getUser(String user, String password) {
		return pms.userLogin(user, password);
	}
	
	private void login(Usuario usuario) {
		this.user = usuario.getAlias();
		if (usuario.getRol() == Rol.RECEPCIONISTA) {
			configMenuRecepcionista(user);
		}
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
