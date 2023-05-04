package hotel_system.models;

import java.util.List;

public class Spa extends Servicio {
	
	private List<Producto> productos;

	public Spa(Long id, List<Producto> productosYServicios) {
		super(id);
		this.productos = productosYServicios;
	}
	
	public void adquirirProducto(Producto producto) {
		this.agregarConsumo(producto);
	}
	
	public void agregarProducto(Producto producto) {
		this.productos.add(producto);
	}
	
	public void eliminarProducto(Long id) {
		for(Producto p : productos) {
			if(p.getId().equals(id)) {
				this.productos.remove(p);
				return;
			}
		}
	}

	public List<Producto> getProductosYServicios() {
		return productos;
	}

	public void setProductosYServicios(List<Producto> productosYServicios) {
		this.productos = productosYServicios;
	}
}
