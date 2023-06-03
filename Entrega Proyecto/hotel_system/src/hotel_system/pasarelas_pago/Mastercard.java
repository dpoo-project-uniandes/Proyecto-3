package hotel_system.pasarelas_pago;

import hotel_system.models.Factura;
import hotel_system.models.Pago;
import hotel_system.models.PasarelaPago;
import hotel_system.models.TipoPago;

public class Mastercard extends PasarelaPago {

	public Mastercard() {
		super("mastercard", "mastercard", "pagos_mastercard.csv");
	}

	@Override
	public Factura pagoConTarjeta(Factura factura, String owner, Long number, Integer cvv, String expiration) throws Exception {
		if (!number.toString().startsWith("9384"))
			throw new Exception("numero de  tarjeta no corresponde a una tarjeta mastercard");
		Pago pago = new Pago(factura.calcularValorTotal(), TipoPago.TARJETA_CREDITO);
		factura.setPago(pago);
		return factura;
	}
}
