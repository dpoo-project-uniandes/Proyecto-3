package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DataPanel extends JPanel {
	
	private JPanel titlePanel;
	private JLabel title;
	private JLabel withoutResultsLabel;
	private JPanel dataPanel;
		
	public DataPanel(String title) {
		configPanel();
		configTitlePanel(title);
		configDataPanel();
		configWithoutResults();
		configComponents();
	}
	
	private void configComponents() {
		this.add(titlePanel);
		this.add(dataPanel);
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
	    this.titlePanel.setMaximumSize(new Dimension(5000, 50));
	}
	
	private void configDataPanel() {
		this.dataPanel = new JPanel();
		this.dataPanel.setOpaque(false);
		this.dataPanel.setLayout(new GridBagLayout());
		this.dataPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	private void configWithoutResults() {
		// WITHOUT RESULTS LABEL
		this.withoutResultsLabel = new JLabel("Sin Resultados");
		this.withoutResultsLabel.setFont(new Font(getName(), Font.PLAIN, 20));
		this.withoutResultsLabel.setForeground(Color.GRAY);
		this.dataPanel.add(this.withoutResultsLabel, Utils.getConstraints(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, GridBagConstraints.CENTER));
	}

}