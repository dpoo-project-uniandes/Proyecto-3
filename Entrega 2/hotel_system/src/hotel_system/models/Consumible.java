package hotel_system.models;

public interface Consumible {
	public Factura facturar(Huesped titular);
	public Double valor();
}
