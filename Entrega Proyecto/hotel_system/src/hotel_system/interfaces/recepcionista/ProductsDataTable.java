package hotel_system.interfaces.recepcionista;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hotel_system.interfaces.components.AddItems;
import hotel_system.interfaces.components.FormDataTable;
import hotel_system.models.Consumible;

public class ProductsDataTable extends FormDataTable<Consumible> {
	
	List<Consumible> productos;

	public ProductsDataTable(List<Consumible> productos) {
		super(
			Arrays.asList("Numero","Item","Precio","Carrito"),
			Arrays.asList(0.2,0.3,0.2,0.3), 
			new ArrayList<>()
		);
		this.productos = productos;
		buildData();
	}
	
	private void buildData() {
		this.data = this.productos.stream()
				.map(producto -> {
					List<Object> row = new ArrayList<>();
					row.add(producto.getId().toString());
					row.add(producto.getNombre());
					row.add(producto.getPrecio().toString());
					row.add(new AddItems());
					return row;
				})
				.toList();
	}
	
	public void clean() {
		this.data.stream()
			.forEach(row -> ((AddItems) row.get(3)).setValue(0));
	}
}
