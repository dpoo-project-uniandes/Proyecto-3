package hotel_system.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import hotel_system.models.Disponibilidad;
import hotel_system.models.Estadia;
import hotel_system.models.EstadoReserva;
import hotel_system.models.Habitacion;
import hotel_system.models.Reserva;
import hotel_system.models.Titular;
import hotel_system.utils.Utils;
import services.FileManager;

public class HotelManagementReservas {
	
	private Map<Integer, Habitacion> inventarioHabitaciones;
	private Map<Long, Reserva> reservas;

	public HotelManagementReservas(Map<Integer, Habitacion> inventarioHabitaciones, Map<Long, Reserva> reservas) {
		super();
		this.inventarioHabitaciones = inventarioHabitaciones;
		this.reservas = reservas;
	}

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
		
		if (id != null) {
			actualizarReserva(id, nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);
			return;
		}
		
		Reserva reserva = construirReserva(null, nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);		
		procesarDisponibilidades(reserva, false);
		
		reservas.put(reserva.getNumero(), reserva);
		
		persistirReserva(reserva);
	}
	
	public void actualizarReserva(
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
		// Construccion y validacion de la reserva
		Reserva reservaOriginal = reservas.get(id);
		if (reservaOriginal == null)
			throw new Exception("reserva " + id.toString() + " no encontrada en el registro");
		
		Reserva reserva = construirReserva(id, nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);
		
		// Disponibilidades
		if (llegada != reservaOriginal.getFechaDeLlegada() || salida != reservaOriginal.getFechaDeSalida()) {
			// Remover disponibilidad ocupadas
			procesarDisponibilidades(reservaOriginal, true);

			// Guardar nuevas disponibilidades
			procesarDisponibilidades(reserva, false);
		}
		
		// Archivo de reservas
		List<String> datos = reservaToListString(reserva);
		FileManager.modificarLineaCSV("reservas.csv", "numero", id.toString(), datos);
		
		// Archivo de Titular
		List<String> titular = titularToListString(reserva.getTitular());
		FileManager.modificarLineaCSV("huespedes.csv", "dni", reserva.getTitular().getDni(), titular);

		// Archivo de reservas y habitaciones
		for(Habitacion habitacion: reservaOriginal.getHabitaciones()) {
			FileManager.removerLineaCSV("reservas_habitaciones.csv", "numero_reserva", id.toString());
		}
		
		List<List<String>> reservasYHabitaciones = reservaHabitacionesToListString(reserva);
		FileManager.agregarLineasCSV("reservas_habitaciones.csv", reservasYHabitaciones);
		
		// Actualizar la reserva en el mapa
		reservas.put(id, reserva);
	}
	
	public Habitacion seleccionarHabitacionDisponible(String tipo, Date desde, Date hasta) {
		return inventarioHabitaciones.values().stream()
				.filter(hab -> hab.getTipo().getAlias().equals(tipo) && hab.consultarDisponibilidad(desde, hasta))
				.findAny()
				.get();
	}
	
	public Integer calcularCapacidadHabitaciones(List<Habitacion> habitaciones) {
		return habitaciones.stream()
				.map(h -> h.getTipo().getCapacidad())
				.reduce(0, (val, accumulator) -> accumulator + val);
	}
	
	public Reserva getReservaById(Long id) {
		return reservas.get(id);
	}
	
	public Reserva getReservaByDNI(String dni) {
		Optional<Reserva> reservacion = reservas.values().stream()
				.filter(res -> res.getTitular().getDni().equals(dni))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get();}
		else {return null;}
	}
	
	public Reserva getReservaByHabitacion(String id) {
		Optional<Habitacion> reservacion = inventarioHabitaciones.values().stream()
				.filter(hab -> (hab.getReservaActual().getNumero()+"").equals(id))
				.findAny();
		if (reservacion.isPresent()) {return reservacion.get().getReservaActual();}
		else {return null;}
	}
	
	public void cancelarReserva(Long id) throws Exception {
		Reserva reserva = reservas.get(id);
		reserva.setEstado(EstadoReserva.CANCELADA);
		procesarDisponibilidades(reserva, true);
		FileManager.modificarLineaCSV("reservas.csv", "numero", reserva.getNumero().toString(), reservaToListString(reserva));
	}
	
	public void cerrarReserva(Reserva reserva) throws Exception {
		reserva.setEstado(EstadoReserva.CERRADA);
		FileManager.modificarLineaCSV("reservas.csv", "numero", reserva.getNumero().toString(), reservaToListString(reserva));
	}
		
	
	public void eliminarReserva(Long id) throws Exception {
		// Archivo de reservas
		Reserva reserva = this.reservas.get(id);
		if (reserva == null) 
			return;
		this.reservas.remove(id);
		procesarDisponibilidades(reserva, true);
		FileManager.removerLineaCSV("reservas.csv", "numero", reserva.getNumero().toString());
		
		// Archivo de reservas y habitaciones
		for(Habitacion habitacion: reserva.getHabitaciones()) {
			FileManager.removerLineaCSV("reservas_habitaciones.csv", "numero_reserva", reserva.getNumero().toString());	
		}
	}
	
	public void setEstadiaReserva(Long idReserva, Estadia estadia ) throws Exception {
		Reserva reserva = this.reservas.get(idReserva);
		if (reserva == null) 
			return;
		reserva.setEstadia(estadia);
		FileManager.modificarLineaCSV("reservas.csv", "numero", reserva.getNumero().toString(), reservaToListString(reserva));
	}
	
	private Reserva construirReserva(
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
		) throws IOException {
		Titular titular = new Titular(nombre, dni, edad, email, telefono);
		List<Habitacion> habitaciones = new ArrayList<>(); 
		habitacionesElegidas.keySet().stream()
				.forEach(tipo -> IntStream.range(0, habitacionesElegidas.get(tipo))
						.forEach(i -> habitaciones.add(seleccionarHabitacionDisponible(tipo, llegada, salida))));
		if (calcularCapacidadHabitaciones(habitaciones) < cantidad) {
			throw new IOException("La cantidad de huespedes es mayor a la capacidad de las habitaciones"); 
		}
		Reserva reserva = new Reserva(id, llegada, salida, titular, cantidad, habitaciones);
		return reserva;
	}
	
	private void persistirReserva(Reserva reserva) throws Exception {
		// Archivo de reservas
		FileManager.agregarLineasCSV("reservas.csv", List.of(reservaToListString(reserva)));
		
		// Archivo de Titular
		FileManager.agregarLineasCSV("huespedes.csv", List.of(titularToListString(reserva.getTitular())));
		
		// Archivo de reservas y habitaciones
		FileManager.agregarLineasCSV("reservas_habitaciones.csv", reservaHabitacionesToListString(reserva));
	}
	
	private void procesarDisponibilidades(Reserva reserva, Boolean estado) throws Exception {
		// Archivo Disponibilidades
		List<List<Disponibilidad>> disponibilidades = new ArrayList<>();
		for (Habitacion habitacion : reserva.getHabitaciones()) {
			List<Disponibilidad> disponibilidadesModificadas = habitacion.modificarDisponibilidad(reserva.getFechaDeLlegada(), reserva.getFechaDeSalida(), reserva, estado);
			disponibilidades.add(disponibilidadesModificadas);
		}
		List<List<String>> disponibilidadesCSV = new ArrayList<>();
		for (List<Disponibilidad> lista : disponibilidades) {
			for (Disponibilidad disponibilidad : lista) {
				disponibilidadesCSV.add(disponibilidadToListString(disponibilidad));
			}
		}
		for (List<String> row : disponibilidadesCSV) {
			FileManager.modificarLineaCSV("disponibilidades.csv", "id", row.get(0), row);
			// Archivo reservas - disponibilidades
			if (estado) {
				FileManager.removerLineaCSV("reservas_disponibilidades.csv", "id_reserva", reserva.getNumero().toString());
			} else {
				FileManager.agregarLineasCSV("reservas_disponibilidades.csv", Arrays.asList(Arrays.asList(row.get(0), reserva.getNumero().toString())));
			}
		}
	}
	
	private List<List<String>> reservaHabitacionesToListString(Reserva reserva) {		
		return reserva.getHabitaciones().stream()
				.map(habitacion -> Arrays.asList(reserva.getNumero().toString(), habitacion.getNumero().toString()))
				.toList();
	}
	
	private List<String> reservaToListString(Reserva reserva) {
		return Arrays.asList(
				reserva.getNumero().toString(),
				reserva.getTarifaTotal().toString(),
				reserva.getEstado().toString(),
				reserva.getCantidadPersonas().toString(),
				Utils.stringLocalDate(reserva.getFechaDeCreacion()),
				Utils.stringLocalDate(reserva.getFechaDeLlegada()),
				Utils.stringLocalDate(reserva.getFechaDeSalida()),
				reserva.getTitular().getDni().toString(),
				reserva.getEstadia() != null ? reserva.getEstadia().getId().toString() : "0"
		);
	}
	
	private List<String> titularToListString(Titular titular) {
		return Arrays.asList(
				titular.getDni(),
				titular.getNombre(),
				titular.getEdad().toString(),
				titular.getEmail(),
				titular.getTelefono()
		);
	}
	
	private List<String> disponibilidadToListString(Disponibilidad disponibilidad) {
		return Arrays.asList(
				disponibilidad.getId().toString(),
				disponibilidad.getHabitacion().toString(),
				Utils.stringLocalDate(disponibilidad.getFecha()),
				disponibilidad.getEstado().toString(),
				disponibilidad.getPrecio().toString()
		);
	}
}
