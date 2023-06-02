package hotel_system.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import hotel_system.models.Estadia;
import hotel_system.models.Habitacion;
import hotel_system.models.Huesped;
import hotel_system.models.Reserva;

public class HotelManagementEstadias {
	
	private Map<Integer, Habitacion> inventarioHabitaciones;
	private Map<Long, Estadia> estadias;
	
	public HotelManagementEstadias(Map<Integer, Habitacion> inventarioHabitaciones, Map<Long, Estadia> estadias) {
		super();
		this.inventarioHabitaciones = inventarioHabitaciones;
		this.estadias = estadias;
	}
	
	public void iniciarEstadia(Reserva reserva, List<Huesped> huespedes) {
		Estadia estadia = new Estadia(reserva, reserva.getFechaDeLlegada(), reserva.getFechaDeSalida(), huespedes);
		reserva.setEstadia(estadia);
		estadias.put(estadia.getId(), estadia);
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
		if (habitacion.isPresent()) 
			return habitacion.get().getReservaActual().getEstadia();
		else 
			return null;
	}
}
