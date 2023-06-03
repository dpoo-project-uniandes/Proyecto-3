package hotel_system.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hotel_system.models.Estadia;
import hotel_system.models.Habitacion;
import hotel_system.models.Huesped;
import hotel_system.models.Reserva;
import hotel_system.utils.Utils;
import services.FileManager;

public class HotelManagementEstadias {
	
	private Map<Integer, Habitacion> inventarioHabitaciones;
	private Map<Long, Estadia> estadias;
	
	private HotelManagementReservas controladorReservas;
	
	public HotelManagementEstadias(
			Map<Integer, Habitacion> inventarioHabitaciones, 
			Map<Long, Estadia> estadias,
			HotelManagementReservas controladorReservas
		) {
		super();
		this.inventarioHabitaciones = inventarioHabitaciones;
		this.estadias = estadias;
		this.controladorReservas = controladorReservas;
	}
	
	public Estadia iniciarEstadia(Reserva reserva, List<Huesped> huespedes) throws Exception {
		// Construccion Estadia
		Estadia estadia = new Estadia(reserva, reserva.getFechaDeLlegada(), reserva.getFechaDeSalida(), huespedes);
		
		// Archivo Estadias
		FileManager.agregarLineasCSV("estadias.csv", Arrays.asList(estadiaToListString(estadia)));
		
		// Archivo Huespedes
		List<List<String>> huespedesCSV = huespedes.stream().map(huesped -> huespedToListString(huesped)).toList();
		FileManager.agregarLineasCSV("huespedes.csv", huespedesCSV);
		
		// Archivo Huespedes & Reservas
		List<List<String>> huespedesPorEstadia = huespedes.stream()
				.map(huesped -> Arrays.asList(huesped.getDni(), reserva.getNumero().toString(), estadia.getId().toString()))
				.toList();
		FileManager.agregarLineasCSV("huespedes_estadias.csv", huespedesPorEstadia);
		
		// Estadias
		estadias.put(estadia.getId(), estadia);
		
		// Actualizacion Reserva
		controladorReservas.setEstadiaReserva(reserva.getNumero(), estadia);
		
		return estadia;
	}
	
	public void actualizarEstadia(Estadia estadia) throws Exception {
		FileManager.modificarLineaCSV("estadias.csv", "id", estadia.getId().toString(), estadiaToListString(estadia));
	}

	public Estadia getEstadiaById(Long id) {		
		return estadias.get(id);
	}

	public Estadia getEstadiaByTitular(String titular) {
		Optional<Estadia> estadia = estadias.values().stream()
				.filter(reg -> reg.getReserva().getTitular().getNombre().equals(titular))
				.findAny();
		if (estadia.isPresent()) 
			return estadia.get();
		else 
			return null;
	}

	public Estadia getEstadiaByDNI(String dni) {
		Optional<Estadia> estadia = estadias.values().stream()
				.filter(estadia1 -> estadia1.getReserva().getTitular().getDni().equals(dni))
				.findAny();
		if (estadia.isPresent()) 
			return estadia.get();
		else 
			return null;
	}
	
	public Estadia getEstadiaByHabitacion(Integer id) {
		Optional<Habitacion> habitacion = inventarioHabitaciones.values().stream()
				.filter(hab -> hab.getNumero().equals(id))
				.findAny();
		if (habitacion.isPresent()) {
			return habitacion.get().getReservaActual().getEstadia();	
		}
		else 
			return null;
	}
	
	private List<String> estadiaToListString(Estadia estadia) {
		return Arrays.asList(
				estadia.getId().toString(),
				estadia.getReserva().getNumero().toString(),
				Utils.stringLocalDate(estadia.getFechaIngreso()),
				Utils.stringLocalDate(estadia.getFechaSalida()),
				estadia.getFacturaTotal() != null ? estadia.getFacturaTotal().getId().toString() : "0"
		);
	}
	
	private List<String> huespedToListString(Huesped huesped) {
		return Arrays.asList(
				huesped.getDni(),
				huesped.getNombre(),
				huesped.getEdad().toString(),
				"",
				""
		);
	}
}
