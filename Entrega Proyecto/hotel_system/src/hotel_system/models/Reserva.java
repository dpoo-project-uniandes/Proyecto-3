package hotel_system.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hotel_system.utils.Utils;

public class Reserva extends Servicio {

	private Long numero;
	private Double tarifaTotal;
	private EstadoReserva estado;
	private Integer cantidadPersonas;
	private Date fechaDeLlegada;
	private Date fechaDeSalida;
	private Date fechaDeCreacion;
	private Titular titular;
	private Estadia estadia;
	private List<Habitacion> habitaciones;
	private Factura factura;

	public Reserva(java.util.Date fechaDeLlegada2, java.util.Date fechaDeSalida2, Titular titular, Integer cantidad, List<Habitacion> habitaciones) {
		super(Utils.generateId());
		this.numero = this.id;
		this.fechaDeLlegada = (Date) fechaDeLlegada2;
		this.fechaDeCreacion = Utils.nowDate();
		this.cantidadPersonas = cantidad;
		this.fechaDeSalida = (Date) fechaDeSalida2;
		this.titular = titular;
		this.habitaciones = habitaciones;
		this.tarifaTotal = calcularTarifaTotal();
		this.estado = EstadoReserva.CONFIRMADA;
	}
	
	public Reserva(Long id, java.util.Date fechaDeLlegada2, java.util.Date fechaDeSalida2, Titular titular, Integer cantidad, List<Habitacion> habitaciones) {
		super(id == null ? Utils.generateId() : id);
		this.numero = this.id;
		this.fechaDeLlegada = (Date) fechaDeLlegada2;
		this.fechaDeCreacion = Utils.nowDate();
		this.cantidadPersonas = cantidad;
		this.fechaDeSalida = (Date) fechaDeSalida2;
		this.titular = titular;
		this.habitaciones = habitaciones;
		this.tarifaTotal = calcularTarifaTotal();
		this.estado = EstadoReserva.CONFIRMADA;
	}

	public Reserva(Long numero, Double tarifaTotal, EstadoReserva estado, Integer cantidadPersonas, java.util.Date fechaDeLlegada2,
			java.util.Date fechaDeSalida2, java.util.Date fechaDeCreacion2, Titular titular, Estadia estadia, List<Habitacion> habitaciones) {
		super(numero);
		this.numero = this.id;
		this.tarifaTotal = tarifaTotal;
		this.estado = estado;
		this.cantidadPersonas = cantidadPersonas;
		this.fechaDeLlegada = (Date) fechaDeLlegada2;
		this.fechaDeSalida = (Date) fechaDeSalida2;
		this.fechaDeCreacion = (Date) fechaDeCreacion2;
		this.titular = titular;
		this.estadia = estadia;
		this.habitaciones = habitaciones;
	}
	
	public void cerrar() {
		this.estado = EstadoReserva.CERRADA;
		this.tarifaTotal = calcularTarifaTotal();
	}

	public Double calcularTarifaTotal() {
		double valorTotal = 0.0;
		for (Habitacion hab: habitaciones) {
			valorTotal += hab.calcularTarifa(fechaDeLlegada, fechaDeSalida);
		}
		return valorTotal;
	}

	public void confirmarReserva() {
		this.estado = EstadoReserva.CONFIRMADA;
		for (Habitacion hab: habitaciones) {
			hab.modificarDisponibilidad(fechaDeLlegada, fechaDeSalida, this, false);
		}
	}

	public void cancelarReserva() {
		this.estado = EstadoReserva.CANCELADA;
		for (Habitacion hab: habitaciones) {
			hab.modificarDisponibilidad(fechaDeLlegada, fechaDeSalida, null, true);
		}
	}
	
	public Boolean estaActiva() {
		return estado != EstadoReserva.CANCELADA && estado != EstadoReserva.CERRADA;
	}

	@Override
	public String toString() {
		return "Reserva [numero=" + numero +", estado="+estado.toString()+", tarifa total=" + tarifaTotal + ", cantidad de personas="
					+ cantidadPersonas+", fecha de creación="+ Utils.stringLocalDate(fechaDeCreacion)
					+", fecha de llegada="+Utils.stringLocalDate(fechaDeLlegada)+", fecha de salida=" + Utils.stringLocalDate(fechaDeSalida) +"]";
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Double getTarifaTotal() {
		return tarifaTotal;
	}

	public void setTarifaTotal(Double tarifaTotal) {
		this.tarifaTotal = tarifaTotal;
	}

	public EstadoReserva getEstado() {
		return estado;
	}

	public void setEstado(EstadoReserva estado) {
		this.estado = estado;
	}

	public Integer getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(Integer cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public Date getFechaDeLlegada() {
		return fechaDeLlegada;
	}

	public void setFechaDeLlegada(Date fechaDeLlegada) {
		this.fechaDeLlegada = fechaDeLlegada;
	}

	public Date getFechaDeSalida() {
		return fechaDeSalida;
	}

	public void setFechaDeSalida(Date fechaDeSalida) {
		this.fechaDeSalida = fechaDeSalida;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public Titular getTitular() {
		return titular;
	}

	public void setTitular(Titular titular) {
		this.titular = titular;
	}

	public Estadia getEstadia() {
		return estadia;
	}

	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}

	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}
	
	public Boolean estaPaga() {
		return factura != null ? (factura.getPago() != null) : false;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}
	
	@Override
	public Factura facturar(Huesped huesped) {
		this.productosConsumidos.addAll(getConsumibles());
		return super.facturar(titular);
	}

	@SuppressWarnings("unchecked")
	public List<Consumible> getConsumibles() {
		if (habitaciones.isEmpty())
			return new ArrayList<>();
		return (List<Consumible>) ((List<?>)habitaciones.stream()
				.map(habitacion -> {
					return new Producto(
							Integer.toUnsignedLong(habitacion.getNumero()), 
							habitacion.getTipo().getAlias(), 
							habitacion.calcularTarifa(fechaDeLlegada, fechaDeSalida));
				}).toList());
	}
}
