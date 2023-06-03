package hotel_system.interfaces.recepcionista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.DialogFactura;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.UtilsGUI;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.DynamicTable;
import hotel_system.interfaces.components.Facturador;
import hotel_system.interfaces.components.FormDataTable;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.Input;
import hotel_system.models.Estadia;
import hotel_system.models.Factura;
import hotel_system.models.Huesped;
import hotel_system.utils.Utils;

public class EstadiasManagement extends JPanel implements Facturador {

	// BASIC
	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private Button newEstadiaBtn;
	private VerticalButtons verticalButtons;
	private JPanel finderAndNewEstadiaPanel;
	private String title;
	
	// FORM NEW ESTADIA
	private JPanel formNewEstadia;
	private JPanel inputsPanel;
	private Input bookingId;
	private Input guest;
	private Input dni;
	private Input age;
	private Button addGuestBtn;
	private DynamicTable guestsTable;
	private JScrollPane scrollPaneGuestsTable;
	private Button createEstadia;
	private FormDataTable<Huesped> guestsData;
	
	// DATA ESTADIA
	private JPanel estadiasDataPanel;
	private VerticalButtons actionsEstadiasPanel;
	private Button updateEstadiaBtn;
	private Button guestsEstadiaBtn;
	private Button billingEstadiaBtn;
	private Estadia estadiaInjected;
	
	// FACTURA
	DialogFactura dialogFactura;
	Window frame;
	
	// ACTIONS LISTENER
	Function<EstadiasManagement, ActionListener> createAction;
	Function<EstadiasManagement, ActionListener> updateAction;
	Function<EstadiasManagement, ActionListener> guestsAction;
	Function<EstadiasManagement, ActionListener> billingAction;
	
	public EstadiasManagement(
			String user,
			Window frame,
			FormDataTable<Huesped> guestsData,
			HeaderButtonsActions headerButtonsActions,
			Function<Finder, ActionListener> findAction,
			Function<EstadiasManagement, ActionListener> createAction,
			Function<EstadiasManagement, ActionListener> updateAction,
			Function<EstadiasManagement, ActionListener> guestsAction,
			Function<EstadiasManagement, ActionListener> billingAction
	) {
		this.title = "Detalles de la Estadia";
		this.frame = frame;
		this.guestsData = guestsData;
		this.createAction = createAction;
		this.updateAction = updateAction;
		this.guestsAction = guestsAction;
		this.billingAction = billingAction;
		configPanel();
		configHeader(user, "Estadias", headerButtonsActions);
		configFinder("Documento Titular / Nro de Estadia", findAction);
		configNewEstadiaButton();
		configVerticalButtons();
		configPanelFinderAndNewEstadia();
		configDataPanel(title);
		configComponents();
	}
	
	private void configComponents() {
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(finderAndNewEstadiaPanel);
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
	
	private void configVerticalButtons() {
		this.verticalButtons = new VerticalButtons(1);
		this.verticalButtons.addButton(newEstadiaBtn);
	}
	
	private void configNewEstadiaButton() {
		this.newEstadiaBtn = new Button("Nueva Estadia");
		this.newEstadiaBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guestsData = new GuestsData(new ArrayList<>(), null);
				configFormNewEstadia();
			}
		});
	}
	
	private void configPanelFinderAndNewEstadia() {
		this.finderAndNewEstadiaPanel = new JPanel();
		this.finderAndNewEstadiaPanel.setLayout(new GridBagLayout());
		this.finderAndNewEstadiaPanel.setOpaque(false);
		
		this.finderAndNewEstadiaPanel.add(this.finder, UtilsGUI.getConstraints(0, 0, 1, 1, 0.2, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.finderAndNewEstadiaPanel.add(this.verticalButtons, UtilsGUI.getConstraints(1, 0, 1, 1, 0.1, 1, 20, 600, 0, 0, 1, GridBagConstraints.EAST));
		
		this.finderAndNewEstadiaPanel.setMaximumSize(new Dimension(5000, 80));
	}
	
	private void configDataPanel(String title) {
		this.dataPanel = new DataPanel(title);
	}
	
	// ================================================================================================================================================================================
	// FORM NUEVA ESTADIA
	// ================================================================================================================================================================================
	
	public void cleanUserInputs() {
		this.finder.setValue("");
		this.guest.getInput().setText("");
		this.dni.getInput().setText("");
		this.age.getInput().setText("");
	}
	
	private void configFormNewEstadia() {
		// INITIALIZE
		this.formNewEstadia = new JPanel();
		this.formNewEstadia.setLayout(new BoxLayout(this.formNewEstadia, BoxLayout.Y_AXIS));
		this.formNewEstadia.setOpaque(false);
		
		// INPUTS
		configFormNewEstadiaInputs();
		
		// TABLE GUESTS
		configGuestsTable();
		
		// BUTTON
		configButtonEstadia();
		
		// INJECT
		this.dataPanel.injectDataPanel(this.formNewEstadia);
	}

	private void configFormNewEstadiaInputs() {
		// INPUTS PANEL
		this.inputsPanel = new JPanel();
		this.inputsPanel.setLayout(new GridBagLayout());
		this.inputsPanel.setOpaque(false);
		
		// INPUTS
		this.bookingId = Input.Instance("Documento Titular", "text");
		this.guest = Input.Instance("Nombre", "text");
		this.dni = Input.Instance("Documento", "text");
		this.age = Input.Instance("Edad", "number");
		this.addGuestBtn = new Button("Agregar");
		configButtonAddGuest();

		this.inputsPanel.add(bookingId, UtilsGUI.getConstraints(0, 0, 1, 1, 0.3, 1, 0, 0, 10, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(guest, UtilsGUI.getConstraints(0, 1, 1, 1, 0.3, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(dni, UtilsGUI.getConstraints(1, 1, 1, 1, 0.3, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(age, UtilsGUI.getConstraints(2, 1, 1, 1, 0.3, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(addGuestBtn, UtilsGUI.getConstraints(3, 1, 1, 1, 0.1, 1, 10, 50, 20, 50, 1, GridBagConstraints.CENTER));
		
		this.formNewEstadia.add(inputsPanel);
		this.formNewEstadia.add(Box.createRigidArea(new Dimension(0, 50)));
	}
	
	private void configGuestsTable() {
		configGuestsDataListeners();
		
		this.guestsTable = new DynamicTable(guestsData);
		scrollPaneGuestsTable = new JScrollPane(guestsTable);
		scrollPaneGuestsTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
 		
 		this.formNewEstadia.add(scrollPaneGuestsTable);
 		this.formNewEstadia.add(Box.createRigidArea(new Dimension(0, 20)));
	}
	
	private void updateGuestsTable() {
		this.formNewEstadia.removeAll();
		
		// RE-ADD INPUTS
		this.inputsPanel.add(bookingId, UtilsGUI.getConstraints(0, 0, 1, 1, 0.3, 1, 0, 0, 10, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(guest, UtilsGUI.getConstraints(0, 1, 1, 1, 0.3, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(dni, UtilsGUI.getConstraints(1, 1, 1, 1, 0.3, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(age, UtilsGUI.getConstraints(2, 1, 1, 1, 0.3, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(addGuestBtn, UtilsGUI.getConstraints(3, 1, 1, 1, 0.1, 1, 10, 50, 20, 50, 1, GridBagConstraints.CENTER));
		this.formNewEstadia.add(inputsPanel);
		this.formNewEstadia.add(Box.createRigidArea(new Dimension(0, 50)));
		
		// RECREATE TABLE
		configGuestsTable();
		
		// READD BUTTON
		configButtonEstadia();
		
		this.revalidate();
	}
	
	private void configButtonEstadia() {
		this.createEstadia = new Button("Registrar", new Dimension(300, 40));
		this.createEstadia.setAlignmentX(CENTER_ALIGNMENT);
		this.createEstadia.addActionListener(this.createAction.apply(this));
		this.formNewEstadia.add(this.createEstadia);
	}
	
	private void configButtonAddGuest() {
		this.addGuestBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (guest.getValue().isEmpty() && dni.getValue().isEmpty() && age.getValue().isEmpty())
					return;
				Huesped huesped = new Huesped(guest.getValue(), dni.getValue(), Integer.parseInt(age.getValue()));
				guestsData.addData(huesped);
				cleanUserInputs();
				updateGuestsTable();
			}
		});
	}
	
	private Function<GuestsData, ActionListener> deleteAction() {
		Function<GuestsData, ActionListener> deleteAction = (data) -> {
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Button btn = (Button) e.getSource();
					data.remove(Long.parseLong(btn.getName()));
					updateGuestsTable();
				}
			};
		};
		return deleteAction;
	}
	
	private void configGuestsDataListeners() {
		((GuestsData) guestsData).withDeleteAction(deleteAction());
	}
	
	public List<Huesped> guests() {
		return guestsData.getTypedData();
	}
	
	public String bookingId() {
		return bookingId.getValue();
	}
	
	// ================================================================================================================================================================================
	// INYECCION DE PANELES
	// ================================================================================================================================================================================
	
	public void withoutResults() {
		this.dataPanel.emptyResults();
		this.estadiaInjected = null;
	}
	
	private JPanel getColumnPanelDataEstadia() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false);
		return panel;
	}
	
	private JPanel getLabelDataEstadia(String title, String value) {
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
	
	public void injectData(Estadia estadia) {
		// UPDPATE CURRENT ESTADIA
		this.estadiaInjected = estadia;
		
		// PANEL
		this.estadiasDataPanel = new JPanel();
		this.estadiasDataPanel.setLayout(new GridBagLayout());
		this.estadiasDataPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		this.estadiasDataPanel.setOpaque(false);
		
		List<String> titles = Arrays.asList("Nro", "Llegada", "Salida", "Titular", "Reserva", "Huespedes", "Facturado");
		List<String> values = Arrays.asList(
				estadia.getId().toString(),
				Utils.stringLocalDate(estadia.getFechaIngreso()),
				Utils.stringLocalDate(estadia.getFechaSalida()),
				estadia.getReserva().getTitular().getNombre().toString(),
				estadia.getReserva().getNumero().toString(),
				String.valueOf(estadia.getHuespedes().size()),
				String.valueOf(estadia.calcularTotal().toString())
		);
		
		// COLUMNS DATA
		Integer middle = (titles.size() / 2);
		JPanel col1 = getColumnPanelDataEstadia();
		JPanel col2 = getColumnPanelDataEstadia();
		
		for(int i = 0; i < titles.size(); i++) {
			if (middle < i)
				col1.add(getLabelDataEstadia(titles.get(i), values.get(i)));
			else
				col2.add(getLabelDataEstadia(titles.get(i), values.get(i)));
		}
		
		this.estadiasDataPanel.add(col2, UtilsGUI.getConstraints(0, 0, 1, 1, 0.4, 1, 0, 0, 0, 0, 1, GridBagConstraints.WEST));
		this.estadiasDataPanel.add(col1, UtilsGUI.getConstraints(1, 0, 1, 1, 0.4, 1, 0, 0, 0, 0, 1, GridBagConstraints.WEST));
		
		// BUTTONS
		this.actionsEstadiasPanel = new VerticalButtons(3);
		this.updateEstadiaBtn = new Button("Modificar");
		this.updateEstadiaBtn.addActionListener(this.updateAction.apply(this));
		this.guestsEstadiaBtn = new Button("Huespedes");
		this.guestsEstadiaBtn.addActionListener(this.guestsAction.apply(this));
		this.billingEstadiaBtn = new Button("Facturar");
		this.billingEstadiaBtn.addActionListener(this.billingAction.apply(this));
		
		this.actionsEstadiasPanel.add(this.updateEstadiaBtn);
		this.actionsEstadiasPanel.add(this.guestsEstadiaBtn);
		this.actionsEstadiasPanel.add(this.billingEstadiaBtn);
		
		this.estadiasDataPanel.add(this.actionsEstadiasPanel, UtilsGUI.getConstraints(3, 0, 1, 1, 0.2, 1, 60, 0, 60, 0, 1, GridBagConstraints.EAST));
		
		// REFRESH
		this.dataPanel.injectDataPanel(this.estadiasDataPanel);
	}
	
	public void formNewEstadiaInjectData(Estadia estadia) {
		// RESTART FROM PANEL
		configFormNewEstadia();
		
		// SET INFORMATION
		this.bookingId.getInput().setText(estadia.getId().toString());
		this.guestsData = new GuestsData(estadia.getHuespedes(), deleteAction());
		
		// REFRESH TABLE
		updateGuestsTable();
	}
	
	public Estadia getEstadiaInjected() {
		return estadiaInjected;
	}

	@Override
	public void injectFactura(Factura factura) {
		// TODO Auto-generated method stub
		this.dialogFactura = new DialogFactura(frame, "Factura Electronica", factura);
	}
}
