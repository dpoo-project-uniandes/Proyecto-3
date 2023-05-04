package hotel_system.models;

public class TipoHabitacion {
	
	private String alias;
	private Integer capacidad;
	private Boolean conBalcon;
	private Boolean conVista;
	private Boolean conCocina;
	private Integer camasSencillas;
	private Integer camasDobles;
	private Integer camasQueen;
	private Double precio;
	
	public TipoHabitacion(String alias, Integer capacidad, Boolean conBalcon, Boolean conVista, Boolean conCocina,
			Integer camasSencillas, Integer camasDobles, Integer camasQueen, Double precio) {
		this.alias = alias;
		this.capacidad = capacidad;
		this.conBalcon = conBalcon;
		this.conVista = conVista;
		this.conCocina = conCocina;
		this.camasSencillas = camasSencillas;
		this.camasDobles = camasDobles;
		this.camasQueen = camasQueen;
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "TipoHabitacion [alias=" + alias + ", capacidad=" + capacidad + ", conBalcon=" + conBalcon
				+ ", conVista=" + conVista + ", conCocina=" + conCocina + ", camasSencillas=" + camasSencillas
				+ ", camasDobles=" + camasDobles + ", camasQueen=" + camasQueen + ", precio=" + precio + "]";
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Boolean getConBalcon() {
		return conBalcon;
	}

	public void setConBalcon(Boolean conBalcon) {
		this.conBalcon = conBalcon;
	}

	public Boolean getConVista() {
		return conVista;
	}

	public void setConVista(Boolean conVista) {
		this.conVista = conVista;
	}

	public Boolean getConCocina() {
		return conCocina;
	}

	public void setConCocina(Boolean conCocina) {
		this.conCocina = conCocina;
	}

	public Integer getCamasSencillas() {
		return camasSencillas;
	}

	public void setCamasSencillas(Integer camasSencillas) {
		this.camasSencillas = camasSencillas;
	}

	public Integer getCamasDobles() {
		return camasDobles;
	}

	public void setCamasDobles(Integer camasDobles) {
		this.camasDobles = camasDobles;
	}

	public Integer getCamasQueen() {
		return camasQueen;
	}

	public void setCamasQueen(Integer camasQueen) {
		this.camasQueen = camasQueen;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
}
