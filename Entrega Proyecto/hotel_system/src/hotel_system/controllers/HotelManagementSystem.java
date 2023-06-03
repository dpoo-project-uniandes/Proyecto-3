package hotel_system.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import hotel_system.models.Consumible;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Habitacion;
import hotel_system.models.Hotel;
import hotel_system.models.Huesped;
import hotel_system.models.Pago;
import hotel_system.models.PasarelaPago;
import hotel_system.models.Producto;
import hotel_system.models.Reserva;
import hotel_system.models.Restaurante;
import hotel_system.models.Rol;
import hotel_system.models.Servicio;
import hotel_system.models.Spa;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Usuario;

public class HotelManagementSystem {

	private Map<Integer, Habitacion> inventarioHabitaciones;
	private Map<String, TipoHabitacion> opcionesHabitacion;
	private Map<Long, Reserva> reservas;
	private Map<Long, Estadia> estadias;
	private Map<Long, Producto> inventarioProductos;
	private Map<Long, Servicio> inventarioServicios;
	private Map<String, Usuario> usuarios;
	private Map<String, PasarelaPago> pasarelasPago;
	private Hotel hotel;
	
	private HotelManagementLoaderData loaderData;
	private HotelManagementUsuarios controladorUsuarios;
	private HotelManagementReservas controladorReservas;
	private HotelManagementEstadias controladorEstadias;
	private HotelManagementConsumibles controladorConsumibles;
	private HotelManagementPagos controladorPagos;

	public HotelManagementSystem()  {
		cargarDatos();
		cargarControladores();
	}
	
	private void cargarDatos() {
		try {
			this.loaderData = new HotelManagementLoaderData();
			this.hotel = this.loaderData.cargarHotel();
			this.opcionesHabitacion = this.loaderData.cargarTipoHabitaciones();
			this.inventarioHabitaciones = this.loaderData.cargarHabitaciones(opcionesHabitacion, hotel);
			this.reservas = this.loaderData.cargarReservas(inventarioHabitaciones);
			this.estadias = this.loaderData.cargarEstadias(reservas);
			this.inventarioProductos = this.loaderData.cargarProductos();
			this.inventarioServicios = this.loaderData.cargarServicios();
			this.usuarios = this.loaderData.cargarUsuarios();
			this.pasarelasPago = this.loaderData.cargarPasarelas();
			this.loaderData.cargarDisponibilidades(inventarioHabitaciones, reservas);
		} catch (Exception e) {
			System.out.println("La carga de datos no ha sido posible debido a: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void cargarControladores() {
		this.controladorUsuarios = new HotelManagementUsuarios(usuarios);
		this.controladorReservas = new HotelManagementReservas(inventarioHabitaciones, reservas);
		this.controladorEstadias = new HotelManagementEstadias(inventarioHabitaciones, estadias, controladorReservas);
		this.controladorConsumibles = new HotelManagementConsumibles(inventarioProductos, inventarioServicios);
		this.controladorPagos = new HotelManagementPagos(pasarelasPago);
	}
	
	// =====================================================================================================================================================
	// RESERVAS 
	// =====================================================================================================================================================
	
	public void reservar(
			Long id,
			String nombre,
			String email,
			String dni,
			String telefono,
			Integer edad,
			Integer cantidad,
			Date llegada,
			Date salida,
			Map<String, Integer> habitacionesElegidas
	) throws Exception {
		controladorReservas.reservar(id, nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);
	}
	
	public void cancelarReserva(Long id) throws Exception {
		controladorReservas.cancelarReserva(id);
	}
	
	public void eliminarReserva(Long id) throws Exception {
		controladorReservas.eliminarReserva(id);
	}
	
	public Reserva getReservaById(Long id) {
		return controladorReservas.getReservaById(id);
	}
	
	public Reserva getReservaByDNI(String dni) {
		return controladorReservas.getReservaByDNI(dni);
	}
	
	public Reserva getReservaByHabitacion(String id) {
		return controladorReservas.getReservaByHabitacion(id);
	}
	
	public Map<String, TipoHabitacion> getOpcionesHabitacion() {
		return opcionesHabitacion;
	}	
	
	// =====================================================================================================================================================
	
	
	// =====================================================================================================================================================
	// ESTADIAS
	// =====================================================================================================================================================
	
	public Estadia iniciarEstadia(String dniTitular, List<Huesped> huespedes) throws Exception {
		Reserva reserva = getReservaByDNI(dniTitular);
		if (reserva == null) 
			throw new Exception("No se encontraron reservas para el dni "+ dniTitular);
		if (huespedes.size() > reserva.getCantidadPersonas()) 
			throw new Exception("La cantidad de huespedes supera los esperados");
		return controladorEstadias.iniciarEstadia(reserva, huespedes);
	}
	
	public Estadia getEstadiaById(Long id) {
		return controladorEstadias.getEstadiaById(id);
	}

	public Estadia getEstadiaByTitular(String titular) {
		return controladorEstadias.getEstadiaByTitular(titular);
	}

	public Estadia getEstadiaByDNI(String dni) {
		return controladorEstadias.getEstadiaByDNI(dni);
	}

	public Estadia getEstadiaByHabitacion(Integer id) {
		return controladorEstadias.getEstadiaByHabitacion(id);
	}
	
	public Estadia cerrarEstadia(Estadia estadia) throws Exception {
		controladorReservas.cerrarReserva(estadia.getReserva());
		controladorEstadias.actualizarEstadia(estadia);
		return estadia;
	}

	// =====================================================================================================================================================
	
	// =====================================================================================================================================================
	// LOGIN 
	// =====================================================================================================================================================
	
	public boolean userExists(String user) {
		return controladorUsuarios.userExists(user);
	}

	public Usuario userLogin(String user, String password) {
		return controladorUsuarios.userLogin(user, password);
	}
	
	public void registrarUsuario(String user, String password, Rol rol) throws Exception {
		controladorUsuarios.userSignUp(user, password, rol);
	}
	
	// =====================================================================================================================================================
	
	// =====================================================================================================================================================
	// CONSUMIBLES
	// =====================================================================================================================================================
	
	public void facturarAlaHabitacion(Integer habitacion, Map<Long, Integer> productos) throws Exception {
		Estadia estadia = getEstadiaByHabitacion(habitacion);
		if (estadia == null)
			throw new Exception("No se encontraron estadias en la habitacion " + habitacion);
		controladorConsumibles.facturarAlaHabitacion(estadia, productos);
	}
	
	public Factura facturar(Integer habitacion, Map<Long, Integer> consumibles) throws Exception {
		Estadia estadia = getEstadiaByHabitacion(habitacion);
		if (estadia == null)
			throw new Exception("No se encontraron estadias en la habitacion " + habitacion);
		return controladorConsumibles.facturar(estadia, consumibles);
	}
	
	public Factura facturarEstadia(Estadia estadia) throws Exception {
		return controladorConsumibles.facturarEstadia(estadia);
	}
	
	public Factura facturarReserva(Reserva reserva) throws Exception {
		return controladorConsumibles.facturarReserva(reserva);
	}
	
	// =====================================================================================================================================================
	// PRODUCTOS
	// =====================================================================================================================================================
	
	public Map<Long, Producto> getProductos() {
		return controladorConsumibles.getProductos();
	}
	
	public Consumible getProductoByID(Long id) {		
		return controladorConsumibles.getProductoById(id);
	}
		
	// =====================================================================================================================================================
	// SERVICIOS
	// =====================================================================================================================================================
	
	public Restaurante getRestaurante() {
		return controladorConsumibles.getRestaurante();
	}
	
	public Spa getSpa() {
		return controladorConsumibles.getSpa();
	}
	
	// =====================================================================================================================================================
	// PAGOS
	// =====================================================================================================================================================
	
	public Map<String, PasarelaPago> getPasarelas() {
		return controladorPagos.getPlataformas();
	}
	
	public Factura procesarPagoConTarjetaCredito(Factura factura, String pasarela, String owner, Long number, Integer cvv, String expiration) throws Exception {
		return controladorPagos.procesarPagoConTarjetaCredito(factura, pasarela, owner, number, cvv, expiration);
	}
}
