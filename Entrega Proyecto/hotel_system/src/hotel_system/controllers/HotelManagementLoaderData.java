package hotel_system.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hotel_system.models.Consumible;
import hotel_system.models.Disponibilidad;
import hotel_system.models.Estadia;
import hotel_system.models.EstadoReserva;
import hotel_system.models.Factura;
import hotel_system.models.Habitacion;
import hotel_system.models.Hotel;
import hotel_system.models.Huesped;
import hotel_system.models.PasarelaPago;
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

public class HotelManagementLoaderData {
	
	public void limpiarDisponibilidades() throws Exception {
		FileManager.eliminarArchivo("disponibilidades.csv");
		FileManager.agregarLineasCSV("disponibilidades.csv", List.of(List.of("id","numero_habitacion","fecha","estado","precio")));
	}
	
	public Map<String, TipoHabitacion> cargarTipoHabitaciones() throws Exception {
		Map<String, TipoHabitacion> opcionesHabitaciones = new HashMap<>();
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
			Double area = Double.parseDouble(dato.get("area"));
			Boolean conAireAcondicionado = Boolean.parseBoolean(dato.get("aire_acondicionado"));
			Boolean conCalefaccion = Boolean.parseBoolean(dato.get("calefaccion"));
			Boolean conTelevision = Boolean.parseBoolean(dato.get("television"));
			Boolean conCafetera = Boolean.parseBoolean(dato.get("cafetera"));
			Boolean conCubrelecho = Boolean.parseBoolean(dato.get("cubrelecho"));
			Boolean conTapetesHipoalergenicos = Boolean.parseBoolean(dato.get("tapetes_hipoalergenicos"));
			Boolean conPlancha = Boolean.parseBoolean(dato.get("plancha"));
			Boolean coSecadorCabello = Boolean.parseBoolean(dato.get("secador_cabello"));
			String voltaje = dato.get("voltaje");
			Boolean conUSBA = Boolean.parseBoolean(dato.get("usb_a"));
			Boolean conUSBC = Boolean.parseBoolean(dato.get("usb_c"));
			Boolean conDesayuno = Boolean.parseBoolean(dato.get("desayuno"));
			TipoHabitacion tipoHabitacion = new TipoHabitacion(
					alias,
					capacidad,
					conBalcon,
					conVista,
					conCocina,
					camasSencillas,
					camasDobles,
					camasQueen,
					precio,
					area,
					conAireAcondicionado,
					conCalefaccion,
					conTelevision,
					conCafetera,
					conCubrelecho,
					conTapetesHipoalergenicos,
					conPlancha,
					coSecadorCabello,
					voltaje,
					conUSBA,
					conUSBC,
					conDesayuno
			);
			opcionesHabitaciones.put(alias, tipoHabitacion);
		}
		return opcionesHabitaciones;
	}
	
	public Map<Long, Disponibilidad> cargarDisponibilidades(Map<Integer, Habitacion> habitaciones, Map<Long, Reserva> reservas) throws Exception {
		Map<Long, Disponibilidad> disponibilidades = new HashMap<>();
		List<Map<String, String>> disponibilidadesCSV = FileManager.cargarArchivoCSV("disponibilidades.csv");
		Map<Long, Long> disponibilidadesReservas = cargarDisponibilidadesReservas();
		for (Map<String, String> row : disponibilidadesCSV) {
			Long id = Long.parseLong(row.get("id"));
			Integer habitacion = Integer.parseInt(row.get("numero_habitacion"));
			Date fecha = Utils.stringToDate(row.get("fecha"));
			Boolean estado = Boolean.parseBoolean(row.get("estado"));
			Double precio = Double.parseDouble(row.get("precio"));
			Reserva reserva = reservas.get(disponibilidadesReservas.get(id));
			Disponibilidad disponibilidad = new Disponibilidad(id, habitacion, precio, estado, fecha, reserva);
			habitaciones.get(habitacion).agregarDisponibilidad(disponibilidad);
		}
		return disponibilidades;
	}
	
	public Map<Integer, Habitacion> cargarHabitaciones(Map<String, TipoHabitacion> opcionesHabitacion, Hotel hotel) throws Exception{
		Map<Integer, Habitacion> habitaciones = new HashMap<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("habitaciones.csv");
		for(Map<String, String> dato : datos) {
			Integer numeroHabitacion = Integer.parseInt(dato.get("numero_habitacion"));
			TipoHabitacion tipo = opcionesHabitacion.get(dato.get("tipo_habitacion"));
			List<Disponibilidad> disponibilidad = new ArrayList<>();
			Habitacion habitacion = new Habitacion(numeroHabitacion, tipo, disponibilidad, hotel);
			habitaciones.put(numeroHabitacion, habitacion);
		}
		return habitaciones;
	}

	public Map<String, Usuario> cargarUsuarios() throws Exception {
		Map<String, Usuario> usuarios = new HashMap<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("usuarios.csv");
		for(Map<String, String> dato : datos) {
			usuarios.put(dato.get("user"), new Usuario(dato.get("user"), dato.get("password"), Rol.valueOf(dato.get("rol"))));
		}
		return usuarios;
	}

	public List<Reserva> limpiarReservas() throws Exception {
		FileManager.eliminarArchivo("reservas.csv");
		FileManager.agregarLineasCSV("reservas.csv", List.of(List.of("numero","tarifa_total","estado","cantidad_personas","fecha_creacion","fecha_llegada","fecha_salida","titular_dni","numero_estadia")));
		FileManager.eliminarArchivo("reservas_habitaciones.csv");
		FileManager.agregarLineasCSV("reservas_habitaciones.csv", List.of(List.of("numero_reserva", "numero_habitacion")));
		return new ArrayList<>();
	}
	
	public Map<Long, Reserva> cargarReservas(Map<Integer, Habitacion> habitaciones) throws Exception {
		Map<Long, Reserva> reservas = new HashMap<>();
		Map<String, Titular> titulares = cargarTitulares();
		Map<Long, List<Integer>> reservasHabitaciones = cargarReservasHabitaciones();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("reservas.csv");
		for (Map<String, String> dato : datos) {
			Long id = Long.parseLong(dato.get("numero"));
			Double tarifaTotal = Double.parseDouble(dato.get("tarifa_total"));
			EstadoReserva estado = EstadoReserva.valueOf(dato.get("estado").toUpperCase());
			Integer cantidad = Integer.parseInt(dato.get("cantidad_personas"));
			Date fechaCreacion = Utils.stringToDate(dato.get("fecha_creacion"));
			Date fechaLlegada = Utils.stringToDate(dato.get("fecha_llegada"));
			Date fechaSalida = Utils.stringToDate(dato.get("fecha_salida"));
			Titular titular = titulares.get(dato.get("titular_dni"));
			Long estadiaId = Long.parseLong(dato.get("numero_estadia"));
			Estadia estadia = new Estadia(estadiaId);
			List<Habitacion> habitacionesDeLaReserva = reservasHabitaciones.get(id)
					.stream()
					.map(idHabitacion -> habitaciones.get(idHabitacion))
					.toList();
			reservas.put(id, new Reserva(
					id,
					tarifaTotal, 
					estado, 
					cantidad, 
					fechaLlegada, 
					fechaSalida, 
					fechaCreacion, 
					titular, 
					estadia, 
					habitacionesDeLaReserva
			));
		}
		return reservas;
	}

	public Map<Long, Estadia> cargarEstadias(Map<Long, Reserva> reservas) throws Exception {
		Map<Long, Estadia> estadias = new HashMap<>();
		Map<String, Titular> huespedes = cargarTitulares();
		List<Map<String, String>> huespedesPorEstadia = FileManager.cargarArchivoCSV("huespedes_estadias.csv");
		List<Map<String, String>> data = FileManager.cargarArchivoCSV("estadias.csv");
		for (Map<String, String> dato : data) {
			Long id = Long.parseLong(dato.get("id"));
			Long idReserva = Long.parseLong(dato.get("numero_reserva"));
			Reserva reserva = reservas.get(idReserva);
			Date fechaIngreso = Utils.stringToDate(dato.get("fecha_ingreso"));
			Date fechaSalida = Utils.stringToDate(dato.get("fecha_salida"));
			Long facturaTotalId = Long.parseLong(dato.get("factura_id"));
			Factura facturaTotal = null;
			List<Huesped> huespedesEstadia = new ArrayList<>();
			huespedesPorEstadia.stream()
				.forEach(row -> {
					if (row.get("numero_estadia").equals(id.toString())) 
						huespedesEstadia.add(huespedes.get(row.get("dni_huesped")));
				});
			Estadia estadia = new Estadia(
					id,
					reserva,
					fechaIngreso,
					fechaSalida,
					facturaTotal,
					new ArrayList<>(),
					huespedesEstadia
			);
			estadias.put(id, estadia);
			reserva.setEstadia(estadia);
		}
		return estadias;
	}
	
	public Hotel cargarHotel() throws Exception {
		List<Map<String, String>> datosHotel = FileManager.cargarArchivoCSV("caracteristicas_hotel.csv");
		Map<String, String> datos = datosHotel.get(0);
		Boolean conParqueaderoIncluido = Boolean.parseBoolean(datos.get("parqueadero"));
		Boolean conPiscina = Boolean.parseBoolean(datos.get("piscina"));
		Boolean conZonasHumedas = Boolean.parseBoolean(datos.get("zonas_humedas"));
		Boolean conBBQ = Boolean.parseBoolean(datos.get("bbq"));
		Boolean conWifi = Boolean.parseBoolean(datos.get("wifi"));
		Boolean conRecepcion24Horas = Boolean.parseBoolean(datos.get("recepcion_24hrs"));
		Boolean admiteMascotas = Boolean.parseBoolean(datos.get("mascotas"));
		return new Hotel(conParqueaderoIncluido, conPiscina, conZonasHumedas, conBBQ, conWifi, conRecepcion24Horas, admiteMascotas);
	}
	
	public Map<String, PasarelaPago> cargarPasarelas() throws Exception {
		Map<String, PasarelaPago> pasarelas = new HashMap<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("pasarelas.csv");
		for (Map<String, String> row : datos) {
			Class clazz = Class.forName(row.get("class_name"));
			PasarelaPago pasarela = (PasarelaPago) clazz.getDeclaredConstructor(null).newInstance();
			pasarelas.put(pasarela.getName(), pasarela);
		}
		return pasarelas;
	}
	
	public Map<Long, Producto> cargarProductos() throws Exception {
		Map<Long, Producto> productosCargados = new HashMap<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("productos.csv");
		for (Map<String, String> dato : datos) {
			Long id = Long.parseLong(dato.get("id"));
		    String nombre = dato.get("nombre");
		    Double precio = Double.parseDouble(dato.get("precio"));
		    Producto producto = new Producto(id, nombre, precio);
		    productosCargados.put(id, producto);
		}
		return productosCargados;
	}

	public Map<Long, Servicio> cargarServicios() throws Exception {
        HashMap<Long, Servicio> serviciosCargados = new HashMap<>();
        Restaurante restaurante = cargarServicioRestaurante();
        Spa spa = cargarServicioSpa();
        serviciosCargados.put(restaurante.getId(), restaurante);
        serviciosCargados.put(spa.getId(), spa);
        return serviciosCargados;
    }

	public Spa cargarServicioSpa() throws Exception {
        List<Consumible> productosSpa = new ArrayList<>();
        List<Map<String, String>> datosSpa = FileManager.cargarArchivoCSV("productos_spa.csv");
        for (Map<String, String> dato : datosSpa) {
            Long id = Long.parseLong(dato.get("id"));
            String nombre = dato.get("nombre");
            Double precio = Double.parseDouble(dato.get("precio"));
            productosSpa.add(new Producto(id, nombre, precio));
        }
        return new Spa(Utils.generateId(), productosSpa);
	}

	public Restaurante cargarServicioRestaurante() throws Exception {
        List<Consumible> productosRestaurante = new ArrayList<>();
        List<Map<String, String>> datosRestaurante = FileManager.cargarArchivoCSV("productos_restaurante.csv");
        for (Map<String, String> dato : datosRestaurante) {
            Long id = Long.parseLong(dato.get("id"));
            String nombre = dato.get("nombre");
            Double precio = Double.parseDouble(dato.get("precio"));
            String fechaInicial = dato.get("fecha_inicial");
            String fechaFinal = dato.get("fecha_final");
            List<Date> fechas = new ArrayList<>();
            fechas.add(Utils.stringToDate(fechaInicial));
            fechas.add(Utils.stringToDate(fechaFinal));
            Boolean alCuarto = Boolean.parseBoolean(dato.get("al_cuarto"));
            String tipo = dato.get("tipo");
            ProductoRestaurante producto = new ProductoRestaurante(id, nombre, precio, fechas, alCuarto, tipo);
            productosRestaurante.add(producto);
        }
        return new Restaurante(Utils.generateId(), productosRestaurante);
	}
	
	public Map<String, Titular> cargarTitulares() throws Exception {
		Map<String, Titular> titulares = new HashMap<>();
		List<Map<String, String>> data = FileManager.cargarArchivoCSV("huespedes.csv");
		for (Map<String, String> dato : data) {
			String dni = dato.get("dni");
			String nombre = dato.get("nombre");
			Integer edad = Integer.parseInt(dato.get("edad"));
			String email = dato.get("email");
			String telefono = dato.get("telefono");
			titulares.put(dni, new Titular(nombre, dni, edad, email, telefono));
		}
		return titulares;
	}
	
	public Map<Long, List<Integer>> cargarReservasHabitaciones() throws Exception {
		Map<Long, List<Integer>> reservasHabitaciones = new HashMap<>();
		List<Map<String, String>> data = FileManager.cargarArchivoCSV("reservas_habitaciones.csv");
		for (Map<String, String> dato : data) {
			Long reserva = Long.parseLong(dato.get("numero_reserva"));
			Integer habitacion = Integer.parseInt(dato.get("numero_habitacion"));
			if (!reservasHabitaciones.containsKey(reserva))
				reservasHabitaciones.put(reserva, new ArrayList<>());
			reservasHabitaciones.get(reserva).add(habitacion);
		}
		return reservasHabitaciones;
	}
	
	public Map<Long, Long> cargarDisponibilidadesReservas() throws Exception {
		Map<Long, Long> disponibilidadesReservas = new HashMap<>();
		List<Map<String, String>> data = FileManager.cargarArchivoCSV("reservas_disponibilidades.csv");
		for (Map<String, String> dato : data) {
			Long disponibilidad = Long.parseLong(dato.get("id_disponibilidad"));
			Long reserva = Long.parseLong(dato.get("id_reserva"));
			disponibilidadesReservas.put(disponibilidad, reserva);
		}
		return disponibilidadesReservas;
	}
}
