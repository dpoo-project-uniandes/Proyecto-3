package hotel_system.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import hotel_system.utils.Utils;

public class Factura {

	private Long id;
	private Huesped titular;
	private Double valorTotal;
	private Pago pago;
	private Date date;
	private List<Consumible> consumibles;

	public Factura(Huesped titular, List<Consumible> consumibles) {
		this.id = Utils.generateId();
		this.titular = titular;
		this.consumibles = consumibles;
		this.date = Utils.nowDate();
		this.valorTotal = calcularValorTotal();
	}

	public Factura(Huesped titular) {
		this.id = Utils.generateId();
		this.titular = titular;
		this.valorTotal = 0.0;
		this.consumibles = new ArrayList<>();
		this.date = Utils.nowDate();
	}

	public Double calcularValorTotal() {
		double valorTotal = 0.0;
		for (Consumible consumible : this.consumibles) {
			valorTotal += consumible.getPrecio();
		}
		return valorTotal;
	}

	public void procesarPago() {
		this.pago = new Pago(valorTotal);
	}

	public void agregarConsumible(Consumible consumible) {
		this.consumibles.add(consumible);
	}

	public void eliminarConsumible(Consumible consumible) {
		this.consumibles.remove(consumible);
	}
	
	public Long getId() {
		return id;
	}

	public Huesped getTitular() {
		return titular;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public Pago getPago() {
		return pago;
	}
	
	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public List<Consumible> getConsumibles() {
		return consumibles;
	}
	
	public Date getDate() {
		return date;
	}
}
