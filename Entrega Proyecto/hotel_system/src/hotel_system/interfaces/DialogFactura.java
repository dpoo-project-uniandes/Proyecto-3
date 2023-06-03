package hotel_system.interfaces;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import hotel_system.interfaces.components.DynamicTable;
import hotel_system.models.Factura;
import hotel_system.utils.Utils;

public class DialogFactura extends JDialog {
	
	// DATA
	Factura factura;
	
	// COMPONENTS
	JLabel title;
	JPanel voucher;
	JPanel fecha;
	JPanel titular;
	JPanel documento;
	JPanel billigPanel;
	DynamicTable table;
	JScrollPane scrollPane;
	JPanel totalAPagar;
	Window owner;
	
	public DialogFactura(Window owner, String title, Factura factura) {
		super(owner, title, ModalityType.APPLICATION_MODAL);
		this.owner = owner;
		this.factura = factura;
		configDialog();
		configBillingPanel();
		configBillingLabels();
		configTable();
		configBillingFooter();
		configComponents();
	}
	
	private void configComponents() {
		this.add(billigPanel);
		this.setVisible(true);
	}
	
	private void configDialog() {
		this.setSize(900, 700);
		this.setLocationRelativeTo(owner);
		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
	}
	
	private void configBillingPanel() {
		this.billigPanel = new JPanel();
		this.billigPanel.setOpaque(false);
		this.billigPanel.setLayout(new GridBagLayout());
		this.billigPanel.setBackground(Color.WHITE);
	}
	
	private void configBillingLabels() {
		// TITLE
		this.title = defaultBillingTitleLabel("Factura De Venta", 25);
		this.billigPanel.add(title, UtilsGUI.getConstraints(0, 0, 1, 1, 1, 1, 0, 0, 50, 0, 1, GridBagConstraints.WEST));
		
		// METADATA
		this.voucher = defaultBillingTitleValueLabel("Nro. ", factura.getId().toString());
		this.billigPanel.add(voucher, UtilsGUI.getConstraints(0, 1, 1, 1, 1, 1, 0, 0, 10, 100, 1, GridBagConstraints.WEST));
		
		this.fecha = defaultBillingTitleValueLabel("Fecha", Utils.stringLocalDate(factura.getDate()));
		this.billigPanel.add(fecha, UtilsGUI.getConstraints(1, 1, 1, 1, 1, 1, 0, 0, 10, 200, 1, GridBagConstraints.WEST));
		
		this.titular = defaultBillingTitleValueLabel("Cliente", factura.getTitular().getNombre());
		this.billigPanel.add(titular, UtilsGUI.getConstraints(0, 2, 1, 1, 1, 1, 0, 0, 10, 100, 1, GridBagConstraints.WEST));
		
		this.documento = defaultBillingTitleValueLabel("Documento", factura.getTitular().getDni());
		this.billigPanel.add(documento, UtilsGUI.getConstraints(1, 2, 1, 1, 1, 1, 0, 0, 10, 200, 1, GridBagConstraints.WEST));
	}
	
	private void configTable() {
		ConsumibleDataTable consumibleDataTable = new ConsumibleDataTable(factura.getConsumibles());
		this.table = new DynamicTable(consumibleDataTable);
		this.scrollPane = new JScrollPane(table);
		this.scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.billigPanel.add(scrollPane, UtilsGUI.getConstraints(0, 3, 5, 1, 1, 1, 30, 0, 30, 0, 1, GridBagConstraints.CENTER));
	}
	
	private void configBillingFooter() {
		this.billigPanel.add(new JSeparator());
		
		// TOTAL
		JLabel text = defaultBillingTitleLabel("Total a Pagar", 20);
		JLabel value = defaultBillingTitleLabel(factura.calcularValorTotal().toString() + " USD", 20);
		
		this.totalAPagar = new JPanel();
		this.totalAPagar.setOpaque(false);
		this.totalAPagar.setLayout(new GridBagLayout());
		
		this.totalAPagar.add(text, UtilsGUI.getConstraints(0, 0, 1, 1, 1, 1, 0, 0, 0, 350, 1, GridBagConstraints.WEST));
		this.totalAPagar.add(value, UtilsGUI.getConstraints(1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, GridBagConstraints.EAST));
		
		this.billigPanel.add(totalAPagar, UtilsGUI.getConstraints(0, 4, 5, 1, 1, 1, 20, 0, 30, 0, 1, GridBagConstraints.CENTER));
	}
	
	private JLabel defaultBillingTitleLabel(String text, Integer size) {
		JLabel label = new JLabel(text);
		label.setFont(new Font(getName(), Font.BOLD, size));
		return label;
	}

	private JLabel defaultBillingValueLabel(String text, Integer size) {
		JLabel label = new JLabel(text);
		label.setFont(new Font(getName(), Font.PLAIN, size));
		return label;
	}
	
	private JPanel defaultBillingTitleValueLabel(String title, String value) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleLabel = defaultBillingTitleLabel(title + ": ", 18);
		JLabel valueLabel = defaultBillingValueLabel(value, 18);
		
		panel.add(titleLabel);
		panel.add(valueLabel);
		panel.setOpaque(false);
		
		return panel;
	}
}
