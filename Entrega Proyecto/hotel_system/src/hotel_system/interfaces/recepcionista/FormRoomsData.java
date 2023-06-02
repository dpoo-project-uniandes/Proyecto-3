package hotel_system.interfaces.recepcionista;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hotel_system.interfaces.components.AddItems;
import hotel_system.interfaces.components.FormDataTable;
import hotel_system.models.TipoHabitacion;
import hotel_system.utils.Utils;
import services.ImagesManager;

public class FormRoomsData extends FormDataTable<TipoHabitacion> {
	
	private List<TipoHabitacion> tipoHabitaciones;
	
	public FormRoomsData(List<TipoHabitacion> tipoHabitaciones) {
		super(
			Arrays.asList("Habitacion", "Capacidad", "Precio", "Caracteristicas", "Opciones"),
			Arrays.asList(0.2, 0.1, 0.2, 0.3, 0.2),
			Arrays.asList()
		);
		this.tipoHabitaciones = tipoHabitaciones;
		buildData();
	}
	
	private void buildData() {
		this.data = this.tipoHabitaciones.stream()
				.map(tipo -> {
					List<Object> row = new ArrayList<>();
					row.add(tipo.getAlias().toString());
					row.add(tipo.getCapacidad().toString());
					row.add(tipo.getPrecio().toString());
					row.add(buildCharacteristics(tipo));
					row.add(new AddItems());
					return row;
				}).toList();
	}
	
	private JPanel buildCharacteristics(TipoHabitacion tipoHabitacion) {
		JPanel characteristicsPanel = new JPanel();
		characteristicsPanel.setOpaque(false);
		if (tipoHabitacion.getConBalcon()) characteristicsPanel.add(logoCharacteristic("balcony"));
		if (tipoHabitacion.getConVista()) characteristicsPanel.add(logoCharacteristic("view"));
		if (tipoHabitacion.getConCocina()) characteristicsPanel.add(logoCharacteristic("kitchen"));
		if (tipoHabitacion.getConAireAcondicionado()) characteristicsPanel.add(logoCharacteristic("air-conditioner"));
		if (tipoHabitacion.getConCalefaccion()) characteristicsPanel.add(logoCharacteristic("heating"));
		if (tipoHabitacion.getConTelevision()) characteristicsPanel.add(logoCharacteristic("television"));
		if (tipoHabitacion.getConCafetera()) characteristicsPanel.add(logoCharacteristic("coffe-maker"));
		if (tipoHabitacion.getConCubrelecho()) characteristicsPanel.add(logoCharacteristic("bedspread"));
		if (tipoHabitacion.getConTapetesHipoalergenicos()) characteristicsPanel.add(logoCharacteristic("hypoallergenic-mats"));
		if (tipoHabitacion.getConPlancha()) characteristicsPanel.add(logoCharacteristic("clothes-iron"));
		if (tipoHabitacion.getConSecadorCabello()) characteristicsPanel.add(logoCharacteristic("hairdryer"));
		if (tipoHabitacion.getConDesayuno()) characteristicsPanel.add(logoCharacteristic("breakfast"));
		return characteristicsPanel;
	}
	
	private JLabel logoCharacteristic(String logoName) {
		ImageIcon originalIcon = ImagesManager.ImageIcon(logoName);
		ImageIcon icon = ImagesManager.resizeIcon(originalIcon, 18, 18);
		JLabel label = new JLabel();
		label.setIcon(icon);
		return label;
	}
	
	public void selectRooms(List<TipoHabitacion> habitaciones) {
		habitaciones.stream()
				.collect(Collectors.groupingBy(TipoHabitacion::getAlias))
				.forEach((key, val) -> {
					data.stream().forEach(row -> {
						if (row.get(0).equals(key))
							((AddItems) row.get(4)).setValue(val.size());
					});
				});
	}
	
	public void clean() {
		for (List<Object> list : data) {
			((AddItems) list.get(4)).setValue(0);
		}
	}
}