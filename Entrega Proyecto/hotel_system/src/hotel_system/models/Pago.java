package hotel_system.models;

import java.sql.Date;

import hotel_system.utils.Utils;

public class Pago {

	private TipoPago tipo;
	private Double monto;
	private Date fecha;

	public Pago(Double monto, TipoPago tipo) {
		super();
		this.monto = monto;
		this.fecha = Utils.nowDate();
		this.tipo = tipo;
	}
	
	public Pago(Double monto) {
		super();
		this.monto = monto;
		this.fecha = Utils.nowDate();
		this.tipo = TipoPago.EFECTIVO;
	}

	public TipoPago getTipo() {
		return tipo;
	}

	public Double getMonto() {
		return monto;
	}

	public Date getFecha() {
		return fecha;
	}
}
