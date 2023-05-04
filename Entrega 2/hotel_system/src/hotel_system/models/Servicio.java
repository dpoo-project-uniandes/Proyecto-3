package hotel_system.models;

import java.util.ArrayList;
import java.util.List;

public abstract class Servicio implements Consumible {
	
	protected Long id;
	protected List<Producto> productosConsumidos;
	
	public Servicio(Long id) {
		super();
		this.id = id;
		this.productosConsumidos = new ArrayList<>();
	}
	
	@Override
	public Factura facturar(Huesped titular) {
	    Factura factura = new Factura(titular, getConsumo());
	    factura.procesarPago();
	    return factura;
	}
	
	@Override
	public Double valor() {
		Double valorTotal = 0.0;
		for (Consumible consumible : getConsumo()) {
			valorTotal += consumible.valor();
		}
		return valorTotal;
	}
	
	public void agregarConsumo(Producto producto) {
		this.productosConsumidos.add(producto);
	}
	
	public void eliminarConsumo(Producto producto) {
		this.productosConsumidos.remove(producto);
	}
	
	@SuppressWarnings("unchecked")
	public List<Consumible> getConsumo() {
		return (List<Consumible>) productosConsumidos.stream().map(p -> (Consumible) p);
	}

	public List<Producto> getProductosConsumidos() {
		return productosConsumidos;
	}

	public void setProductosConsumidos(List<Producto> productosConsumidos) {
		this.productosConsumidos = productosConsumidos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
