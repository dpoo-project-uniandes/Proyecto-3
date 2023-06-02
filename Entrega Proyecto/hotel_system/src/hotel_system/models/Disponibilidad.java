package hotel_system.models;

import java.sql.Date;
import java.util.Objects;

public class Disponibilidad {

	private Long id;
	private Integer habitacion;
	private Double precio;
	private Boolean estado;
	private Date fecha;
	private Reserva reserva;

	public Disponibilidad(Long id, Integer habitacion, Double precio, Boolean estado, Date fecha, Reserva reserva) {
		super();
		this.id = id;
		this.habitacion = habitacion;
		this.precio = precio;
		this.estado = estado;
		this.fecha = fecha;
		this.reserva = reserva;
	}

	public Disponibilidad(Integer habitacion, Double precio, Boolean estado, Date fecha) {
		this.id = fecha.getTime() + habitacion;
		this.precio = precio;
		this.estado = estado;
		this.fecha = fecha;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass()) {
			return false;
		}
		Disponibilidad other = (Disponibilidad) obj;
		return Objects.equals(fecha, other.fecha);
	}

	@Override
	public String toString() {
		return "Disponibilidad [precio=" + precio + ", estado=" + estado + ", fecha=" + fecha + ", reserva=" + reserva
				+ "]";
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getHabitacion() {
		return habitacion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
}
