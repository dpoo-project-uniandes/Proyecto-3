package hotel_system.models;

import java.util.List;

public interface Facturable {
	public abstract List<Consumible> getConsumibles();
	public abstract Factura facturar(Huesped titular);
	public abstract Double calcularTotal();
	public abstract void addConsumible(Consumible consumible);
	public abstract void removeConsumible(Consumible consumible);
}
