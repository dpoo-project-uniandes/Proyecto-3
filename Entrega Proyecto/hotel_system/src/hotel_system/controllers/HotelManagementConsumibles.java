package hotel_system.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hotel_system.models.Consumible;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Producto;
import hotel_system.models.Restaurante;
import hotel_system.models.Servicio;
import hotel_system.models.Spa;

public class HotelManagementConsumibles {
	
	Map<Long, Producto> productos;
	Map<Long, Servicio> servicios;
	Map<Long, Consumible> consumibles;

	public HotelManagementConsumibles(Map<Long, Producto> productos, Map<Long, Servicio> servicios) {
		this.productos = productos;
		this.servicios = servicios;
		inventarioConsumibles();
	}
	
	private void inventarioConsumibles() {
		this.consumibles = new HashMap<>();
		productos.keySet().stream().forEach(key -> consumibles.put(key, productos.get(key)));
		servicios.keySet().stream()
			.forEach(key -> servicios.get(key).getConsumibles().stream()
					.forEach(consumible -> consumibles.put(consumible.getId(), consumible)));
	}
	
	public Map<Long, Producto> getProductos() {
		return productos;
	}
	
	public Restaurante getRestaurante() {
		return (Restaurante) servicios.values().stream().filter(service -> service instanceof Restaurante).findAny().get();
	}
	
	public Spa getSpa() {
		return (Spa) servicios.values().stream().filter(service -> service instanceof Spa).findAny().get();
	}
	
	public Consumible getProductoById(Long id) {
		return consumibles.get(id);
	}

	public void facturarAlaHabitacion(Estadia estadia, Map<Long, Integer> productos) {
		List<Consumible> consumibles = mapConsumibles(productos);
		consumibles.stream().forEach(c -> estadia.addConsumible(c));
	}
	
	public Factura facturar(Estadia estadia, Map<Long, Integer> productos) {
		List<Consumible> consumibles = mapConsumibles(productos);
		Factura factura = new Factura(estadia.getReserva().getTitular(), consumibles);
		return factura;
	}

	public Factura facturarEstadia(Estadia estadia) {
		estadia.cerrar();
		return estadia.getFacturaTotal();
	}
	
	private List<Consumible> mapConsumibles(Map<Long, Integer> productos) {
		List<Consumible> consumibles = new ArrayList<>();
		for (Long key : productos.keySet()) {
			for (int i = 0; i < productos.get(key); i++) {
				consumibles.add(this.consumibles.get(key));
			}
		}
		return consumibles;
	}
}
