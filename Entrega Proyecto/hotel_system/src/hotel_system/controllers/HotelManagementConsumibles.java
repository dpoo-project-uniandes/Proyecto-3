package hotel_system.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.text.html.Option;

import hotel_system.models.Consumible;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Producto;
import hotel_system.models.ProductoRestaurante;
import hotel_system.models.Restaurante;
import hotel_system.models.Reserva;
import hotel_system.models.Restaurante;
import hotel_system.models.Servicio;
import hotel_system.models.Spa;
import hotel_system.utils.Utils;
import services.Dupla;
import services.FileManager;

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

	public Consumible getProductoSpa(Long id){
		Optional<Consumible> consumible = getSpa().getConsumibles().stream().filter(c -> c.getId().equals(id)).findFirst();
		return consumible.isPresent() ? consumible.get() : null;
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
	
	public Factura facturarReserva(Reserva reserva) {
		return reserva.facturar(reserva.getTitular());
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

    public void eliminarProducto(Consumible producto, String tipoProducto) {
		if (tipoProducto.equals("Producto")) {
			productos.remove(producto.getId());
		} else if (tipoProducto.equals("ProductoSpa")){
			Spa spa = getSpa();
			spa.getConsumibles().remove(producto);
		} else{
			Restaurante restaurante = getRestaurante();
			restaurante.getConsumibles().remove(producto);
		}
		consumibles.remove(producto.getId());
    }

    public void crearProducto(String id, String nombre, String precio, String tipo, String alCuarto,
            String rangoHorario1, String rangoHorario2) throws Exception {
		List<Date> fechas = new ArrayList<>();
		fechas.add(Utils.stringToDate(rangoHorario1));
		fechas.add(Utils.stringToDate(rangoHorario2));
		ProductoRestaurante producto = new ProductoRestaurante(Long.parseLong(id), nombre, Double.parseDouble(precio), fechas
				, Boolean.parseBoolean(alCuarto), tipo);
		productos.put(producto.getId(), producto);
		consumibles.put(producto.getId(), producto);
		Restaurante restaurante = (Restaurante) servicios.values().stream()
		.filter(servicio -> servicio instanceof Restaurante).findAny()
		.orElseThrow(() -> new RuntimeException("No existe un servicio de restaurante"));
		restaurante.addConsumible(producto);
		List<List<String>> listaDataProducto = Arrays.asList(productoRestAsList(producto, rangoHorario1, rangoHorario2));
		FileManager.agregarLineasCSV("productos_restaurante.csv", listaDataProducto);
	}

    private List<String> productoRestAsList(ProductoRestaurante producto, String rangoHorario1, String rangoHorario2) {
		return Arrays.asList(
			producto.getId().toString(),
			producto.getNombre(),
			producto.getPrecio().toString(),
			rangoHorario1,
			rangoHorario2,
			producto.getAlCuarto().toString(),
			producto.getTipo()
		);
	}

	public void crearProducto(String id, String nombre, String precio, String tipo) throws Exception {
		Producto producto = new Producto(Long.parseLong(id), nombre, Double.parseDouble(precio));
		productos.put(producto.getId(), producto);
		consumibles.put(producto.getId(), producto);
		List<List<String>> listaDataProducto = Arrays.asList(productoAsList(producto));
		if (tipo.equals("Producto")) {
			FileManager.agregarLineasCSV("productos.csv", listaDataProducto);
		} else {
			Spa spa = (Spa) servicios.values().stream()
				.filter(servicio -> servicio instanceof Spa)
				.findFirst()
				.orElseThrow(() -> new Exception("No existe un servicio de spa"));
			spa.addConsumible(producto);
			FileManager.agregarLineasCSV("productos_spa.csv", listaDataProducto);
    }}

	private List<String> productoAsList(Producto producto) {
		return Arrays.asList(
			producto.getId().toString(),
			producto.getNombre(),
			producto.getPrecio().toString()
		);
	}

}
