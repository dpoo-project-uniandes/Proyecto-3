package hotel_system.interfaces.recepcionista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.Tabla;
import hotel_system.interfaces.Utils;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.Input;
import hotel_system.models.Reserva;

public class BookingManagement extends JPanel {
	
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
	private JPanel formSelectionRooms;
	private Input guest;
	private Input checkout;
	private Input email;
	private Input arrival;
	private Input nroGuests;
	private Input dni;
	private Input age;
	private Input phone;
	private Tabla selectRoomsTable;
	private List<String> headersFormNewBooking;
	private List<List<String>> dataFormNewBooking;
	
	public BookingManagement(
		String user,
		List<String> headersFormRooms,
		List<List<String>> dataFormRooms,
		Function<Finder, ActionListener> findAction,
		Function<Finder, ActionListener> deleteAction,
		Function<Finder, ActionListener> updateAction
	) {
		this.title = "Detalles de la Reserva";
		this.headersFormNewBooking = headersFormRooms;
		this.dataFormNewBooking = dataFormRooms;
		configPanel();
		configHeader(user, "Reservas");
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
	
	private void configHeader(String user, String title) {
    	this.header = new MainHeader(user, title);
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
		this.finderAndNewBookingPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		this.finderAndNewBookingPanel.add(this.finder, Utils.getConstraints(0, 0, 1, 1, 0.2, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.finderAndNewBookingPanel.add(this.verticalButtons, Utils.getConstraints(1, 0, 1, 1, 0.1, 1, 20, 600, 0, 0, 1, GridBagConstraints.EAST));
		
		this.finderAndNewBookingPanel.setMaximumSize(new Dimension(5000, 80));
	}
	
	private void configFormNewBooking() {
		// INITIALIZE
		this.formNewBooking = new JPanel();
		this.formNewBooking.setLayout(new BoxLayout(this.formNewBooking, BoxLayout.Y_AXIS));
		this.formNewBooking.setOpaque(false);
		
		// INPUTS
		configFormNewBookignInputs();
		
		// FORM SELECT ROOMS
		configFormNewBookingSelectRooms();
		
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
		// PANEL
		this.formSelectionRooms = new JPanel();
		this.formSelectionRooms.setLayout(new GridBagLayout());
		
		// CELLS HEADERS
		this.selectRoomsTable = new Tabla(this.headersFormNewBooking, this.dataFormNewBooking);
		
		this.formSelectionRooms.add(this.selectRoomsTable);
		this.formNewBooking.add(this.selectRoomsTable);
	}
	
	private void configDataPanel(String title) {
		// DATA PANEL
		this.dataPanel = new DataPanel(title);
		this.dataPanel.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	public void withoutResults() {
		configDataPanel(title);
	}
	
	public void injectData(Reserva booking) {
		System.out.println(booking);
	}
}