package hotel_system.models;

import java.util.ArrayList;
import java.util.List;

import hotel_system.utils.Utils;

public class Factura {
	
	private Huesped titular;
	private Double valorTotal;
	private Pago pago;
	private List<Consumible> consumibles;
	
	public Factura(Huesped titular, List<Consumible> consumibles) {
		this.titular = titular;
		this.consumibles = consumibles;
		calcularValorTotal();
	}
	
	public Factura(Huesped titular) {
		this.titular = titular;
		this.valorTotal = 0.0;
		this.consumibles = new ArrayList<>();
	}
	
	public void calcularValorTotal() {
		Double valorTotal = 0.0;
		for (Consumible consumible : this.consumibles) {
			valorTotal += consumible.valor();
		}
		this.valorTotal = valorTotal;
	}
	
	public String generarFactura() {
		return String.format("%s;%s;%s;%s;%.2f;%b",
				this.titular.getNombre(),
				this.titular.getDni(),
				Utils.stringLocalDate(Utils.nowDate()), 
				consumibles.toString(), 
				valorTotal,
				pago != null);
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

	public Huesped getTitular() {
		return titular;
	}

	public void setTitular(Huesped titular) {
		this.titular = titular;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
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

	public void setConsumibles(List<Consumible> consumibles) {
		this.consumibles = consumibles;
	}
}
