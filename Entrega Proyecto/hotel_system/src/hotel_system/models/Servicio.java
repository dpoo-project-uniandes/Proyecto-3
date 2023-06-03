package hotel_system.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Servicio implements Facturable {

	protected Long id;
	protected List<Consumible> productosConsumidos;

	public Servicio(Long id) {
		super();
		this.id = id;
		this.productosConsumidos = new ArrayList<>();
	}
	
	public Servicio(Long id, List<Consumible> consumibles) {
		super();
		this.id = id;
		this.productosConsumidos = consumibles;
	}

	@Override
	public Factura facturar(Huesped titular) {
	    Factura factura = new Factura(titular, productosConsumidos);
	    factura.procesarPago();
	    return factura;
	}

	@Override
	public Double calcularTotal() {
		double valorTotal = 0.0;
		for (Consumible consumible : productosConsumidos) {
			valorTotal += consumible.getPrecio();
		}
		return valorTotal;
	}

	@Override
	public void addConsumible(Consumible consumible) {
		this.productosConsumidos.add(consumible);
	}

	@Override
	public void removeConsumible(Consumible consumible) {
		this.productosConsumidos.remove(consumible);
	}

	@Override
	public List<Consumible> getConsumibles() {
		return productosConsumidos;
	}
	
	public Long getId() {
		return id;
	}

	public void deleteById(Long id) {
		this.productosConsumidos.removeIf(consumible -> consumible.getId() == id);
	}
}
