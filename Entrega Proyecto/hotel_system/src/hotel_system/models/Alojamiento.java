package hotel_system.models;

import java.util.List;

import hotel_system.utils.Utils;

public class Alojamiento extends Servicio {

	public Alojamiento(Reserva reserva) {
        super(Utils.generateId(), getConsumo(reserva));
    }

	@SuppressWarnings("unchecked")
	private static List<Consumible> getConsumo(Reserva reserva) {
		return (List<Consumible>) ((List<?>)reserva.getHabitaciones().stream()
				.map(habitacion -> {
					Estadia estadia = reserva.getEstadia();
					return new Producto(
							Integer.toUnsignedLong(habitacion.getNumero()), 
							habitacion.getTipo().getAlias(), 
							habitacion.calcularTarifa(estadia.getFechaIngreso(), estadia.getFechaSalida()));
				}).toList());
    }
}
