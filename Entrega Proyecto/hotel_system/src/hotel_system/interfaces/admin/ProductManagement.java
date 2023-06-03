package hotel_system.interfaces.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.UtilsGUI;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.AddItems;
import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.DynamicTable;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.Input;
import hotel_system.models.Reserva;
import hotel_system.models.TipoHabitacion;
import hotel_system.utils.Utils;

public class ProductManagement extends JPanel {
	
	// BASIC
	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private Button newBookingBtn;
	private VerticalButtons verticalButtons;
	private JPanel finderAndNewBookingPanel;
	private String title;
	
	// FORM NEW BOOKING
	private JPanel formNewBooking;
	private JPanel inputsPanel;
	private Input guest;
	private Input checkout;
	private Input email;
	private Input arrival;
	private Input nroGuests;
	private Input dni;
	private Input age;
	private Input phone;
	private DynamicTable selectRoomsTable;
	private Button booking;
	private FormProductData formRoomsData;
	
	// DATA BOOKING
	private JPanel bookingDataPanel;
	private VerticalButtons actionsBookingPanel;
	private Button updateBookingBtn;
	private Button deleteBookingBtn;
	private Button cancelBookingBtn;
	private Reserva bookingInjected;
	
	// ACTIONS LISTENER
	Function<ProductManagement, ActionListener> createAction;
	Function<ProductManagement, ActionListener> deleteAction;
	Function<ProductManagement, ActionListener> updateAction;
	Function<ProductManagement, ActionListener> cancelAction;
	
	public ProductManagement(
		String user,
		FormProductData formRoomsData,
		HeaderButtonsActions headerButtonsActions,
		Function<Finder, ActionListener> findAction,
		Function<ProductManagement, ActionListener> createAction,
		Function<ProductManagement, ActionListener> deleteAction,
		Function<ProductManagement, ActionListener> updateAction,
		Function<ProductManagement, ActionListener> cancelAction
	) {
		this.title = "Detalles de la Reserva";
		this.formRoomsData = formRoomsData;
		this.createAction = createAction;
		this.updateAction = updateAction;
		this.deleteAction = deleteAction;
		this.cancelAction = cancelAction;
		configPanel();
		configHeader(user, "Reservas", headerButtonsActions);
		configFinder("Documento Titular / Nro de Reserva", findAction);
		configNewBookingButton();
		configVerticalButtons();
		configPanelFinderAndNewBooking();
		configDataPanel(title);
		configComponents();
	}
	
	private void configComponents() {
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(finderAndNewBookingPanel);
		this.add(Box.createRigidArea(new Dimension(0, 50)));
		this.add(dataPanel);
	}
	
	private void configPanel() {
    	this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(50, 50, 50, 50));
	}
	
	private void configHeader(String user, String title, HeaderButtonsActions headerButtonsActions) {
    	this.header = new MainHeader(user, title, headerButtonsActions);
	}
	
	private void configFinder(
		String text,
		Function<Finder, ActionListener> findAction
	) {
		this.finder = new Finder(text, findAction);
	}
	
	private void configNewBookingButton() {
		this.newBookingBtn = new Button("Nueva Reserva");
		this.newBookingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configFormNewBooking();
			}
		});
	}
	
	private void configVerticalButtons() {
		this.verticalButtons = new VerticalButtons(1);
		this.verticalButtons.addButton(newBookingBtn);
	}
	
	private void configPanelFinderAndNewBooking() {
		this.finderAndNewBookingPanel = new JPanel();
		this.finderAndNewBookingPanel.setLayout(new GridBagLayout());
		this.finderAndNewBookingPanel.setOpaque(false);
		
		this.finderAndNewBookingPanel.add(this.finder, UtilsGUI.getConstraints(0, 0, 1, 1, 0.2, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.finderAndNewBookingPanel.add(this.verticalButtons, UtilsGUI.getConstraints(1, 0, 1, 1, 0.1, 1, 20, 600, 0, 0, 1, GridBagConstraints.EAST));
		
		this.finderAndNewBookingPanel.setMaximumSize(new Dimension(5000, 80));
	}
	
	private void configFormNewBooking() {
		// CLEAN
		cleanUserInputs();
		
		// INITIALIZE
		this.formNewBooking = new JPanel();
		this.formNewBooking.setLayout(new BoxLayout(this.formNewBooking, BoxLayout.Y_AXIS));
		this.formNewBooking.setOpaque(false);
		
		// INPUTS
		configFormNewBookignInputs();
		
		// FORM SELECT ROOMS
		configFormNewBookingSelectRooms();
		
		// BUTTON
		configButtonBooking();
		
		// INJECT
		this.dataPanel.injectDataPanel(this.formNewBooking);
	}
	
	private void configFormNewBookignInputs() {
		// INPUTS PANEL
		this.inputsPanel = new JPanel();
		this.inputsPanel.setLayout(new GridLayout(2, 4, 30, 20));
		this.inputsPanel.setOpaque(false);
		
		// INPUTS
		this.guest = Input.Instance("Titular", "text");
		this.checkout = Input.Instance("Salida", "text");
		this.email = Input.Instance("Email", "text");
		this.arrival = Input.Instance("Llegada", "text");
		this.nroGuests = Input.Instance("Huespedes", "text");
		this.dni = Input.Instance("Documento", "text");
		this.age = Input.Instance("Edad", "number");
		this.phone = Input.Instance("Telefono", "number");

		this.inputsPanel.add(guest);
		this.inputsPanel.add(checkout);
		this.inputsPanel.add(email);
		this.inputsPanel.add(arrival);
		this.inputsPanel.add(nroGuests);
		this.inputsPanel.add(dni);
		this.inputsPanel.add(age);
		this.inputsPanel.add(phone);
		
		this.formNewBooking.add(inputsPanel);
		this.formNewBooking.add(Box.createRigidArea(new Dimension(0, 40)));
	}
	
	private void configFormNewBookingSelectRooms() {		
		// CELLS HEADERS
 		this.selectRoomsTable = new DynamicTable(formRoomsData);
 		JScrollPane scrollPane = new JScrollPane(this.selectRoomsTable);
 		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.formNewBooking.add(scrollPane);
		this.formNewBooking.add(Box.createRigidArea(new Dimension(0, 20)));
	}
	
	private void configButtonBooking() {
		this.booking = new Button("Reservar", new Dimension(200, 50));
		this.booking.setAlignmentX(CENTER_ALIGNMENT);
		this.booking.addActionListener(this.createAction.apply(this));
		this.formNewBooking.add(this.booking);
	}
	
	private void configDataPanel(String title) {
		// DATA PANEL
		this.dataPanel = new DataPanel(title);
	}
	
	private JPanel getLabelDataBooking(String title, String value) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleLabel = new JLabel(title + ":");
		JLabel valueLabel = new JLabel(value);
		
		// CUSTOMOZATION
		titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
		valueLabel.setFont(new Font(getName(), Font.PLAIN, 20));
		
		panel.add(titleLabel);
		panel.add(valueLabel);
		panel.setOpaque(false);
		
		return panel;
	}
	
	private JPanel getColumnPanelDataBooking() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		return panel; 
	}
	
	public void withoutResults() {
		this.dataPanel.emptyResults();
		this.bookingInjected = null;
	}
	
	public void injectData(Reserva booking) {
		// UPDATE CURRENT
		this.bookingInjected = booking;
		
		// PANEL
		this.bookingDataPanel = new JPanel();
		this.bookingDataPanel.setLayout(new GridBagLayout());
		this.bookingDataPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		this.bookingDataPanel.setOpaque(false);
		
		List<String> titles = List.of("Nro", "Llegada", "Salida", "Titular", "Documento", "Estado", "Estadia", "Tarifa", "Habitaciones");
		List<String> values = List.of(
				booking.getNumero().toString(),
				Utils.stringLocalDate(booking.getFechaDeLlegada()),
				Utils.stringLocalDate(booking.getFechaDeSalida()),
				booking.getTitular().getNombre(),
				booking.getTitular().getDni(),
				booking.getEstado().toString(),
				booking.getEstadia() == null ? "" : booking.getEstadia().getId().toString(),
				booking.getTarifaTotal().toString(),
				booking.getHabitaciones().stream().map(h -> h.getNumero().toString()).toList().toString()
		);
		
		// COLUMNS DATA
		Integer middle = (titles.size() / 2);
		JPanel col1 = getColumnPanelDataBooking();
		JPanel col2 = getColumnPanelDataBooking();
		
		for(int i = 0; i < titles.size(); i++) {
			if (middle < i)
				col1.add(getLabelDataBooking(titles.get(i), values.get(i)));
			else
				col2.add(getLabelDataBooking(titles.get(i), values.get(i)));
		}
		
		this.bookingDataPanel.add(col2, UtilsGUI.getConstraints(0, 0, 1, 1, 0.4, 1, 0, 0, 0, 0, 1, GridBagConstraints.WEST));
		this.bookingDataPanel.add(col1, UtilsGUI.getConstraints(1, 0, 1, 1, 0.4, 1, 0, 0, 0, 0, 1, GridBagConstraints.WEST));
		
		// BUTTONS
		this.actionsBookingPanel = new VerticalButtons(3);
		this.updateBookingBtn = new Button("Modificar");
		this.updateBookingBtn.addActionListener(this.updateAction.apply(this));
		this.deleteBookingBtn = new Button("Eliminar");
		this.deleteBookingBtn.addActionListener(this.deleteAction.apply(this));
		this.cancelBookingBtn = new Button("Cancelar");
		this.cancelBookingBtn.addActionListener(this.cancelAction.apply(this));
		this.actionsBookingPanel.addButton(this.updateBookingBtn);
		this.actionsBookingPanel.addButton(this.cancelBookingBtn);
		this.actionsBookingPanel.addButton(this.deleteBookingBtn);
		
		this.bookingDataPanel.add(this.actionsBookingPanel, UtilsGUI.getConstraints(3, 0, 1, 1, 0.2, 1, 60, 0, 60, 0, 1, GridBagConstraints.EAST));
		
		// REFRESH
		this.dataPanel.injectDataPanel(this.bookingDataPanel);
		this.revalidate();
	}
	
	public void formNewBookingInjectData(Reserva booking) {
		// Restart form panel
		configFormNewBooking();
		
		// Set rooms selected
		List<TipoHabitacion> habitaciones = booking.getHabitaciones().stream().map(h -> h.getTipo()).toList();
		this.formRoomsData.selectRooms(habitaciones);
		
		// Set information from booking data
		this.guest.getInput().setText(booking.getTitular().getNombre());
		this.checkout.getInput().setText(Utils.stringLocalDate(booking.getFechaDeSalida()));
		this.email.getInput().setText(booking.getTitular().getEmail());
		this.arrival.getInput().setText(Utils.stringLocalDate(booking.getFechaDeLlegada()));
		this.nroGuests.getInput().setText(booking.getCantidadPersonas().toString());
		this.dni.getInput().setText(booking.getTitular().getDni().toString());
		this.age.getInput().setText(booking.getTitular().getEdad().toString());
		this.phone.getInput().setText(booking.getTitular().getTelefono().toString());
	}
	
	public void cleanUserInputs() {
		this.formRoomsData.clean();
		this.finder.setValue("");;
	}
	
	public Map<String, String> getDataMap() {
		return Map.of(
			"id", this.bookingInjected == null ? "0" : this.bookingInjected.getNumero().toString(),
			"titular", this.guest.getValue(), 
			"salida", this.checkout.getValue(), 
			"email", this.email.getValue(), 
			"llegada", this.arrival.getValue(), 
			"huespedes", this.nroGuests.getValue(),
			"dni", this.dni.getValue(),
			"edad", this.age.getValue(),
			"telefono", this.phone.getValue()
		);
	}	
	
	public Map<String, Integer> getDataRoomsMap() {
		Map<String, Integer> rooms = new HashMap();
		this.formRoomsData.getData().stream().forEach(r -> {
			AddItems addItems = (AddItems) r.get(r.size()-1);
			if (addItems.getValue() > 0) 
				rooms.put((String) r.get(0), addItems.getValue());
		});
		return rooms;
	}
	
	public Reserva getBookingInjected() {
		return bookingInjected;
	}
}