package hotel_system.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.interfaces.admin.MenuAdministrador;
import hotel_system.interfaces.admin.MenuCargaAdministrador;
import hotel_system.interfaces.admin.MenuModificarAdmin;
import hotel_system.interfaces.components.FormDataTable;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.recepcionista.BookingManagement;
import hotel_system.interfaces.recepcionista.EstadiasManagement;
import hotel_system.interfaces.recepcionista.FormRoomsData;
import hotel_system.interfaces.recepcionista.GuestsData;
import hotel_system.interfaces.recepcionista.MenuConsumible;
import hotel_system.interfaces.recepcionista.MenuProductosServicios;
import hotel_system.interfaces.recepcionista.MenuRecepcionista;
import hotel_system.interfaces.recepcionista.MenuServicios;
import hotel_system.interfaces.recepcionista.ProductsData;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Huesped;
import hotel_system.models.Producto;
import hotel_system.models.Reserva;
import hotel_system.models.Rol;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Usuario;
import hotel_system.utils.Utils;
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
	private EstadiasManagement estadiasManagement;
	private MenuModificarAdmin menuModificarAdmin;
	private MenuServicios menuServicios;
	private MenuProductosServicios menuProductosServicios;
	private String user;
	
	// STACK DE VENTANAS
	private Stack<JPanel> framesStack;
	private HeaderButtonsActions headerButtonsActions;
	private JPanel currentHome;
	
	
	public HotelSystemInterface() throws IOException {
		this.user = "Juan Rojas";
		this.pms = new HotelManagementSystem();
		configHeaderButtonsActions();
//		configMenuRecepcionista();
		configConsumingManagement();
	}
	
	// ============================================================================================================================================================================
	// HEADER ACTIONS
	// ============================================================================================================================================================================
	
	private void configHeaderButtonsActions() {
		this.framesStack = new Stack<>();
		// ============================================================================================================================================================================
		// ACTIONS LISTENERS DEL HEADER
		// ============================================================================================================================================================================
		Function<MainHeader, ActionListener> home = (header) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configMainFrame(currentHome);
				}
			};
		};
		Function<MainHeader, ActionListener> back = (header) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					framesStack.pop();
					configMainFrame(framesStack.pop());
				}
			};
		};
		
		// ============================================================================================================================================================================
		// INICIALIZACION
		// ============================================================================================================================================================================
		this.headerButtonsActions = new HeaderButtonsActions(home, back, home);
	}
	
	// ================================================================================================================================================================================
	// FRAME GRANDE
	// ================================================================================================================================================================================
	
	private void configMainFrame(JPanel panel) {
		// STACK
		this.framesStack.push(panel);
		
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
	
	// ================================================================================================================================================================================
	// FRAME PEQUENO
	// ================================================================================================================================================================================
	
	private void configSmallFrame(JPanel panel) {
		// CLEAN
		this.setContentPane(panel);

		// LAYOUT CONFIGURATION & CLEAN
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// SETTINGS
		this.getContentPane().setBackground(Color.WHITE);
		this.setSize(600, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Hotel System Management");
		this.setResizable(false);
	} 
	
	// ================================================================================================================================================================================
	// LOGIN
	// ================================================================================================================================================================================

	private void configLogin() {  
		// ============================================================================================================================================================================
		// ACTION LISTENERS DEL LOGIN
		// ============================================================================================================================================================================
		Function<Login, ActionListener> loginAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Usuario authenticated = pms.userLogin(
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
		
		// ============================================================================================================================================================================
		// INICIALIZACION DEL LOGIN
		// ============================================================================================================================================================================
		this.login = new Login(loginAction, signUpAction);
		configSmallFrame(login);
	}
	
	private void login(Usuario usuario) {
		this.user = usuario.getAlias();
		if (usuario.getRol() == Rol.RECEPCIONISTA) { 
			configMenuRecepcionista();
		}
		else {
//			configMenuAdmin(user);
		}
	}
	
	// ================================================================================================================================================================================
	
	// ================================================================================================================================================================================
	// SIGN UP
	// ================================================================================================================================================================================
	
	private void configSignUp() {
		// ============================================================================================================================================================================
		// ACTION LISTENERS DE SIGN UP
		// ============================================================================================================================================================================
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
		
		// ============================================================================================================================================================================
		// INICIALIZACION DEL SIGN UP
		// ============================================================================================================================================================================
	    String[] roles = List.of(Rol.values()).stream().map(r -> r.toString()).toArray(String[]::new);
	    this.registrarse = new Registrarse(roles, registerAction);
	    configSmallFrame(registrarse);
	}
	
	// ================================================================================================================================================================================
	
	// ================================================================================================================================================================================
	// MENUS DE LA APLICACIONES
	// ================================================================================================================================================================================
	
	// ================================================================================================================================================================================
	// MENU ADMINISTRADOR
	// ================================================================================================================================================================================
	
//	private void configMenuAdmin(String user) {
		// ============================================================================================================================================================================
		// ACTION LISTENERS DEL MENU ADMINISTRADOR
		// ============================================================================================================================================================================
//		Function<MenuAdministrador, ActionListener> loadAction = (panel) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					configLoadingManagement(user);
//				}
//			};
//		};
//		Function<MenuAdministrador, ActionListener> modifyAction = (panel) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					configModifingManagement(user);
//
//				}
//			};
//		};
//		
//		Function<MenuAdministrador, ActionListener> searchAction = (panel) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					configSearchingManagement(user);
//				}
//			};
//		};
//		
//		// INITIALIZE
//		this.menuAdmin = new MenuAdministrador(user, headerButtonsActions.withButtons(), loadAction, modifyAction, searchAction);
//		configMainFrame(this.menuAdmin);
//		
//	}
	
	// ================================================================================================================================================================================
	// CARGA DE DATOS DESDE EL PANEL DE ADMINISTRACION
	// ================================================================================================================================================================================
	
//	private void configLoadingManagement(String user) {
//		Function<MenuCargaAdministrador, ActionListener> RestauranteAction = (panel) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//                    JOptionPane.showMessageDialog(null, "Datos del restaurante cargados con éxito");
//				}
//			};
//		};
//		Function<MenuCargaAdministrador, ActionListener> SpaAction = (panel) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//                    JOptionPane.showMessageDialog(null, "Datos del spa cargados con éxito");
//				}
//			};
//		};
//		
//		Function<MenuCargaAdministrador, ActionListener> HabitacionesAction = (panel) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//                    JOptionPane.showMessageDialog(null, "Datos de las habitaciones cargados con éxito");
//				}
//			};
//		};
//		
//		// INITIALIZE
//		this.menuCargaAdministrador = new MenuCargaAdministrador(user, headerButtonsActions.withButtons(), RestauranteAction, SpaAction, HabitacionesAction);
//		configMainFrame(this.menuCargaAdministrador);
//	}
	
	// ================================================================================================================================================================================
	// ....
	// ================================================================================================================================================================================
	
//	private void configModifingManagement(String user) {
//		// ACTIONS LISTENERS
//		Function<Finder, ActionListener> findAction = (finder) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					String id = finder.getValue();
//					Dupla<Producto, String> dupla = pms.getProductoByID(id);
//					Producto producto = dupla.getPrimero();
//					String tipo = dupla.getSegundo();
//					if (dupla == null)
//						menuModificarAdmin.withoutResults();
//					else
//						menuModificarAdmin.setTipoProducto(tipo);
//						menuModificarAdmin.injectData(id);
//				}
//			};
//		};
//		Function<Finder, ActionListener> deleteAction = (btn) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					Producto producto = menuModificarAdmin.getProductoActual();
//					pms.eliminarProducto(producto, menuModificarAdmin.getTipoProducto());
//				}
//			};
//		};
//		Function<Finder, ActionListener> updateAction = (btn) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					//TODO
//					
//				}
//			};
//		
//		};
//		Function<Finder, ActionListener> createAction = (btn) -> {
//			return new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					try {
//						//pms.nuevoProducto(,"");
//					} catch (Exception e1) {
//						e1.printStackTrace();
//	                    JOptionPane.showMessageDialog(null, "Se produjo un error creando la reserva");
//
//					}
//				}
//			};
//		
//		};
//				
//		// INITIALIZE
//		this.menuModificarAdmin = new MenuModificarAdmin(user, headerButtonsActions.withButtons(), findAction, deleteAction ,updateAction, createAction);
//		configMainFrame(this.menuModificarAdmin);
//		
//	}
	
	// ================================================================================================================================================================================
	// MENU RECEPCIONISTA
	// ================================================================================================================================================================================
	private void configMenuRecepcionista() {
		// ============================================================================================================================================================================
		// ACTION LISTENERS DEL MENU RECEPCIONISTA
		// ============================================================================================================================================================================
		Function<MenuRecepcionista, ActionListener> bookingAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configBookingManagement();
				}
			};
		};
		Function<MenuRecepcionista, ActionListener> staysAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configEstadiasManagement();
				}	
			};
		};
		Function<MenuRecepcionista, ActionListener> consumiblesAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configConsumingManagement();
				}		
			};
		};
		
		// ================================================================================================================================================================================
		// INICIALIZACION DEL MENU RECEPCIONISTA
		// ================================================================================================================================================================================
		this.menuRecepcionista = new MenuRecepcionista(user, headerButtonsActions.withoutButtons(), bookingAction, staysAction, consumiblesAction);
		this.currentHome = this.menuRecepcionista;
		configMainFrame(menuRecepcionista);
	}
	
	// ================================================================================================================================================================================
	// RESERVAS
	// ================================================================================================================================================================================
	
	private void configBookingManagement() {
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
						String id = data.get("id");
						pms.reservar(
								id != "0" ?  Long.parseLong(id) : null,
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
				headerButtonsActions.withButtons(),
				findAction, 
				createAction,
				deleteAction, 
				updateAction,
				cancelAction
		);
		configMainFrame(bookingManagement);
	}
	
	// ================================================================================================================================================================================
	
	// ================================================================================================================================================================================
	// ESTADIAS
	// ================================================================================================================================================================================
	
	private void configEstadiasManagement() {
		// ============================================================================================================================================================================
		// ACTION LISTENERS DE ESTADIAS
		// ============================================================================================================================================================================
		Function<Finder, ActionListener> findAction = (finder) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estadia estadia;
					String text = finder.getValue();
					estadia = pms.getEstadiaByDNI(text);
					if (estadia == null) 
						estadia = pms.getEstadiaById(Long.parseLong(text));
					if (estadia == null)
						estadiasManagement.withoutResults();
					else
						estadiasManagement.injectData(estadia);
				}
			};
		};
		Function<EstadiasManagement, ActionListener> guestsAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estadia estadia = panel.getEstadiaInjected();
					panel.formNewEstadiaInjectData(estadia);
				}
			};
		};
		Function<EstadiasManagement, ActionListener> billingAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				}
			};
		};
		Function<EstadiasManagement, ActionListener> updateAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Estadia estadia = panel.getEstadiaInjected();
					panel.formNewEstadiaInjectData(estadia);
				}
			};
		
		};
		Function<EstadiasManagement, ActionListener> createAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Estadia estadia = pms.iniciarEstadia(panel.bookingId(), panel.guests());
						panel.injectData(estadia);
					} catch (Exception e1) {
						e1.printStackTrace();
	                    JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			};
		
		};
		
		// ============================================================================================================================================================================
		// INICIALIZACION DEL PANEL DE ESTADIAS
		// ============================================================================================================================================================================
		FormDataTable<Huesped> guestsData = new GuestsData(new ArrayList<>(), null);
		this.estadiasManagement = new EstadiasManagement(
				user, 
				guestsData,
				headerButtonsActions.withButtons(),
				findAction, 
				createAction, 
				updateAction, 
				guestsAction, 
				billingAction
		);
		configMainFrame(estadiasManagement);
	}
	
	// ================================================================================================================================================================================
	// MENUS DE CONSUMIBLES
	// ================================================================================================================================================================================
	
	private void configConsumingManagement() {
		Function<MenuConsumible, ActionListener> ProductosAction = (panel) -> {
			return new ActionListener() {
				private MenuProductosServicios menuProductosServicios;

				@Override
				public void actionPerformed(ActionEvent e) {
					configProductsManagement();
				}
			};
		};
		Function<MenuConsumible, ActionListener> ServiciosAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					configServices(user);
				}
			};
		};
		// INITIALIZE
		this.menuConsumible = new MenuConsumible(user, headerButtonsActions.withButtons(), ProductosAction, ServiciosAction);
		configMainFrame(this.menuConsumible);
	}
	
	private void configProductsManagement() {
		Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
        Function<MenuProductosServicios, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
        Function<MenuProductosServicios, ActionListener> payLaterAction = (panel) -> { 
        	return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Integer habitacion = Integer.parseInt(panel.getHabitacion());
					Map<String, Integer> productsSelected = panel.getProductsMap();
					Map<Long, Integer> products = new HashMap<>();
					productsSelected.forEach((k, v) -> products.put(Long.parseLong(k), v));
					try {
						Factura factura = pms.facturarAlaHabitacion(habitacion, products);
						panel.injectDataFactura(factura);
					} catch (Exception e1) {
						e1.printStackTrace();
	                    JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			};
        };
        FormDataTable<Producto> data = new ProductsData(new ArrayList<>(pms.getProductos().values()));
		this.menuProductosServicios = new MenuProductosServicios(
				user,
				data,
				headerButtonsActions.withButtons(),
				generateAction,
				payNowAction,
				payLaterAction
		);
		configMainFrame(this.menuProductosServicios);
	}
	
	// ================================================================================================================================================================================
	// MENUS DE SERVICIOS
	// ================================================================================================================================================================================
	
//	// Config Servicios
//	private void configServices(String user) {
//		Function<MenuServicios, ActionListener> restauranteAction = (panel) -> {
//			return new ActionListener() {
//				private MenuProductosServicios menuProductosServicios;
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//					this.menuProductosServicios = new MenuProductosServicios(user,headerButtonsActions.withButtons(),generateAction,payNowAction,payLaterAction,"data/productos_restaurante.csv");
//					configMainFrame(this.menuProductosServicios);
//				}
//			};
//		};
//		Function<MenuServicios, ActionListener> spaAction = (panel) -> {
//			return new ActionListener() {
//				private MenuProductosServicios menuProductosServicios;
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//					this.menuProductosServicios = new MenuProductosServicios(user,headerButtonsActions.withButtons(),generateAction,payNowAction,payLaterAction,"data/productos_spa.csv");
//					configMainFrame(this.menuProductosServicios);
//				}
//			};
//		};
//		
//		Function<MenuServicios, ActionListener> alojamientoAction = (panel) -> {
//			return new ActionListener() {
//				private MenuProductosServicios menuProductosServicios;
//
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					Function<Finder, ActionListener> generateAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//	                Function<Finder, ActionListener> payNowAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//	                Function<Finder, ActionListener> payLaterAction = finder -> { return (ActionEvent event) -> { /* Empty function */ }; };
//					this.menuProductosServicios = new MenuProductosServicios(user,headerButtonsActions.withButtons(),generateAction,payNowAction,payLaterAction,"data/reservas_habitaciones.csv");
//					configMainFrame(this.menuProductosServicios);
//				}
//			};
//		};
//		
//		// INITIALIZE
//		this.menuServicios = new MenuServicios(user, headerButtonsActions.withButtons(), restauranteAction, spaAction, alojamientoAction);
//		configMainFrame(this.menuServicios);
//	}
	
		
}
