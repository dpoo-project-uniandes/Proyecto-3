package hotel_system.interfaces.usuarios;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.interfaces.CreditCardPay;
import hotel_system.interfaces.Login;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.Registrarse;
import hotel_system.interfaces.components.Facturador;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.recepcionista.FormRoomsData;
import hotel_system.models.Factura;
import hotel_system.models.PasarelaPago;
import hotel_system.models.Reserva;
import hotel_system.models.Rol;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Usuario;
import hotel_system.utils.Utils;
import services.SecValidation;

public class HotelSystemInterfaceUsuarios extends JFrame {

	public static void main(String[] args) throws IOException  {
		HotelSystemInterfaceUsuarios HotelSystemInterface = new HotelSystemInterfaceUsuarios();
	} 
	
	private HotelManagementSystem pms;
	private Login login;
	private Registrarse registrarse;
	private BookingManagementUsuario bookingManagementUsuario;
	private String user;
	
	private HeaderButtonsActions headerButtonsActions;
	
	public HotelSystemInterfaceUsuarios() throws IOException {
		this.user = "My User";
		this.pms = new HotelManagementSystem();
		configHeaderButtonsActions();
		configLogin();
	}
	
	// ============================================================================================================================================================================
	// HEADER ACTIONS
	// ============================================================================================================================================================================
	
	private void configHeaderButtonsActions() {
		// ============================================================================================================================================================================
		// ACTIONS LISTENERS DEL HEADER
		// ============================================================================================================================================================================
		Function<MainHeader, ActionListener> logout = (header) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					configLogin();
				}
			};
		};
		
		this.headerButtonsActions = new HeaderButtonsActions(null, null, null, logout);
	}
	
	// ================================================================================================================================================================================
	// FRAME GRANDE
	// ================================================================================================================================================================================
	
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
					else {
						user = authenticated.getAlias();
						configBookingManagementUsuario();	
					}
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
	    String[] roles = new String[]{Rol.USUARIO.toString()};
	    this.registrarse = new Registrarse(roles, registerAction);
	    configSmallFrame(registrarse);
	}
	
	// ================================================================================================================================================================================
	
	// ================================================================================================================================================================================
	// RESERVAS
	// ================================================================================================================================================================================
	
	private void configBookingManagementUsuario() {
		// ============================================================================================================================================================================
		// ACTION LISTENERS DE RESERVAS
		// ============================================================================================================================================================================
		Function<BookingManagementUsuario, ActionListener> payAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Reserva reserva = panel.getBookingInjected();
						Factura factura = pms.facturarReserva(reserva);
						configCreditCardPay(factura, bookingManagementUsuario);
						panel.injectData(reserva);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Lo sentimos no se pudo facturar la estadia, intente nuevamente\n" + e1.getMessage());
					}
				}
			};
		};
		Function<BookingManagementUsuario, ActionListener> deleteAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						pms.eliminarReserva(panel.getBookingInjected().getNumero());
						panel.withoutResults();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			};
		};
		Function<BookingManagementUsuario, ActionListener> cancelAction = (panel) -> {
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
		Function<BookingManagementUsuario, ActionListener> updateAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Reserva reserva = panel.getBookingInjected();
					panel.formNewBookingInjectData(reserva);
				}
			};
		
		};
		Function<BookingManagementUsuario, ActionListener> createAction = (panel) -> {
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
		List<TipoHabitacion> tiposHabitacion = new ArrayList<>(pms.getOpcionesHabitacion().values());
		FormRoomsData formRoomsData = new FormRoomsData(tiposHabitacion);
		this.bookingManagementUsuario = new BookingManagementUsuario(
				user,
				this,
				formRoomsData,
				headerButtonsActions,
				createAction,
				deleteAction,
				updateAction,
				cancelAction,
				payAction
		);
		configMainFrame(bookingManagementUsuario);
	}
	
	// ================================================================================================================================================================================
	
	// ================================================================================================================================================================================
	// PANEL DE PAGO CON TARJETA CREDITO
	// ================================================================================================================================================================================
	
	private void configCreditCardPay(Factura factura,Facturador owner) {
		Function<CreditCardPay, ActionListener> payAction = (panel) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Map<String, String> data = panel.getDataMap();
						Factura facturaPagada = pms.procesarPagoConTarjetaCredito(
								factura, 
								data.get("pasarela"),
								data.get("owner"),
								Long.parseLong(data.get("card-number")),
								Integer.parseInt(data.get("cvv")),
								data.get("expiration")
						); 
						owner.injectFactura(facturaPagada);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Lo sentimos el pago no ha sido procesado, intente nuevamente\n" + e1.getMessage());
					}
				}
			};
		};
		
		Map<String, String> pasarelas = pms.getPasarelas().values().stream()
				.map(pasarela -> pasarela).collect(Collectors.toMap(PasarelaPago::getName, PasarelaPago::getLogo));
		CreditCardPay pay = new CreditCardPay(this, pasarelas, payAction);
	}
}
