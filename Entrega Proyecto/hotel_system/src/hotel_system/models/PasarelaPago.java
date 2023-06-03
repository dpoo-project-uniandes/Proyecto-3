package hotel_system.models;

import java.util.Arrays;

import hotel_system.utils.Utils;
import services.FileManager;

public abstract class PasarelaPago {
	
	private String name;
	private String logo;
	private String path;
	
	public PasarelaPago(String name, String logo, String path) {
		super();
		this.name = name;
		this.logo = logo;
		this.path = path;
	}
	
	public String getName() {
		return name;
	};
	
	public String getLogo() {
		return logo;
	};
	
	
	/**
	 * Metodo generico para guardar pagos que puede ser sobreescrito por la implemetancion
	 * */
	public void guardarPago(Factura factura) throws Exception {
		FileManager.agregarLineasCSV(path, Arrays.asList(Arrays.asList(
				factura.getTitular().getDni(),
				factura.getTitular().getNombre(),
				Utils.stringDate(factura.getPago().getFecha()),
				factura.getPago().getMonto().toString(),
				String.valueOf(factura.getConsumibles().size()),
				String.valueOf(true)
		)));
	}
	
	public abstract Factura pagoConTarjeta(Factura factura, String owner, Long number, Integer cvv, String expiration) throws Exception;
}
