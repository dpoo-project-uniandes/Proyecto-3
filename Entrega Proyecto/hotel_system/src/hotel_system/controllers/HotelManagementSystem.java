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
	
	private Titular setTitular(String string) {
		String[] datos = string.split(",");
		return new Titular(datos[0],
				datos[2],
				Integer.parseInt(datos[1]),
				datos[3],
				datos[4]);
	}

	public Spa getServicioSpa(){
		Spa spa = (Spa)inventarioServicios.get("spa");
		return spa;
	}

	public Restaurante getServicioRestaurante(){
		Restaurante res = (Restaurante)inventarioServicios.get("restaurante");
		return res;
	}


	public Estadia getEstadiaById(String id) {
		Optional<Estadia> estadia = estadias.values().stream()
				.filter(reg -> (""+reg.getId()).equals(id))
				.findAny();
		if (estadia.isPresent()) {return estadia.get();}
		else {return null;}
	}

	public Estadia getEstadiaByTitular(String nom) {
		Optional<Estadia> estadia = estadias.values().stream()
				.filter(reg -> (reg.getReserva().getTitular()
				.getNombre().equals(nom))).findAny();
		if (estadia.isPresent()) {return estadia.get();}
		else {return null;}
	}

	public Estadia getEstadiaByDNI(String dni) {
		Optional<Estadia> reservacion = estadias.values().stream()
				.filter(estadia -> estadia.getReserva().getTitular().getDni().equals(dni))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get();}
		else {return null;}
	}

	
	public Estadia getEstadiaByHabitacion(String id) {
		Optional<Habitacion> habitacion = inventarioHabitaciones.values().stream()
				.filter(hab -> (hab.getNumero().toString()).equals(id))
				.findAny();
		if (habitacion.isPresent()) {return habitacion.get().getReservaActual().getEstadia();}
		else {return null;}
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
		Estadia estadia = getEstadiaByHabitacion(hab);
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
		Estadia estadia = getEstadiaByHabitacion(hab);
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
		Estadia estadia = getEstadiaByHabitacion(hab);
		if (pagar) {
			estadia.cargarFactura(new Factura(estadia.getReserva().getTitular(), consumos));
		}
		else {
			for (Consumible consumible : consumos) {
				estadia.cargarConsumo(consumible);
			}
		}
	}

	public List<Integer> iniciarEstadia(String dni, List<List<String>> huesp) {
		Reserva reserva = getReservaByDNI(dni);
		List<Huesped> huespedes = new ArrayList<>();
		huespedes.add(reserva.getTitular());
		for (List<String> huesped : huesp) {
			huespedes.add(new Huesped(huesped.get(0),
					huesped.get(1),
					Integer.parseInt(huesped.get(2))));
		}
		Estadia estadia = new Estadia(reserva, reserva.getFechaDeLlegada(), reserva.getFechaDeSalida(), huespedes);
		reserva.setEstadia(estadia);
		estadias.put(estadia.getId(), estadia);

		return reserva.getHabitaciones().stream().map(h -> h.getNumero()).toList();
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
