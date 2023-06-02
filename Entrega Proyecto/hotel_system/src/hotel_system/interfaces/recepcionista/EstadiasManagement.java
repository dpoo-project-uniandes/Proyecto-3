package hotel_system.interfaces.recepcionista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.UtilsGUI;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.DynamicTable;
import hotel_system.interfaces.components.FormDataTable;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.Input;
import hotel_system.models.Estadia;
import hotel_system.models.Huesped;

public class EstadiasManagement extends JPanel {

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
	private Button deleteEstadiaBtn;
	private Button cancelEstadiaBtn;
	private Estadia estadiaInjected;
	
	// ACTIONS LISTENER
	Function<EstadiasManagement, ActionListener> createAction;
	Function<EstadiasManagement, ActionListener> updateAction;
	Function<EstadiasManagement, ActionListener> guestsAction;
	Function<EstadiasManagement, ActionListener> billingAction;
	
	public EstadiasManagement(
			String user,
			FormDataTable guestsData,
			HeaderButtonsActions headerButtonsActions,
			Function<Finder, ActionListener> findAction,
			Function<EstadiasManagement, ActionListener> createAction,
			Function<EstadiasManagement, ActionListener> updateAction,
			Function<EstadiasManagement, ActionListener> guestsAction,
			Function<EstadiasManagement, ActionListener> billingAction
	) {
		this.title = "Detalles de la Estadia";
		this.guestsData = guestsData;
		this.createAction = createAction;
		this.updateAction = updateAction;
		this.guestsAction = guestsAction;
		this.billingAction = billingAction;
		configPanel();
		configHeader(user, "Estadias", headerButtonsActions);
		configFinder("Documento Titular / Nro de Reserva", findAction);
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
		this.finder.setValue("");;
	}
	
	private void configFormNewEstadia() {
		// CLEAN
		cleanUserInputs();
		
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
		this.guest = Input.Instance("Nombre", "text");
		this.dni = Input.Instance("Documento", "text");
		this.age = Input.Instance("Edad", "number");
		this.addGuestBtn = new Button("Agregar");
		configButtonAddGuest();

		this.inputsPanel.add(guest, UtilsGUI.getConstraints(0, 0, 1, 1, 0.25, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(dni, UtilsGUI.getConstraints(1, 0, 1, 1, 0.25, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(age, UtilsGUI.getConstraints(2, 0, 1, 1, 0.25, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.inputsPanel.add(addGuestBtn, UtilsGUI.getConstraints(3, 0, 1, 1, 0.1, 1, 20, 50, 20, 50, 1, GridBagConstraints.CENTER));
		this.inputsPanel.setMaximumSize(new Dimension(5000, 300));
		
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
		configFormNewEstadia();
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
				updateGuestsTable();
			}
		});
	} 
	
	private void configGuestsDataListeners() {
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
		((GuestsData) guestsData).withDeleteAction(deleteAction);
	}
}
