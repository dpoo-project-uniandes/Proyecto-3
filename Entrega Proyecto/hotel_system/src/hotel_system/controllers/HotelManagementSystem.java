package hotel_system.controllers;

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
import hotel_system.utils.Utils;
import services.Dupla;
import services.FileManager;

public class HotelManagementSystem {

	private List<Habitacion> inventarioHabitaciones;
	private List<TipoHabitacion> opcionesHabitacion;
	private List<Reserva> reservas;
	private List<Estadia> estadias;
	private List<Producto> inventarioProductos;
	private Map<String,Usuario> usuarios;
	private Map<String, Servicio> inventarioServicios;
	private Hotel hotel;
	
	private HotelManagementLoaderData loaderData;
	private HotelManagementUsuarios controladorUsuarios;

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
			this.reservas = this.loaderData.cargarReservas();
			this.estadias = this.loaderData.cargarEstadias();
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
	}

	private Titular setTitular(String string) {
		String[] datos = string.split(",");
		return new Titular(datos[0],
				datos[2],
				Integer.parseInt(datos[1]),
				datos[3],
				datos[4]);
	}

	public Integer calcularCapacidadTotal(List<Integer> ids) {
		int count = 0;
		for (Integer id:ids) {
			count+=inventarioHabitaciones.stream().filter(hab -> hab.getNumero() == id)
			.findAny().get().getTipo().getCapacidad();
		}
		return count;
	}

	public void reservar(String nombre, String email, String dni, String telefono
			,Integer edad, Integer cantidad, List<Integer> opcionHab, String llegada, String salida) throws Exception {

		Titular titular = new Titular(nombre, dni, edad, email, telefono);

		List<Habitacion> habitaciones = new ArrayList<>();

		for (Integer num: opcionHab) {
			habitaciones.add(inventarioHabitaciones.stream()
					.filter(hab -> hab.getNumero() == num)
					.findAny()
					.get()
			);
		}

		Reserva reserva = new Reserva(Utils.stringToDate(llegada), Utils.stringToDate(salida), titular, cantidad, habitaciones);
		reservas.add(reserva);

		List<List<String>> rowReserva = List.of(List.of(
				reserva.getNumero().toString(),
				reserva.getTarifaTotal().toString(),
				reserva.getEstado().toString(),
				reserva.getCantidadPersonas().toString(),
				Utils.stringLocalDate(reserva.getFechaDeCreacion()),
				Utils.stringLocalDate(reserva.getFechaDeLlegada()),
				Utils.stringLocalDate(reserva.getFechaDeSalida()),
				reserva.getTitular().getDni().toString(),
				"0"
				));

		FileManager.agregarLineasCSV("reservas.csv", rowReserva);
	}
	//Funciones de reservas
	public void cancelarReserva(String dni) {
		getReservaByDNI(dni).cancelarReserva();
	}
	public void eliminarReserva(Reserva reserva) {
		//TODO eliminarReservaEnCsv
		this.reservas.remove(reserva);
	}
	public void modificarReserva(Reserva reservaNueva) {
		Reserva reservaVieja = getReservaById(""+reservaNueva.getNumero());
		this.reservas.remove(reservaVieja);
		this.reservas.add(reservaNueva);
	}
	
		//getters
	public Reserva getReservaById(String id) {
		Optional<Reserva> reservacion = reservas.stream()
				.filter(res -> (""+res.getNumero()).equals(id))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get();}
		else {return null;}
	}
	public Reserva getReservaByHabitacion(String id) {
		Optional<Habitacion> reservacion = inventarioHabitaciones
				.stream().filter(hab -> (hab.getReservaActual().getNumero()+"").equals(id))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get().getReservaActual();}
		else {return null;}
	}

	
	public Integer seleccionarHab(Integer select, String desde, String hasta) {
		String alias = opcionesHabitacion.get(select).getAlias();
		return inventarioHabitaciones.stream()
				.filter(hab -> hab.getTipo().getAlias().equals(alias) && hab.consultarDisponibilidad(Utils.stringToDate(desde), Utils.stringToDate(hasta)))
				.findAny()
				.get()
				.getNumero();
	}
	
	public Integer seleccionarHab(String alias, String desde, String hasta) {
		return inventarioHabitaciones.stream()
				.filter(hab -> hab.getTipo().getAlias().equals(alias) && hab.consultarDisponibilidad(Utils.stringToDate(desde), Utils.stringToDate(hasta)))
				.findAny()
				.get()
				.getNumero();
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
		Optional<Estadia> estadia = estadias.stream()
				.filter(reg -> (""+reg.getId()).equals(id))
				.findAny();
		if (estadia.isPresent()) {return estadia.get();}
		else {return null;}
	}

	public Reserva getReservaByDNI(String dni) {
		Optional<Reserva> reservacion = reservas.stream()
				.filter(res -> res.getTitular().getDni().equals(dni))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get();}
		else {return null;}
	}

	public Estadia getEstadiaByTitular(String nom) {
		Optional<Estadia> estadia = estadias.stream()
				.filter(reg -> (reg.getReserva().getTitular()
				.getNombre().equals(nom))).findAny();
		if (estadia.isPresent()) {return estadia.get();}
		else {return null;}
	}

	public Estadia getEstadiaByDNI(String dni) {
		Optional<Estadia> reservacion = estadias.stream()
				.filter(estadia -> estadia.getReserva().getTitular().getDni().equals(dni))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get();}
		else {return null;}
	}

	
	public Estadia getEstadiaByHabitacion(String id) {
		Optional<Habitacion> habitacion = inventarioHabitaciones.stream()
				.filter(hab -> (hab.getNumero().toString()).equals(id))
				.findAny();
		if (habitacion.isPresent()) {return habitacion.get().getReservaActual().getEstadia();}
		else {return null;}
	}


	

	public List<Habitacion> getInventarioHabitaciones() {
		return inventarioHabitaciones;
	}

	public void setInventarioHabitaciones(List<Habitacion> inventarioHabitaciones) {
		this.inventarioHabitaciones = inventarioHabitaciones;
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

	public List<TipoHabitacion> getOpcionesHabitacion() {
		return opcionesHabitacion;
	}

	public void setOpcionesHabitacion(List<TipoHabitacion> opcionesHabitacion) {
		this.opcionesHabitacion = opcionesHabitacion;
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
		estadias.add(estadia);

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
	
	// LOGIN ====================================================================================================================
	
	public boolean userExists(String user) {
		return controladorUsuarios.userExists(user);
	}

	public Usuario userLogin(String user, String password) {
		return controladorUsuarios.userLogin(user, password);
	}
	
	public void registrarUsuario(String user, String password, Rol rol) throws Exception {
		controladorUsuarios.userSignUp(user, password, rol);
	}
	
	//  ====================================================================================================================
	
	public void eliminarProducto(Producto producto, String tipo) {
		if (tipo.equals("hotel")) {
			
		}else if(tipo.equals("spa")) {
			
		}else {
			
		}
		
	}

}
