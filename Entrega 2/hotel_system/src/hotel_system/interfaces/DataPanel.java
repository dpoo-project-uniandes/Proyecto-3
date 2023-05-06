package hotel_system.interfaces;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DataPanel extends JPanel {
	
	private JPanel titlePanel;
	private JLabel title;
		
	public DataPanel(String title) {
		configPanel();
		configTitlePanel(title);
		configComponents();
	}
	
	private void configComponents() {
		this.add(titlePanel);
	}
	
	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	private void configTitlePanel(String title) {
		// PANEL
		this.titlePanel = new JPanel(new FlowLayout());
		this.titlePanel.setOpaque(true);
		this.titlePanel.setBackground(Color.BLACK);
		
		// TITLE TEXT
		this.title = new JLabel(title);
	    this.title.setFont(new Font(getName(), Font.BOLD, 20));
	    this.title.setBorder(new EmptyBorder(10,20,10,20));
	    this.title.setForeground(Color.WHITE);
	    
	    this.titlePanel.add(this.title);
	}

}
