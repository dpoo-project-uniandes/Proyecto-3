package hotel_system.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hotel_system.models.Consumible;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Habitacion;
import hotel_system.models.Hotel;
import hotel_system.models.Huesped;
import hotel_system.models.Producto;
import hotel_system.models.ProductoRestaurante;
import hotel_system.models.Reserva;
import hotel_system.models.Restaurante;
import hotel_system.models.Rol;
import hotel_system.models.Servicio;
import hotel_system.models.Spa;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Titular;
import hotel_system.models.Usuario;
import services.Dupla;

public class HotelManagementSystem {

	private Map<Integer, Habitacion> inventarioHabitaciones;
	private Map<String, TipoHabitacion> opcionesHabitacion;
	private Map<Long, Reserva> reservas;
	private Map<Long, Estadia> estadias;
	private List<Producto> inventarioProductos;
	private Map<String,Usuario> usuarios;
	private Map<String, Servicio> inventarioServicios;
	private Hotel hotel;
	
	private HotelManagementLoaderData loaderData;
	private HotelManagementUsuarios controladorUsuarios;
	private HotelManagementReservas controladorResevas;
	private HotelManagementEstadias controladorEstadias;

	public HotelManagementSystem()  {
		cargarDatos();
		cargarControladores();
	}
	
	private void cargarDatos() {
		try {
			this.loaderData = new HotelManagementLoaderData();
			this.loaderData.limpiarDisponibilidades();
			this.hotel = this.loaderData.cargarHotel();
			this.opcionesHabitacion = this.loaderData.cargarTipoHabitaciones();
			this.inventarioHabitaciones = this.loaderData.cargarHabitaciones(opcionesHabitacion, hotel);
			this.estadias = this.loaderData.cargarEstadias();
			this.reservas = this.loaderData.cargarReservas(inventarioHabitaciones, estadias);
			this.inventarioProductos = this.loaderData.cargarProductos();
			this.inventarioServicios = this.loaderData.cargarServicios();
			this.usuarios = this.loaderData.cargarUsuarios();
		} catch (Exception e) {
			System.out.println("La carga de datos no ha sido posible debido a: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void cargarControladores() {
		this.controladorUsuarios = new HotelManagementUsuarios(usuarios);
		this.controladorResevas = new HotelManagementReservas(inventarioHabitaciones, reservas);
		this.controladorEstadias = new HotelManagementEstadias(inventarioHabitaciones, estadias);
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
		controladorResevas.reservar(id, nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);
	}
	
	public void cancelarReserva(Long id) throws Exception {
		controladorResevas.cancelarReserva(id);
	}
	
	public void eliminarReserva(Long id) throws Exception {
		controladorResevas.eliminarReserva(id);
	}
	
	public Reserva getReservaById(Long id) {
		return controladorResevas.getReservaById(id);
	}
	
	public Reserva getReservaByDNI(String dni) {
		return controladorResevas.getReservaByDNI(dni);
	}
	
	public Reserva getReservaByHabitacion(String id) {
		return controladorResevas.getReservaByHabitacion(id);
	}
	
	// =====================================================================================================================================================
	
	
	// =====================================================================================================================================================
	// ESTADIAS
	// =====================================================================================================================================================
	
	public void iniciarEstadia(String dni, List<Huesped> huespedes) throws Exception {
		Reserva reserva = controladorResevas.getReservaByDNI(dni);
		if (reserva != null) 
			throw new Exception("No se encontraron reservas para el dni "+ dni);
		controladorEstadias.iniciarEstadia(reserva, huespedes);
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

	// =====================================================================================================================================================

	public Spa getServicioSpa(){
		Spa spa = (Spa)inventarioServicios.get("spa");
		return spa;
	}

	public Restaurante getServicioRestaurante(){
		Restaurante res = (Restaurante)inventarioServicios.get("restaurante");
		return res;
	}

	public List<Producto> getInventarioProductos() {
		return inventarioProductos;
	}

	public void setInventarioProductos(List<Producto> inventarioProductos) {
		this.inventarioProductos = inventarioProductos;
	}


	public Map<String, Servicio> getInventarioServicios() {
		return inventarioServicios;
	}

	public void setInventarioServicios(HashMap<String, Servicio> inventarioServicios) {
		this.inventarioServicios = inventarioServicios;
	}

	public Map<String, TipoHabitacion> getOpcionesHabitacion() {
		return opcionesHabitacion;
	}

	public void seleccionarProductoSpa(List<Producto> consumos, String hab, Boolean pagar) {
		Estadia estadia = controladorEstadias.getEstadiaByHabitacion(Integer.parseInt(hab));
		if (pagar) {
			for (Producto consumible : consumos) {
				getServicioSpa().agregarConsumo(consumible);
			}
			estadia.cargarFactura(getServicioSpa().facturar(estadia.getReserva().getTitular()));
		}
		else {
			for (Producto consumible : consumos) {
				estadia.cargarConsumo(consumible);
			}
		}
	}

	public void seleccionarProductoRestaurante(List<Producto> consumos, String hab, Boolean pagar) {
		Estadia estadia = controladorEstadias.getEstadiaByHabitacion(Integer.parseInt(hab));
		if (pagar) {
			for (Producto consumible : consumos) {
				getServicioRestaurante().agregarConsumo(consumible);
			}
			estadia.cargarFactura(getServicioRestaurante().facturar(estadia.getReserva().getTitular()));
		}
		else {
			for (Producto consumible : consumos) {
				estadia.cargarConsumo(consumible);
			}
		}
	}

	public void seleccionarProducto(List<Consumible> consumos, String hab, Boolean pagar) {
		Estadia estadia = controladorEstadias.getEstadiaByHabitacion(Integer.parseInt(hab));
		if (pagar) {
			estadia.cargarFactura(new Factura(estadia.getReserva().getTitular(), consumos));
		}
		else {
			for (Consumible consumible : consumos) {
				estadia.cargarConsumo(consumible);
			}
		}
	}

	public Dupla<Producto, String> getProductoByID(String id) {
		Optional<Producto> consumible; 
		Optional<ProductoRestaurante> productoRestaurante;
		consumible = inventarioProductos.stream()
		.filter(cons -> cons.getId().toString().equals(id))
		.findAny();
		if (consumible.isPresent()) {return new Dupla<Producto, String>(consumible.get(), "hotel");}
		productoRestaurante = getServicioRestaurante().getProductos().stream()
			.filter(cons -> cons.getId().toString().equals(id))
				.findAny();
		if (productoRestaurante.isPresent()) {return new Dupla<Producto, String>(productoRestaurante.get(), "restaurante");}
		consumible = getServicioSpa().getProductosYServicios().stream()
			.filter(cons -> cons.getId().toString().equals(id))
				.findAny();
		if (consumible.isPresent()) {return new Dupla<Producto, String>(consumible.get(), "spa");}
		return null;
	}
	public void finalizarEstadia(String dni) {
		Estadia estadia = getEstadiaByDNI(dni);
		estadia.facturarEstadia();
		Factura factura = estadia.getFacturaTotal();
		System.out.println(factura.generarFactura());
	}

	public Integer cantidadReserva(String dni) {
		return getReservaByDNI(dni).getCantidadPersonas();
	}
	
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
	
	public void eliminarProducto(Producto producto, String tipo) {
		if (tipo.equals("hotel")) {
			
		}else if(tipo.equals("spa")) {
			
		}else {
			
		}
		
	}

}
