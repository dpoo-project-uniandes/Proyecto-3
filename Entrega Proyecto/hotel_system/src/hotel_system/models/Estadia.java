package hotel_system.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hotel_system.utils.Utils;

public class Estadia extends Servicio {

	Reserva reserva;
	Date fechaIngreso;
	Date fechaSalida;
	Factura facturaTotal;
	List<Factura> facturas;
	List<Huesped> huespedes;
	
	public Estadia(Long id) {
		super(id);
	}
	
	public Estadia(Long id, Reserva reserva, Date fechaIngreso, Date fechaSalida, Factura facturaTotal,
			List<Factura> facturas, List<Huesped> huespedes) {
		super(id);
		this.id = id;
		this.reserva = reserva;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.facturaTotal = facturaTotal;
		this.facturas = facturas;
		this.huespedes = huespedes;
	}

	public Estadia(Reserva reserva, Date fechaIngreso, Date fechaSalida, List<Huesped> huespedes) {
		super(Utils.generateId());
		this.reserva = reserva;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.facturaTotal = new Factura(reserva.getTitular());
		this.huespedes = huespedes;
		this.facturas = new ArrayList<>();
	}
	
	@Override
	public Factura facturar(Huesped titular) {
		this.productosConsumidos.addAll(getConsumoHabitaciones());
		return super.facturar(titular);
	}
	
	@SuppressWarnings("unchecked")
	private List<Consumible> getConsumoHabitaciones() {
		return (List<Consumible>) ((List<?>)reserva.getHabitaciones().stream()
				.map(habitacion -> {
					Estadia estadia = reserva.getEstadia();
					return new Producto(
							Integer.toUnsignedLong(habitacion.getNumero()), 
							habitacion.getTipo().getAlias(), 
							habitacion.calcularTarifa(estadia.getFechaIngreso(), estadia.getFechaSalida()));
				}).toList());
    }

	public void cargarFactura(Factura factura) {
		this.facturas.add(factura);
	}

	public List<Huesped> getHuespedes() {
		return huespedes;
	}

	public Long getId() {
		return id;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public Factura getFacturaTotal() {
		return facturaTotal;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}
}
