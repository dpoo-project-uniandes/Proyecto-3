package hotel_system.models;

import java.sql.Date;
import java.util.ArrayList;
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

	public void setRangoHorario(List<Date> rangoHorario) {
		this.rangoHorario = rangoHorario;
	}

	public Boolean getAlCuarto() {
		return alCuarto;
	}

	public void setAlCuarto(Boolean alCuarto) {
		this.alCuarto = alCuarto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	

}


