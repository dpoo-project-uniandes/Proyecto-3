package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.models.Reserva;
import hotel_system.models.Rol;
import hotel_system.models.Usuario;
import services.SecValidation;

public class HotelSystemInterface extends JFrame {
	
	public static void main(String[] args) throws IOException  {
		HotelSystemInterface HotelSystemInterface = new HotelSystemInterface();
	} 
	 
	private HotelManagementSystem pms;
	private Login login;
	private Registrarse registrarse;
	private MenuRecepcionista menuRecepcionista;
	private BookingManagement bookingManagement;
	private String user;
	
	public HotelSystemInterface() throws IOException {
		this.user = "Juan Rojas";
		this.pms = new HotelManagementSystem();
		configLogin();
	}

	private void configMainFrame(JPanel panel) {
		// CLEAN
		this.setContentPane(panel);

		// LAYOUT CONFIGURATION
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// SETTINGS
		this.setTitle("Hotel System Management");
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(1416, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(true);
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
					Usuario authenticated = getUser("juan", "juan");
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
					configSignUp();
				}
			};
		};
		
		// INITIALIZE
		this.login = new Login(loginAction, signUpAction);
		
		// LAYOUT CONFIGURATION & CLEAN
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setContentPane(this.login);

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
	
	private void configSignUp() {
		// ACTION LISTENERS
	    Function<Registrarse, ActionListener> registerAction = (panel) -> {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String password = panel.getPasswordInput().getInput().getText();
	                if (!SecValidation.checkPassword(password)) {
	                    panel.displayPasswordRequirementsWarning();
	                } else {
	                    try {
							pms.registrarUsuario(panel.getUserInput().getInput().getText(), 
									password, Rol.valueOf(panel.getRolSelect().optionSelected()) );
						} catch (Exception e1) {
							e1.printStackTrace();
						}
	                    configLogin();
	                }
	            }  
	        };
	    };
		
		// INITIALIZE
	    String[] roles = List.of(Rol.values()).stream().map(r -> r.toString()).toArray(String[]::new);
	    this.registrarse = new Registrarse(roles, registerAction);
		
		// LAYOUT CONFIGURATION & CLEAN
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	    this.setContentPane(this.registrarse);
		  
		// SETTINGS
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(600, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Hotel System Management");
		this.setResizable(false);
	}
	
	private void login(Usuario usuario) {
		this.user = usuario.getAlias();
		if (usuario.getRol() == Rol.RECEPCIONISTA) {
			configMenuRecepcionista(user);
		}
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
		Function<Finder, ActionListener> findAction = (finder) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Reserva booking;
					String text = finder.getInput().getInput().getText();
					System.out.println(text);
					booking = pms.getReservaByDNI(text);
					System.out.println(booking);
					if (booking == null) 
						booking = pms.getReservaById(text);
					if (booking == null)
						bookingManagement.withoutResults();
					else
						bookingManagement.injectData(booking);
				}
			};
		};
		Function<Finder, ActionListener> deleteAction = (btn) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("btn buscar presionado");
				}
			};
		};
		Function<Finder, ActionListener> updateAction = (btn) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("btn buscar presionado");
				}
			};
		};
		
		// INITIALIZE
		List<String> headers = Arrays.asList("Habitacion", "Capacidad", "Balcon", "Vista", "Cocina", "Precio");
		List<List<String>> data = pms.getOpcionesHabitacion().stream().map(opt -> Arrays.asList(
				opt.getAlias(), 
				opt.getCapacidad().toString(), 
				opt.getConBalcon().toString(), 
				opt.getConVista().toString(), 
				opt.getConCocina().toString(), 
				opt.getPrecio().toString()
			)
		).toList();
		this.bookingManagement = new BookingManagement(
				user,
				headers,
				data,
				findAction, 
				deleteAction, 
				updateAction
		);
		configMainFrame(bookingManagement);
	}
}


