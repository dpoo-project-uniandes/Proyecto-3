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
	private Double area;
	private Boolean conAireAcondicionado;
	private Boolean conCalefaccion;
	private Boolean conTelevision;
	private Boolean conCafetera;
	private Boolean conCubrelecho;
	private Boolean conTapetesHipoalergenicos;
	private Boolean conPlancha;
	private Boolean coSecadorCabello;
	private String voltaje;
	private Boolean conUSBA;
	private Boolean conUSBC;
	private Boolean conDesayuno;

	public TipoHabitacion(
			String alias, 
			Integer capacidad, 
			Boolean conBalcon, 
			Boolean conVista, 
			Boolean conCocina,
			Integer camasSencillas, 
			Integer camasDobles, 
			Integer camasQueen, 
			Double precio,
			Double area,
			Boolean conAireAcondicionado,
			Boolean conCalefaccion,
			Boolean conTelevision,
			Boolean conCafetera,
			Boolean conCubrelecho,
			Boolean conTapetesHipoalergenicos,
			Boolean conPlancha,
			Boolean coSecadorCabello,
			String voltaje,
			Boolean conUSBA,
			Boolean conUSBC,
			Boolean conDesayuno
	) {
		this.alias = alias;
		this.capacidad = capacidad;
		this.conBalcon = conBalcon;
		this.conVista = conVista;
		this.conCocina = conCocina;
		this.camasSencillas = camasSencillas;
		this.camasDobles = camasDobles;
		this.camasQueen = camasQueen;
		this.precio = precio;
		this.area = area;
		this.conAireAcondicionado = conAireAcondicionado;
		this.conCalefaccion = conCalefaccion;
		this.conTelevision = conTelevision;
		this.conCafetera = conCafetera;
		this.conCubrelecho = conCubrelecho;
		this.conTapetesHipoalergenicos = conTapetesHipoalergenicos;
		this.conPlancha = conPlancha;
		this.coSecadorCabello = coSecadorCabello;
		this.voltaje = voltaje;
		this.conUSBA = conUSBA;
		this.conUSBC = conUSBC;
		this.conDesayuno = conDesayuno;
	}

	@Override
	public String toString() {
		return "TipoHabitacion [alias=" + alias + ", capacidad=" + capacidad + ", conBalcon=" + conBalcon
				+ ", conVista=" + conVista + ", conCocina=" + conCocina + ", camasSencillas=" + camasSencillas
				+ ", camasDobles=" + camasDobles + ", camasQueen=" + camasQueen + ", precio=" + precio + ", area="
				+ area + ", conAireAcondicionado=" + conAireAcondicionado + ", conCalefaccion=" + conCalefaccion
				+ ", conTelevision=" + conTelevision + ", conCafetera=" + conCafetera + ", conCubrelecho="
				+ conCubrelecho + ", conTapetesHipoalergenicos=" + conTapetesHipoalergenicos + ", conPlancha="
				+ conPlancha + ", coSecadorCabello=" + coSecadorCabello + ", voltaje=" + voltaje + ", conUSBA="
				+ conUSBA + ", conUSBC=" + conUSBC + ", conDesayuno=" + conDesayuno + "]";
	}

	public String getAlias() {
		return alias;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public Boolean getConBalcon() {
		return conBalcon;
	}

	public Boolean getConVista() {
		return conVista;
	}

	public Boolean getConCocina() {
		return conCocina;
	}

	public Integer getCamasSencillas() {
		return camasSencillas;
	}

	public Integer getCamasDobles() {
		return camasDobles;
	}

	public Integer getCamasQueen() {
		return camasQueen;
	}

	public Double getPrecio() {
		return precio;
	}

	public Double getArea() {
		return area;
	}

	public Boolean getConAireAcondicionado() {
		return conAireAcondicionado;
	}

	public Boolean getConCalefaccion() {
		return conCalefaccion;
	}

	public Boolean getConTelevision() {
		return conTelevision;
	}

	public Boolean getConCafetera() {
		return conCafetera;
	}

	public Boolean getConCubrelecho() {
		return conCubrelecho;
	}

	public Boolean getConTapetesHipoalergenicos() {
		return conTapetesHipoalergenicos;
	}

	public Boolean getConPlancha() {
		return conPlancha;
	}

	public Boolean getCoSecadorCabello() {
		return coSecadorCabello;
	}

	public String getVoltaje() {
		return voltaje;
	}

	public Boolean getConUSBA() {
		return conUSBA;
	}

	public Boolean getConUSBC() {
		return conUSBC;
	}

	public Boolean getConDesayuno() {
		return conDesayuno;
	}
}
