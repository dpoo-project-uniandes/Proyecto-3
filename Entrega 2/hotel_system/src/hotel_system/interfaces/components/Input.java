package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Input extends JPanel {
	
	private JLabel title;
	private RoundJTextField input;
	
	public Input(String title) {
		configPanel();
		configTitle(title);
		configInput();
	}
	
	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	private void configTitle(String title) {
		this.title = new JLabel(title + ":");
		this.title.setBackground(Color.WHITE);
		this.title.setForeground(Color.BLACK);
		this.title.setOpaque(true);
		this.title.setFont(new Font(getName(), Font.BOLD, 16));
		this.title.setHorizontalAlignment(SwingConstants.LEFT);
		this.title.setAlignmentX(LEFT_ALIGNMENT);
		this.title.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(this.title);
	}
	
	private void configInput() {
		this.input = new RoundJTextField();
		this.input.setOpaque(true);
		this.input.setBackground(Color.WHITE);
		this.input.setHorizontalAlignment(SwingConstants.LEFT);
		this.input.setAlignmentX(LEFT_ALIGNMENT);
		this.add(this.input);
	}

}
