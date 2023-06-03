package hotel_system.interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hotel_system.interfaces.components.FormDataTable;
import hotel_system.models.Consumible;

public class ConsumibleDataTable extends FormDataTable<Consumible> {
	
	List<Consumible> consumibles;

	protected ConsumibleDataTable(List<Consumible> consumibles) {
		super(
				Arrays.asList("Cantidad", "Item", "Precio Unitario", "Total"),
				Arrays.asList(0.2, 0.4, 0.2, 0.2),
				new ArrayList<>()
		);
		this.consumibles = consumibles;
		buildData();
	}
	
	private void buildData() {
		Map<String, List<Consumible>> quantities = consumibles.stream().collect(Collectors.groupingBy(Consumible::getNombre));
		this.data = quantities.keySet().stream()
				.map(consumible -> {
					List<Object> row = new ArrayList<>();
					Double precio = quantities.get(consumible).get(0).getPrecio();
					Integer cantidad = quantities.get(consumible).size();
					row.add(String.valueOf(cantidad));
					row.add(consumible);
					row.add(precio.toString());
					row.add(Double.valueOf((precio * cantidad)).toString());
					return row;
				})
				.toList();
	}

}
