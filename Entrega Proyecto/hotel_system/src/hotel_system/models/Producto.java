package hotel_system.models;

import java.util.Objects;

public class Producto implements Consumible {

	private Long id;
	private String nombre;
	private Double precio;

	public Producto(Long id, String nombre, Double precio) {
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

	@Override
	public Double getPrecio() {
		return precio;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getNombre() {
		return nombre;
	}
}
