package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

import hotel_system.interfaces.UtilsGUI;

public class DynamicTable extends JPanel {
	
	private List<String> headers;
	private List<List<Object>> data;
	private List<Double> weights;
	
	public DynamicTable(FormDataTable<?> formDataTable) {
		this.headers = formDataTable.getHeaders();
		this.data = formDataTable.getData();
		this.weights = formDataTable.getWeights();
		configPanel();
		configTable();
	}
	
	private void configPanel() {
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
	}
	
	private void configTable() {
		// HEADERS
		for(int i = 0; i < this.headers.size(); i++) {
			JLabel header = new JLabel(this.headers.get(i));
			header.setHorizontalAlignment(SwingConstants.CENTER);
	        header.setOpaque(true);
	        header.setBackground(Color.BLACK);
	        header.setForeground(Color.WHITE);
	        header.setFont(new Font("Arial", Font.BOLD, 15));
	        header.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE), BorderFactory.createEmptyBorder(0,0,0,0)));
	        header.setMaximumSize(new Dimension(1000, 50));
	        header.setPreferredSize(new Dimension(getWidth(), 40));
			this.add(header, UtilsGUI.getConstraints(i, 0, 1, 1, weights.get(i), 1, 0, 0, 0, 0, 1, GridBagConstraints.CENTER));
		}
		
		// DATA
		for(int r = 0; r < this.data.size(); r++) {
			for(int i = 0; i < this.data.get(r).size(); i++) {
				Object obj = this.data.get(r).get(i);
				if (obj instanceof String) {
					JLabel cellString = new JLabel(obj.toString());
			        cellString.setHorizontalAlignment(SwingConstants.CENTER);
			        cellString.setFont(new Font("Arial", Font.PLAIN, 15));
			        cellString.setForeground(Color.BLACK);
			        cellString.setBackground(Color.WHITE);
			        cellString.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));
					this.add(cellString, UtilsGUI.getConstraints(i, r+1, 1, 1, weights.get(i), 1, 0, 0, 0, 0, 1, GridBagConstraints.CENTER));
				}
				if (obj instanceof Component) {
					((JComponent) obj).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));
					this.add((Component) obj, UtilsGUI.getConstraints(i, r+1, 1, 1, weights.get(i), 1, 0, 0, 0, 0, 1, GridBagConstraints.CENTER));
				}
			}
		}
	}

}
