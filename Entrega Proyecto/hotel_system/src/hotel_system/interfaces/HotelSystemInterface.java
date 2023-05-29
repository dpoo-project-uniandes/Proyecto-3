package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.interfaces.admin.MenuAdministrador;
import hotel_system.interfaces.admin.MenuCargaAdministrador;
import hotel_system.interfaces.admin.MenuModificarAdmin;
import hotel_system.interfaces.recepcionista.BookingManagement;
import hotel_system.interfaces.recepcionista.FormRoomsData;
import hotel_system.interfaces.recepcionista.MenuConsumible;
import hotel_system.interfaces.recepcionista.MenuProductosServicios;
import hotel_system.interfaces.recepcionista.MenuRecepcionista;
import hotel_system.interfaces.recepcionista.MenuServicios;
import hotel_system.models.Producto;
import hotel_system.models.Reserva;
import hotel_system.models.Rol;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Usuario;
import hotel_system.utils.Utils;
import services.Dupla;
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
	private MenuModificarAdmin menuModificarAdmin;
	private MenuServicios menuServicios;
	private MenuProductosServicios menuProductosServicios;
	
	public HotelSystemInterface() throws IOException {
		this.user = "Juan Rojas";
		this.pms = new HotelManagementSystem();
//		configLogin();
		configBookingManagement(user);
	}
	
	private void configMainFrame(JPanel panel) {
		// CLEAN
		this.setContentPane(panel);

		// LAYOUT CONFIGURATION
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// SETTINGS
		this.setTitle("Hotel System Management");
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(1530, 900);
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
					Usuario authenticated = getUser(
							panel.getUserInput().getInput().getText(), 
							panel.getPasswordInput().getInput().getText()
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
				public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Datos del restaurante cargados con éxito");
				}
			};
		};
		Function<MenuCargaAdministrador, ActionListener> SpaAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Datos del spa cargados con éxito");
				}
			};
		};
		
		Function<MenuCargaAdministrador, ActionListener> HabitacionesAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Datos de las habitaciones cargados con éxito");
				}
			};
		};
		
		// INITIALIZE
		this.menuCargaAdministrador = new MenuCargaAdministrador(user, RestauranteAction, SpaAction, HabitacionesAction);
		configMainFrame(this.menuCargaAdministrador);
	}
	
	private void configModifingManagement(String user) {
		// ACTIONS LISTENERS
		Function<Finder, ActionListener> findAction = (finder) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = finder.getValue();
					Dupla<Producto, String> dupla = pms.getProductoByID(id);
					Producto producto = dupla.getPrimero();
					String tipo = dupla.getSegundo();
					if (dupla == null)
						menuModificarAdmin.withoutResults();
					else
						menuModificarAdmin.setTipoProducto(tipo);
						menuModificarAdmin.injectData(id);
				}
			};
		};
		Function<Finder, ActionListener> deleteAction = (btn) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Producto producto = menuModificarAdmin.getProductoActual();
					pms.eliminarProducto(producto, menuModificarAdmin.getTipoProducto());
				}
			};
		};
		Function<Finder, ActionListener> updateAction = (btn) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO
					
				}
			};
		
		};
		Function<Finder, ActionListener> createAction = (btn) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						//pms.nuevoProducto(,"");
					} catch (Exception e1) {
						e1.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Se produjo un error creando la reserva");

					}
				}
			};
		
		};
				
		// INITIALIZE
		this.menuModificarAdmin = new MenuModificarAdmin(user, findAction, deleteAction ,updateAction, createAction);
		configMainFrame(this.menuModificarAdmin);
		
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
	
	// ================================================================================================================================================================================
	// VENTANA DE RESERVAS
	// ================================================================================================================================================================================
	
	private void configBookingManagement(String user) {
		// ============================================================================================================================================================================
		// ACTION LISTENERS DE RESERVAS
		// ============================================================================================================================================================================
		Function<Finder, ActionListener> findAction = (finder) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Reserva booking;
					String text = finder.getValue();
					booking = pms.getReservaByDNI(text);
					if (booking == null) 
						booking = pms.getReservaById(Long.parseLong(text));
					if (booking == null)
						bookingManagement.withoutResults();
					else
						bookingManagement.injectData(booking);
				}
			};
		};
		Function<BookingManagement, ActionListener> deleteAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						pms.eliminarReserva(panel.getBookingInjected().getNumero());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			};
		};
		Function<BookingManagement, ActionListener> cancelAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						pms.cancelarReserva(panel.getBookingInjected().getNumero());
					} catch (Exception e1) {
						e1.printStackTrace();
					}	
				}
			};
		};
		Function<BookingManagement, ActionListener> updateAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Reserva reserva = panel.getBookingInjected();
					panel.formNewBookingInjectData(reserva);
				}
			};
		
		};
		Function<BookingManagement, ActionListener> createAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Map<String, String> data = panel.getDataMap();
						Map<String, Integer> rooms = panel.getDataRoomsMap();
						pms.reservar(
								data.get("titular"),
								data.get("email"),
								data.get("dni"),
								data.get("telefono"), 
								Integer.parseInt(data.get("edad")),
								Integer.parseInt(data.get("huespedes")),
								Utils.stringToDate(data.get("llegada")),
								Utils.stringToDate(data.get("salida")),
								rooms
						);
						panel.injectData(pms.getReservaByDNI(data.get("dni")));
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (Exception e2) {
						e2.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Se produjo un error creando la reserva");
					} 
				}
			};
		
		};
		
		// ============================================================================================================================================================================
		// INICIALIZACION DEL PANEL DE RESERVAS
		// ============================================================================================================================================================================
		List<TipoHabitacion> tiposHabitacion = pms.getOpcionesHabitacion().values().stream().toList();
		FormRoomsData formRoomsData = new FormRoomsData(tiposHabitacion);
		this.bookingManagement = new BookingManagement(
				user,
				formRoomsData,
				findAction, 
				createAction,
				deleteAction, 
				updateAction,
				cancelAction
		);
		
		configMainFrame(bookingManagement);
	}
	
	// ================================================================================================================================================================================
	// VENTANA DE ...
	// ================================================================================================================================================================================
	
	private void configStayingManagement(String user) {
		// TODO Auto-generated method stub	
	}
	
	private void configConsumingManagement(String user) {
		Function<MenuConsumible, ActionListener> ProductosAction = (panel) -> {
			return new ActionListener() {
				private MenuProductosServicios menuProductosServicios;

				@Override
				public void actionPerformed(ActionEvent e) {
					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
					this.menuProductosServicios = new MenuProductosServicios(user,generateAction,payNowAction,payLaterAction,"data/productos.csv");
					configMainFrame(this.menuProductosServicios);
				}
			};
		};
		Function<MenuConsumible, ActionListener> ServiciosAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configServices(user);
				}
			};
		};
		// INITIALIZE
		this.menuConsumible = new MenuConsumible(user, ProductosAction, ServiciosAction);
		configMainFrame(this.menuConsumible);
	}
	
	// Config Servicios
	private void configServices(String user) {
		Function<MenuServicios, ActionListener> restauranteAction = (panel) -> {
			return new ActionListener() {
				private MenuProductosServicios menuProductosServicios;

				@Override
				public void actionPerformed(ActionEvent e) {
					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
					this.menuProductosServicios = new MenuProductosServicios(user,generateAction,payNowAction,payLaterAction,"data/productos_restaurante.csv");
					configMainFrame(this.menuProductosServicios);
				}
			};
		};
		Function<MenuServicios, ActionListener> spaAction = (panel) -> {
			return new ActionListener() {
				private MenuProductosServicios menuProductosServicios;

				@Override
				public void actionPerformed(ActionEvent e) {
					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
					this.menuProductosServicios = new MenuProductosServicios(user,generateAction,payNowAction,payLaterAction,"data/productos_spa.csv");
					configMainFrame(this.menuProductosServicios);
				}
			};
		};
		
		Function<MenuServicios, ActionListener> alojamientoAction = (panel) -> {
			return new ActionListener() {
				private MenuProductosServicios menuProductosServicios;

				@Override
				public void actionPerformed(ActionEvent e) {
					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
					this.menuProductosServicios = new MenuProductosServicios(user,generateAction,payNowAction,payLaterAction,"data/reservas_habitaciones.csv");
					configMainFrame(this.menuProductosServicios);
				}
			};
		};
		
		// INITIALIZE
		this.menuServicios = new MenuServicios(user, restauranteAction, spaAction, alojamientoAction);
		configMainFrame(this.menuServicios);
	}
	
		
}
