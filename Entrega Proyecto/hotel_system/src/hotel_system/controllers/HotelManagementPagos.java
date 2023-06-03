package hotel_system.controllers;

import java.util.Map;

import hotel_system.models.Factura;
import hotel_system.models.PasarelaPago;

public class HotelManagementPagos {
	
	Map<String, PasarelaPago> plataformas;

	public HotelManagementPagos(Map<String, PasarelaPago> plataformas) {
		super();
		this.plataformas = plataformas;
	}

	public Factura procesarPagoConTarjetaCredito(Factura factura, String platform, String owner, Long number, Integer cvv, String expiration) throws Exception {
		return plataformas.get(platform).pagoConTarjeta(factura, owner, number, cvv, expiration);
	}
	
	public Map<String, PasarelaPago> getPlataformas() {
		return plataformas;
	}

}
