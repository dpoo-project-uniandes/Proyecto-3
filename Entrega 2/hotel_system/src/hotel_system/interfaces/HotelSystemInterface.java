package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.interfaces.admin.MenuAdministrador;
import hotel_system.interfaces.admin.MenuCargaAdministrador;
import hotel_system.interfaces.recepcionista.BookingManagement;
import hotel_system.interfaces.recepcionista.MenuConsumible;
import hotel_system.interfaces.recepcionista.MenuRecepcionista;
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
	private MenuAdministrador menuAdmin;
	private MenuCargaAdministrador menuCargaAdministrador;
	private MenuConsumible menuConsumible;
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
					Usuario authenticated = getUser("admin", "admin");
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
		else {
			configMenuAdmin(user);
		}
	}
	
	// Funciones, configs y paneles para admin
	private void configMenuAdmin(String user) {
		// ACTIONS LISTENERS
		Function<MenuAdministrador, ActionListener> loadAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configLoadingManagement(user);
				}
			};
		};
		Function<MenuAdministrador, ActionListener> modifyAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configModifingManagement(user);

				}
			};
		};
		
		Function<MenuAdministrador, ActionListener> searchAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configSearchingManagement(user);
				}
			};
		};
		
		// INITIALIZE
		this.menuAdmin = new MenuAdministrador(user, loadAction, modifyAction, searchAction);
		configMainFrame(this.menuAdmin);
		
	}
	
	// Función carga de datos admin
	private void configLoadingManagement(String user) {
		Function<MenuCargaAdministrador, ActionListener> RestauranteAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		Function<MenuCargaAdministrador, ActionListener> SpaAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		
		Function<MenuCargaAdministrador, ActionListener> HabitacionesAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		
		// INITIALIZE
		this.menuCargaAdministrador = new MenuCargaAdministrador(user, RestauranteAction, SpaAction, HabitacionesAction);
		configMainFrame(this.menuCargaAdministrador);
	}
	
	private void configModifingManagement(String user) {
		// TODO Auto-generated method stub
		
	}
	private void configSearchingManagement(String user) {
		// TODO Auto-generated method stub
		
	}
	
	// Funciones, configs y paneles para recepcionista
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
				public void actionPerformed(ActionEvent e) {
					configStayingManagement(user);
				}	
			};
		};
		Function<MenuRecepcionista, ActionListener> consumiblesAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configConsumingManagement(user);
				}		
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
		this.bookingManagement = new BookingManagement(user, findAction, deleteAction, updateAction);
		configMainFrame(bookingManagement);
	}
	
	private void configStayingManagement(String user) {
		// TODO Auto-generated method stub	
	}
	
	private void configConsumingManagement(String user) {
		Function<MenuConsumible, ActionListener> ProductosAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		Function<MenuConsumible, ActionListener> ServiciosAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {}
			};
		};
		
		
		// INITIALIZE
		this.menuConsumible = new MenuConsumible(user, ProductosAction, ServiciosAction);
		configMainFrame(this.menuConsumible);
	}
	
		
		
		
		
}
