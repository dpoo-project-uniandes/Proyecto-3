package hotel_system.pasarelas_pago;

import hotel_system.models.Factura;
import hotel_system.models.Pago;
import hotel_system.models.PasarelaPago;
import hotel_system.models.TipoPago;

public class AmericanExpress extends PasarelaPago {
	
	public AmericanExpress() {
		super("american express", "amex", "american express");
	}

	@Override
	public Factura pagoConTarjeta(Factura factura, String owner, Long number, Integer cvv, String expiration) throws Exception {
		if (!(number.toString().startsWith("5839")) && !(cvv.toString().length() < 4))
			throw new Exception("numero de  tarjeta no corresponde a una tarjeta american express");
		Pago pago = new Pago(factura.calcularValorTotal(), TipoPago.TARJETA_CREDITO);
		factura.setPago(pago);
		return factura;
	}
}
