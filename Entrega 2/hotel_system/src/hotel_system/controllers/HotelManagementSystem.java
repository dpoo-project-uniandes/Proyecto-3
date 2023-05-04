package hotel_system.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hotel_system.models.Consumible;
import hotel_system.models.Disponibilidad;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Habitacion;
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
import services.FileManager;

public class HotelManagementSystem {
	
	private List<Habitacion> inventarioHabitaciones;
	private List<TipoHabitacion> opcionesHabitacion;
	private List<Reserva> reservas;
	private List<Estadia> registros;
	private List<Producto> inventarioProductos;
	private Map<String,Usuario> usuarios;
	private Map<String, Servicio> inventarioServicios;
	
	public HotelManagementSystem()  {
		try {
			cargarTipoHabitaciones();
			cargarDisponibilidades();
			cargarHabitaciones();
			cargarReservas();
			cargarEstadias();
			cargarProductos();
			cargarServicios();
			cargarUsuarios();
		} catch (Exception e) {
			System.out.println("El sistema no puedo inicializarse, intente nuevamente");
			e.printStackTrace();
		}
	}
	
	private void cargarTipoHabitaciones() throws Exception {
		List<TipoHabitacion> opcionesHabitaciones = new ArrayList<TipoHabitacion>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("tipo_habitaciones.csv");
		for (Map<String, String> dato : datos) {
			String alias = dato.get("alias");
			Boolean conBalcon = Boolean.parseBoolean(dato.get("balcon"));
			Boolean conVista = Boolean.parseBoolean(dato.get("vista"));
			Boolean conCocina = Boolean.parseBoolean(dato.get("cocina"));
			Integer camasSencillas = Integer.parseInt(dato.get("camas_sencilla"));
			Integer camasDobles = Integer.parseInt(dato.get("camas_doble"));
			Integer camasQueen = Integer.parseInt(dato.get("camas_queen"));
			Integer capacidad = Integer.parseInt(dato.get("capacidad"));
			Double precio = Double.parseDouble(dato.get("precio"));
			TipoHabitacion tipoHabitacion = new TipoHabitacion(alias, capacidad, conBalcon, conVista, conCocina, camasSencillas, camasDobles, camasQueen, precio);
			opcionesHabitaciones.add(tipoHabitacion);
		}
		this.opcionesHabitacion = opcionesHabitaciones;
	}
	
	private void cargarHabitaciones() throws Exception{
		List<Habitacion> habitaciones = new ArrayList<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("habitaciones.csv");
		for(Map<String, String> dato : datos) {
			Integer numeroHabitacion = Integer.parseInt(dato.get("numero_habitacion"));
			TipoHabitacion tipo = this.opcionesHabitacion.stream()
					.filter(th -> th.getAlias().equals(dato.get("tipo_habitacion")))
					.findAny()
					.get();
			List<Disponibilidad> disponibilidad = cargarDisponibilidad(numeroHabitacion, tipo.getPrecio());
			Habitacion habitacion = new Habitacion(numeroHabitacion, tipo, disponibilidad);
			habitaciones.add(habitacion);
		}
		this.setInventarioHabitaciones(habitaciones);
	}
	
	private List<Disponibilidad> cargarDisponibilidad(Integer numeroHabitacion, Double precio) throws Exception {
		List<Disponibilidad> disponibilidad = new ArrayList<>();
		List<List<String>> datos = new ArrayList<>();
		Date hoy = Utils.nowDate();
		for(int d = 0; d <= 365; d++) {
			LocalDate tomorrow = hoy.toLocalDate().plusDays(d);
			List<String> lista = List.of(numeroHabitacion.toString(), Utils.stringLocalDate(Date.valueOf(tomorrow)), "true", precio.toString());
			datos.add(lista);
			disponibilidad.add(new Disponibilidad(precio, true, Date.valueOf(tomorrow)));
		}
		FileManager.agregarLineasCSV("disponibilidades.csv", datos);
		return disponibilidad;
	}
	
	private void cargarDisponibilidades() throws Exception {
		FileManager.eliminarArchivo("disponibilidades.csv");
		FileManager.agregarLineasCSV("disponibilidades.csv", List.of(List.of("numero_habitacion","fecha","estado","precio")));
	}
	
	private void cargarUsuarios() throws Exception {
		Map<String, Usuario> usuarios = new HashMap<String, Usuario>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("usuarios.csv");
		for(Map<String, String> dato : datos) {
			usuarios.put(dato.get("user"), new Usuario(dato.get("user"), dato.get("password"), Rol.valueOf(dato.get("rol"))));
		}
		this.usuarios = usuarios;
	}
	
	private void cargarReservas() throws Exception{
		FileManager.eliminarArchivo("reservas.csv");
		FileManager.agregarLineasCSV("reservas.csv", List.of(List.of("numero","tarifaTotal","estado","cantidadPersonas","fechaCreacion","fechaLlegada","fechaSalida","titular_dni","numero_estadia")));
		FileManager.eliminarArchivo("reservas_habitaciones.csv");
		FileManager.agregarLineasCSV("reservas_habitaciones.csv", List.of(List.of("numero_reserva", "numero_habitacion")));
		this.reservas = new ArrayList<Reserva>();
	}
	
	private void cargarEstadias(){
		this.registros = new ArrayList<>();
	}
	
	private void cargarProductos() throws Exception {
		List<Producto> productosCargados = new ArrayList<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("productos.csv");
		for (Map<String, String> dato : datos) {
			Long id = Long.parseLong(dato.get("id"));
		    String nombre = dato.get("nombre");
		    Double precio = Double.parseDouble(dato.get("precio"));
		    Producto producto = new Producto(id, nombre, precio);
		    productosCargados.add(producto);
		}
		this.inventarioProductos = productosCargados;
	}	

	private void cargarServicios() throws Exception {
        HashMap<String, Servicio> serviciosCargados = new HashMap<>();
        Restaurante restaurante = cargarServicioRestaurante();
        Spa spa = cargarServicioSpa();
        
        serviciosCargados.put("restaurante", restaurante);
        serviciosCargados.put("spa", spa);

        this.inventarioServicios = serviciosCargados;
    }
	
	private Spa cargarServicioSpa() throws Exception {
        List<Producto> productosSpa = new ArrayList<Producto>();
        List<Map<String, String>> datosSpa = FileManager.cargarArchivoCSV("productos_spa.csv");
        for (Map<String, String> dato : datosSpa) {
            Long id = Long.parseLong(dato.get("id"));
            String nombre = dato.get("nombre");
            Double precio = Double.parseDouble(dato.get("precio"));
            productosSpa.add(new Producto(id, nombre, precio));
        }
        return new Spa(384723984L, productosSpa);
	}
	
	private Restaurante cargarServicioRestaurante() throws Exception {
        List<ProductoRestaurante> productosRestaurante = new ArrayList<>();
        List<Map<String, String>> datosRestaurante = FileManager.cargarArchivoCSV("productos_restaurante.csv");
        for (Map<String, String> dato : datosRestaurante) {
            Long id = Long.parseLong(dato.get("id"));
            String nombre = dato.get("nombre");
            Double precio = Double.parseDouble(dato.get("precio"));
            String fechaInicial = dato.get("fechaInicial");
            String fechaFinal = dato.get("fechaFinal");
            List<Date> fechas = new ArrayList<>();
            fechas.add(Utils.stringToDate(fechaInicial));
            fechas.add(Utils.stringToDate(fechaFinal));
            Boolean alCuarto = Boolean.parseBoolean(dato.get("alCuarto"));
            String tipo = dato.get("tipo");
            ProductoRestaurante producto = new ProductoRestaurante(id, nombre, precio, fechas, alCuarto, tipo);
            productosRestaurante.add(producto);
        }
        return new Restaurante(988374853L, productosRestaurante);
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
		Integer count = 0;
		for (Integer id:ids) {
			count+=inventarioHabitaciones.stream().filter(hab -> hab.getNumero() == id)
			.findAny().get().getTipo().getCapacidad();
		}
		return count;
	}
	
	public void reservar(String nombre, String email, String dni, String telefono
			,Integer edad, Integer cantidad, List<Integer> opcionHab, String llegada, String salida) throws Exception {
		
		Titular titular = new Titular(nombre, dni, edad, email, telefono);
		
		List<Habitacion> habitaciones = new ArrayList<Habitacion>();
		
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
	
	public Integer seleccionarHab(Integer select, String desde, String hasta) {
		String alias = opcionesHabitacion.get(select).getAlias();
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
	
	public Reserva getReservaById(String id) {
		Optional<Reserva> reservacion = reservas.stream()
				.filter(res -> (""+res.getNumero()).equals(id))
						.findAny();
		if (reservacion.isPresent()) {return reservacion.get();}
		else {return null;}
	}    
	
	public Estadia getEstadiaById(String id) {
		Optional<Estadia> registro = registros.stream()
				.filter(reg -> (""+reg.getId()).equals(id))
				.findAny();
		if (registro.isPresent()) {return registro.get();}
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
		Optional<Estadia> registro = registros.stream()
				.filter(reg -> (reg.getReserva().getTitular()
				.getNombre().equals(nom))).findAny();
		if (registro.isPresent()) {return registro.get();}
		else {return null;}
	}
	
	public Estadia getEstadiaByDNI(String dni) {
		Optional<Estadia> reservacion = registros.stream()
				.filter(estadia -> estadia.getReserva().getTitular().getDni().equals(dni))
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
	
	public Estadia getEstadiaByHabitacion(String id) {
		Optional<Habitacion> habitacion = inventarioHabitaciones.stream()
				.filter(hab -> (hab.getNumero().toString()).equals(id))
				.findAny();
		if (habitacion.isPresent()) {return habitacion.get().getReservaActual().getEstadia();}
		else {return null;}
	}
	
	public boolean validadUsuario(String user) {
		return usuarios.containsKey(user);
	}
	
	public String validarContrasenia(String user, String password) {
		if (usuarios.get(user).getPassword().equals(password)) return usuarios.get(user).getRol().toString(); 
		else return null;
	}
	
	public void registrarUsuario(String user, String password, Rol rol) throws Exception {
		usuarios.put(user, new Usuario(user, password, rol));
		List<List<String>> rowUser = List.of(List.of(user, password, rol.toString()));
		FileManager.agregarLineasCSV("usuarios.csv", rowUser);
	}
	
	public void cancelarReserva(String dni) {
		getReservaByDNI(dni).cancelarReserva();;
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
		registros.add(estadia);
		
		return reserva.getHabitaciones().stream().map(h -> h.getNumero()).toList();
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
}
