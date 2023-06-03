package hotel_system.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hotel_system.utils.Utils;

public class Habitacion {

	private Integer numero;
	private TipoHabitacion tipo;
	private List<Disponibilidad> disponibilidad;
	private Hotel hotel;

	public Habitacion(Integer numero, TipoHabitacion tipo, List<Disponibilidad> disponibilidad, Hotel hotel) {
		super();
		this.numero = numero;
		this.tipo = tipo;
		this.disponibilidad = disponibilidad;
		this.hotel = hotel;
	}

	private List<Integer> rangoDeFechasAIndices(Date desde, Date hasta) {
		List<Integer> indexes = new ArrayList<>();
		Long idDesde = desde.getTime() + numero;
		Long idHasta = hasta.getTime() + numero;
		for (int i = 0; i < this.disponibilidad.size(); i++) {
			Disponibilidad disponibilidad = this.disponibilidad.get(i);
			if (disponibilidad.getId() >= idDesde && disponibilidad.getId() < idHasta)
				indexes.add(i);
		}
		return indexes;
	}

	public Boolean consultarDisponibilidad(Date desde, Date hasta) {
		for(Integer i : rangoDeFechasAIndices(desde, hasta)) {
			if(!this.disponibilidad.get(i).getEstado()) {
				break;
			}
			return true;
		}
		return false;
	}

	public List<Disponibilidad> modificarDisponibilidad(Date desde, Date hasta, Reserva reserva, Boolean estado) {
		if (consultarDisponibilidad(desde, hasta) || estado) {
			List<Disponibilidad> disponibilidades = new ArrayList<>();
			for(Integer i : rangoDeFechasAIndices(desde, hasta)) {
				Disponibilidad disponibilidad = this.disponibilidad.get(i);
				disponibilidad.setEstado(estado);
				disponibilidad.setReserva(reserva);
				disponibilidades.add(disponibilidad);
			}
			return disponibilidades.isEmpty()? null : disponibilidades;
		}
		return null;
	}

	public Double calcularTarifa(Date desde, Date hasta) {
		double tarifa = 0.0;
		for(Integer i : rangoDeFechasAIndices(desde, hasta)) {
			Disponibilidad disponibilidad = this.disponibilidad.get(i);
			tarifa += disponibilidad.getPrecio();
		}
		return tarifa;
	}

	@Override
	public String toString() {
		return "Habitacion [numero=" + numero + ", tipo=" + tipo + ", hotel="
				+ hotel + "]";
	}

	public void agregarDisponibilidad(Disponibilidad disponibilidad) {
		this.disponibilidad.add(disponibilidad);
	}

	public Integer getNumero() {
		return numero;
	}

	public TipoHabitacion getTipo() {
		return tipo;
	}

	public Reserva getReservaActual() {
		try {
		  return disponibilidad.stream()
				.filter(dis -> Utils.stringLocalDate(dis.getFecha()).equals(Utils.stringLocalDate(Utils.nowDate())))
				.findAny()
				.get()
				.getReserva();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Hotel getHotel() {
		return hotel;
	}
}
