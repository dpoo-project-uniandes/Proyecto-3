package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Select extends JPanel {
	
	private JLabel title;
	private JComboBox<String> select;
	
	public Select(String title, String[] options) {
		configPanel();
		configTitle(title);
		configSelect(options, null);
	}
	
	public Select(String title, String[] options, Dimension dimension) {
		configPanel();
		configTitle(title);
		configSelect(options, dimension);
	}

	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void configTitle(String title) {
		this.title = new JLabel(title + ":");
		this.title.setForeground(Color.BLACK);
		this.title.setFont(new Font(getName(), Font.BOLD, 16));
		this.title.setHorizontalAlignment(SwingConstants.LEFT);
		this.title.setAlignmentX(LEFT_ALIGNMENT);
		this.title.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(this.title);
	}

	private void configSelect(String[] options, Dimension dimension) {
		if (dimension == null) 
			this.select = new RoundJComboBox(options);
		else 
			this.select = new RoundJComboBox(options, dimension);
		this.select.setAlignmentX(LEFT_ALIGNMENT);
		this.add(this.select);
	}

	public JLabel getTitle() {
		return title;
	}
	
	public String optioinSelected() {
		return (String) this.select.getSelectedItem();
	}

}
