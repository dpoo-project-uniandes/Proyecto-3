package hotel_system.models;

import java.sql.Date;

import hotel_system.utils.Utils;

public class Pago {
	
	private Double monto;
	private Date fecha;
	
	public Pago(Double monto) {
		super();
		this.monto = monto;
		this.fecha = Utils.nowDate();
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
