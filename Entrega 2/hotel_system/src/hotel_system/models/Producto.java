package hotel_system.models;

import java.util.List;
import java.util.Objects;

public class Producto implements Consumible{
	
	private Long id;
	private String nombre;
	private Double precio;
	
	public Producto(Long id, String nombre, Double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(precio, other.precio);
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + "]";
	}

	public Factura facturar(Huesped titular) {
	    Factura factura = new Factura(titular, List.of(this));
	    factura.procesarPago();
	    return factura;
	}
	
	public Double valor() {
		return getPrecio();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
