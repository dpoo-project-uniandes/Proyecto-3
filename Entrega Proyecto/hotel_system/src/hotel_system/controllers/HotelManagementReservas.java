package hotel_system.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hotel_system.models.EstadoReserva;
import hotel_system.models.Habitacion;
import hotel_system.models.Reserva;
import hotel_system.models.TipoHabitacion;
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
		Reserva reserva = construirReserva(nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);
		reservas.put(reserva.getNumero(), reserva);
		persistirReserva(reserva);
	}
	
	public void actualizarReserva(
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
		// Archivo de reservas
		Reserva reserva = construirReserva(nombre, email, dni, telefono, edad, cantidad, llegada, salida, habitacionesElegidas);
		reservas.put(reserva.getNumero(), reserva);
		List<String> datos = reservaToListString(reserva);
		FileManager.modificarLineaCSV("reservas.csv", datos);
		
		// Archivo de Titular
		List<String> titular = titularToListString(reserva.getTitular());
		FileManager.modificarLineaCSV("huespedes.csv", titular);

		// Archivo de reservas y habitaciones
		List<List<String>> reservasYHabitaciones = reservaHabitacionesToListString(reserva);
		for (List<String> row : reservasYHabitaciones) {
			FileManager.modificarLineaCSV("reservas_habitaciones.csv", row);	
		}
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
		reserva.setEstado(EstadoReserva.CANCELADO);
		FileManager.modificarLineaCSV("reservas.csv", reservaToListString(reserva));
	}
	
	public void eliminarReserva(Long id) throws Exception {
		// Archivo de reservas
		Reserva reserva = this.reservas.get(id);
		if (reserva == null) 
			return;
		List<String> datos = reservaToListString(reserva);
		this.reservas.remove(id);
		FileManager.removerLineaCSV("reservas.csv", datos);
		
		// Archivo de reservas y habitaciones
		FileManager.removerLineaCSV("reservas_habitaciones.csv", reservaHabitacionesToListString(reserva).get(0));
	}
	
	private Reserva construirReserva(
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
		Reserva reserva = new Reserva(llegada, salida, titular, cantidad, habitaciones);
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
}
