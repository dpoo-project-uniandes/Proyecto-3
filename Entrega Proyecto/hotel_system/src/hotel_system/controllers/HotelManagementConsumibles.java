package hotel_system.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hotel_system.models.Consumible;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Producto;

public class HotelManagementConsumibles {
	
	Map<Long, Producto> productos;

	public HotelManagementConsumibles(Map<Long, Producto> productos) {
		this.productos = productos;
	}
	
	public Map<Long, Producto> getProductos() {
		return productos;
	}
	
	public Producto getProductoById(Long id) {
		return productos.get(id);
	}

	public Factura facturarAlaHabitacion(Estadia estadia, Map<Long, Integer> productos) {
		List<Consumible> consumibles = productos.keySet().stream()
				.map(k -> (Consumible) this.productos.get(k))
				.toList();
		Factura factura = new Factura(estadia.getReserva().getTitular(), consumibles);
		estadia.cargarFactura(factura);
		return factura;
	}

}
