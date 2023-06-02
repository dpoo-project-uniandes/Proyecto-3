package hotel_system.interfaces.recepcionista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
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
import hotel_system.interfaces.components.FormDataTable;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.models.Factura;
import hotel_system.models.Producto;

public class MenuProductosServicios extends JPanel {
	

	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private Button payLaterBtn;
	private Button payNowBtn;
	private VerticalButtons verticalButtons;
	private JPanel finderAndButtonsPanel;
	private String title;
	private DynamicTable productsTable;
	private JScrollPane scrollPaneProductsTable;
	
	private FormDataTable<Producto> productsData;
	
	public MenuProductosServicios(
		    String user,
		    FormDataTable<Producto> productsData,
			HeaderButtonsActions headerButtonsActions,
		    Function<Finder, ActionListener> generateAction,
		    Function<MenuProductosServicios, ActionListener> payNowAction,
		    Function<MenuProductosServicios, ActionListener> payLaterAction
		) {
		    this.title = "Inventario Productos";
		    this.productsData = productsData;
		    configPanel();
		    configHeader(user, "Productos", headerButtonsActions);
		    configFinder("Numero de habitacion", generateAction);
		    configPayNowButton(payNowAction);
		    configPayLaterButton(payLaterAction);
		    configVerticalButtons();
		    configPanelFinderAndButtons();
		    configTable();
		    configComponents();
		}

	
	private void configComponents() {
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(finderAndButtonsPanel);
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
		Function<Finder, ActionListener> generateAction
	) {
		this.finder = new Finder(text, generateAction);
	}
	
	private void configPayNowButton(Function<MenuProductosServicios, ActionListener> payNowAction) {
		this.payNowBtn = new Button("Pagar ahora");
		this.payNowBtn.addActionListener(payNowAction.apply(this));
	}
	
	private void configPayLaterButton(Function<MenuProductosServicios, ActionListener> payLaterAction) {
		this.payLaterBtn = new Button("Pagar despues");
		this.payLaterBtn.addActionListener(payLaterAction.apply(this));
	}
	
	private void configVerticalButtons() {
		this.verticalButtons = new VerticalButtons(2);
		this.verticalButtons.addButton(payNowBtn);
		this.verticalButtons.addButton(payLaterBtn);
	}
	
	private void configPanelFinderAndButtons() {
		this.finderAndButtonsPanel = new JPanel();
		this.finderAndButtonsPanel.setLayout(new GridBagLayout());
		this.finderAndButtonsPanel.setOpaque(false);
		this.finderAndButtonsPanel.add(this.finder, UtilsGUI.getConstraints(0, 0, 1, 1, 0.1, 1, 0, 0, 0, 600, 1, GridBagConstraints.WEST));
		this.finderAndButtonsPanel.add(this.verticalButtons, UtilsGUI.getConstraints(2, 0, 1, 1, 0.1, 1, 0, 0, 0, 0, 1, GridBagConstraints.EAST));
		this.finderAndButtonsPanel.setMaximumSize(new Dimension(5000, 50));
	}
	
	private void configTable() {
		this.productsTable = new DynamicTable(productsData);
		this.scrollPaneProductsTable = new JScrollPane(productsTable);
		this.scrollPaneProductsTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
	    this.dataPanel = new DataPanel(title);
		this.dataPanel.injectScrollPane(scrollPaneProductsTable);
	}
	
	private void cleanUserInputs() {
		((ProductsData)productsData).clean();
		this.finder.setValue("");
	}
	
	public void injectDataFactura(Factura factura) {
		// CLEAN
		cleanUserInputs();
		
		// TODO mejorar presentacion
		String facturaString = "FACTURA ELECTRONICA\n"
				+ "Titular: "
				+ factura.getTitular().getNombre()
				+ "\nDNI: "
				+ factura.getTitular().getDni()
				+ "\nValor Total: "
				+ factura.getValorTotal();
        JOptionPane.showMessageDialog(this, facturaString);
	}
	
	public String getHabitacion() {
		return finder.getValue();
	}
	
	public Map<String, Integer> getProductsMap() {
		Map<String, Integer> data = new HashMap<>();
		productsData.getData().stream().forEach(r -> {
			AddItems addItems = (AddItems) r.get(r.size()-1);
			if (addItems.getValue() > 0)
				data.put((String) r.get(0), addItems.getValue());
		});
		return data;
	}
}