package hotel_system.models;

public class Hotel {
	
	private Boolean conParqueaderoIncluido;
	private Boolean conPiscina;
	private Boolean conZonasHumedas;
	private Boolean conBBQ;
	private Boolean conWifi;
	private Boolean conRecepcion24Horas;
	private Boolean admiteMascotas;
	
	public Hotel(Boolean conParqueaderoIncluido, Boolean conPisicina, Boolean conZonasHumedas, Boolean conBBQ,
			Boolean conWifi, Boolean conRecepcion24Horas, Boolean admiteMascotas) {
		super();
		this.conParqueaderoIncluido = conParqueaderoIncluido;
		this.conPiscina = conPisicina;
		this.conZonasHumedas = conZonasHumedas;
		this.conBBQ = conBBQ;
		this.conWifi = conWifi;
		this.conRecepcion24Horas = conRecepcion24Horas;
		this.admiteMascotas = admiteMascotas;
	}
	

	@Override
	public String toString() {
		return "Hotel [conParqueaderoIncluido=" + conParqueaderoIncluido + ", conPiscina=" + conPiscina
				+ ", conZonasHumedas=" + conZonasHumedas + ", conBBQ=" + conBBQ + ", conWifi=" + conWifi
				+ ", conRecepcion24Horas=" + conRecepcion24Horas + ", admiteMascotas=" + admiteMascotas + "]";
	}



	public Boolean getConParqueaderoIncluido() {
		return conParqueaderoIncluido;
	}

	public Boolean getConPiscina() {
		return conPiscina;
	}

	public Boolean getConZonasHumedas() {
		return conZonasHumedas;
	}

	public Boolean getConBBQ() {
		return conBBQ;
	}

	public Boolean getConWifi() {
		return conWifi;
	}

	public Boolean getConRecepcion24Horas() {
		return conRecepcion24Horas;
	}

	public Boolean getAdmiteMascotas() {
		return admiteMascotas;
	}
}
