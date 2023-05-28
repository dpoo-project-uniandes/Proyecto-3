package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;

public class DynamicTable extends JPanel {
	
	private List<String> headers;
	private List<List<Object>> data;
	private JScrollPane scrollPane;
	
	public DynamicTable(List<String> headers, List<List<Object>> data) {
		this.headers = headers;
		this.data = data;
		configPanel();
		configTable();
	}
	
	private void configPanel() {
		this.setOpaque(false);
		this.setLayout(new GridLayout(this.data.size() + 1, this.headers.size()));
	}
	
	private void configTable() {
		// HEADERS
		for(int i = 0; i < this.headers.size(); i++) {
			JLabel header = new JLabel(this.headers.get(i));
			header.setHorizontalAlignment(SwingConstants.CENTER);
	        header.setOpaque(true);
	        header.setBackground(Color.BLACK);
	        header.setForeground(Color.WHITE);
	        header.setFont(new Font("Arial", Font.BOLD, 13));
	        header.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE), BorderFactory.createEmptyBorder(0,0,0,0)));
	        header.setMaximumSize(new Dimension(1000, 50));
			this.add(header);
		}
		
		// DATA
		for(int r = 0; r < this.data.size(); r++) {
			for(int i = 0; i < this.data.get(r).size(); i++) {
				Object obj = this.data.get(r).get(i);
				if (obj instanceof String) {
					JLabel cellString = new JLabel(obj.toString());
			        cellString.setHorizontalAlignment(SwingConstants.CENTER);
			        cellString.setFont(new Font("Arial", Font.PLAIN, 13));
			        cellString.setForeground(Color.BLACK);
			        cellString.setBackground(Color.WHITE);
			        cellString.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));
					this.add(cellString);
				}
				if (obj instanceof Component) {
					((JComponent) obj).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));
					this.add((Component) obj);
				}
			}
		}
	}

}
