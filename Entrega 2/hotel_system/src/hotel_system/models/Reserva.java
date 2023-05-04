package hotel_system.models;

import java.sql.Date;
import java.util.List;

import hotel_system.utils.Utils;

public class Reserva {
	
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
	
	public Reserva(Date fechaDeLlegada, Date fechaDeSalida, Titular titular, Integer cantidad,List<Habitacion> habitaciones) {
		this.numero = Utils.generateId();
		this.fechaDeLlegada = fechaDeLlegada;
		this.fechaDeCreacion = Utils.nowDate();
		this.cantidadPersonas = cantidad;
		this.fechaDeSalida = fechaDeSalida;
		this.titular = titular;
		this.habitaciones = habitaciones;
		this.tarifaTotal = calcularTarifaTotal();
		confirmarReserva();
	}
	
	public Double calcularTarifaTotal() {
		Double valorTotal = 0.0;
		for (Habitacion hab: habitaciones) {
			valorTotal += hab.calcularTarifa(fechaDeLlegada, fechaDeSalida);
		}
		return valorTotal;
	}
	
	public void confirmarReserva() {
		this.estado = EstadoReserva.CONFIRMADO;
		for (Habitacion hab: habitaciones) {
			hab.modificarDisponibilidad(fechaDeLlegada, fechaDeSalida, this, false);
		}
	}
	
	public void cancelarReserva() {
		this.estado = EstadoReserva.CANCELADO;
		for (Habitacion hab: habitaciones) {
			hab.modificarDisponibilidad(fechaDeLlegada, fechaDeSalida, null, true);
		}
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
}
