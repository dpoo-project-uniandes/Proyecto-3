package hotel_system.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hotel_system.models.Disponibilidad;
import hotel_system.models.Estadia;
import hotel_system.models.Habitacion;
import hotel_system.models.Hotel;
import hotel_system.models.Producto;
import hotel_system.models.ProductoRestaurante;
import hotel_system.models.Reserva;
import hotel_system.models.Restaurante;
import hotel_system.models.Rol;
import hotel_system.models.Servicio;
import hotel_system.models.Spa;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Usuario;
import hotel_system.utils.Utils;
import services.FileManager;

public class HotelManagementLoaderData {
	
	public void limpiarDisponibilidades() throws Exception {
		FileManager.eliminarArchivo("disponibilidades.csv");
		FileManager.agregarLineasCSV("disponibilidades.csv", List.of(List.of("numero_habitacion","fecha","estado","precio")));
	}
	
	
	public List<TipoHabitacion> cargarTipoHabitaciones() throws Exception {
		List<TipoHabitacion> opcionesHabitaciones = new ArrayList<>();
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
			opcionesHabitaciones.add(tipoHabitacion);
		}
		return opcionesHabitaciones;
	}
	
	public List<Disponibilidad> cargarDisponibilidad(Integer numeroHabitacion, Double precio) throws Exception {
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
	
	public List<Habitacion>  cargarHabitaciones(List<TipoHabitacion> opcionesHabitacion, Hotel hotel) throws Exception{
		List<Habitacion> habitaciones = new ArrayList<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("habitaciones.csv");
		for(Map<String, String> dato : datos) {
			Integer numeroHabitacion = Integer.parseInt(dato.get("numero_habitacion"));
			TipoHabitacion tipo = opcionesHabitacion.stream()
					.filter(th -> th.getAlias().equals(dato.get("tipo_habitacion")))
					.findAny()
					.get();
			List<Disponibilidad> disponibilidad = cargarDisponibilidad(numeroHabitacion, tipo.getPrecio());
			Habitacion habitacion = new Habitacion(numeroHabitacion, tipo, disponibilidad, hotel);
			habitaciones.add(habitacion);
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

	public List<Reserva> cargarReservas() throws Exception{
		FileManager.eliminarArchivo("reservas.csv");
		FileManager.agregarLineasCSV("reservas.csv", List.of(List.of("numero","tarifaTotal","estado","cantidadPersonas","fechaCreacion","fechaLlegada","fechaSalida","titular_dni","numero_estadia")));
		FileManager.eliminarArchivo("reservas_habitaciones.csv");
		FileManager.agregarLineasCSV("reservas_habitaciones.csv", List.of(List.of("numero_reserva", "numero_habitacion")));
		return new ArrayList<>();
	}

	public List<Estadia> cargarEstadias(){
		return new ArrayList<>();
	}
	
	public List<Producto> cargarProductos() throws Exception {
		List<Producto> productosCargados = new ArrayList<>();
		List<Map<String, String>> datos = FileManager.cargarArchivoCSV("productos.csv");
		for (Map<String, String> dato : datos) {
			Long id = Long.parseLong(dato.get("id"));
		    String nombre = dato.get("nombre");
		    Double precio = Double.parseDouble(dato.get("precio"));
		    Producto producto = new Producto(id, nombre, precio);
		    productosCargados.add(producto);
		}
		return productosCargados;
	}

	public Map<String, Servicio> cargarServicios() throws Exception {
        HashMap<String, Servicio> serviciosCargados = new HashMap<>();
        Restaurante restaurante = cargarServicioRestaurante();
        Spa spa = cargarServicioSpa();

        serviciosCargados.put("restaurante", restaurante);
        serviciosCargados.put("spa", spa);

        return serviciosCargados;
    }

	private Spa cargarServicioSpa() throws Exception {
        List<Producto> productosSpa = new ArrayList<>();
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
}
