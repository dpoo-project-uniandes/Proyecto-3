package hotel_system.models;

import java.sql.Date;
import java.util.List;

public class ProductoRestaurante extends Producto {

	private List<Date> rangoHorario;
	private Boolean alCuarto;
	private String tipo;

	public ProductoRestaurante(Long id, String nombre, Double precio, List<Date> rangoHorario2, Boolean alCuarto,
			String tipo) {
		super(id, nombre, precio);
		this.rangoHorario = rangoHorario2;
		this.alCuarto = alCuarto;
		this.tipo = tipo;
	}

	public List<Date> getRangoHorario() {
		return rangoHorario;
	}

	public Boolean getAlCuarto() {
		return alCuarto;
	}

	public String getTipo() {
		return tipo;
	}
}


