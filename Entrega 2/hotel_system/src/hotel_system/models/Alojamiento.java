package hotel_system.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Alojamiento extends Servicio {
	
    private List<Habitacion> habitaciones;
    private Date fechaIngreso;
    private Date fechaSalida;

    public Alojamiento(Long id, List<Habitacion> habitaciones, Date fechaIngreso, Date fechaSalida) {
        super(id);
        this.habitaciones = habitaciones;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
    }

    @Override
    public Factura facturar(Huesped titular) {
        throw new UnsupportedOperationException("method not implemented");
    }

    @Override
    public Double valor() {
    	Double valorTotal = 0.0;
        for(Habitacion habitacion: habitaciones) {
        	valorTotal += habitacion.calcularTarifa(fechaIngreso, fechaSalida);
        }
        return valorTotal;
    }
    
    public List<Consumible> getConsumo() {
    	List<Consumible> habitaciones = new ArrayList<>();
    	for(Habitacion habitacion: this.habitaciones) {
    		habitaciones.add(new Producto(
    				Integer.toUnsignedLong(habitacion.getNumero()), 
    				habitacion.getTipo().getAlias(), 
    				habitacion.calcularTarifa(fechaIngreso, fechaSalida))
				);
    	}
    	return habitaciones;
    }

	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
}
