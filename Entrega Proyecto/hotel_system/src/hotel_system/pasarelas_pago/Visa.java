package hotel_system.pasarelas_pago;

import hotel_system.models.Factura;
import hotel_system.models.Pago;
import hotel_system.models.PasarelaPago;
import hotel_system.models.TipoPago;

public class Visa extends PasarelaPago {
	
	public Visa() {
		super("visa", "visa", "pagos_visa.csv");
	}

	@Override
	public Factura pagoConTarjeta(Factura factura, String owner, Long number, Integer cvv, String expiration) throws Exception {
		if (!number.toString().startsWith("3940"))
			throw new Exception("numero de  tarjeta no corresponde a una tarjeta visa");
		Pago pago = new Pago(factura.calcularValorTotal(), TipoPago.TARJETA_CREDITO);
		factura.setPago(pago);
		super.guardarPago(factura);
		return factura;
	}
}
