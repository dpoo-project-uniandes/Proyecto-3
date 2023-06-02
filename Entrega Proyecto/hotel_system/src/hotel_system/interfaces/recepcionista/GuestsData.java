package hotel_system.interfaces.recepcionista;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.ImageIcon;

import hotel_system.interfaces.components.FormDataTable;
import hotel_system.interfaces.components.OptionButton;
import hotel_system.models.Huesped;
import services.ImagesManager;

public class GuestsData extends FormDataTable<Huesped> {
	
	private ImageIcon trashIcon;
	private Integer lastPositionFree;
	private List<Huesped> guests;
	
	private Function<GuestsData, ActionListener> deleteAction;
	
	public GuestsData(
			List<Huesped> guests,
			Function<GuestsData, ActionListener> deleteAction
	) {
		super(
			Arrays.asList("Huesped", "Documento", "Edad", "Opciones"),
			Arrays.asList(0.3, 0.3, 0.2, 0.2),
			Arrays.asList()
		);
		this.guests = guests;
		this.lastPositionFree = 0;
		this.trashIcon = ImagesManager.resizeIcon(ImagesManager.ImageIcon("trash"), 18, 18);
		this.deleteAction = deleteAction;
		buildData();
	}
	
	private void buildData() {
		this.data = new ArrayList<>();
		this.guests.stream().forEach(guest -> addDataList(guest));
		fillTable();
	}

	@Override
	public void addData(Huesped value) {
		this.guests.add(value);
		addDataList(value);
		this.lastPositionFree += 1;
	}
	
	@Override
	public List<Huesped> getTypedData() {
		return this.guests;
	}
	
	private void addDataList(Huesped guest) {
		List<Object> row = new ArrayList<>();
		row.add(guest.getNombre().toString());
		row.add(guest.getDni().toString());
		row.add(guest.getEdad().toString());
		row.add(configOptionsRow());
		this.data.add(lastPositionFree, row);
	}
	
	private OptionButton configOptionsRow() {
		OptionButton deleteButton = new OptionButton(trashIcon);
		deleteButton.getButton().addActionListener(deleteAction.apply(this));
		return deleteButton;
	}

	private void fillTable() {
		Integer cells = lastPositionFree < 10 ? (10 - lastPositionFree) : 0;
		for (Long i = 0L; i < cells; i++) {
			this.data.add(Arrays.asList("", "", "", new OptionButton(trashIcon)));
		}
	}
	
	public void withDeleteAction(Function<GuestsData, ActionListener> deleteAction) {
		this.deleteAction = deleteAction;
	}
	
	public Integer size() {
		return lastPositionFree;
	}
	
	public void remove(Long id) {
		for(int i = 0; i < this.data.size(); i++) {
			List<Object> row = this.data.get(i);
			OptionButton options = ((OptionButton) row.get(3));
			Long optionsId = options.getId();
			if (id.equals(optionsId)) {
				this.data.remove(i);
				this.lastPositionFree -= 1;
				return;
			}
		}
	}
}
