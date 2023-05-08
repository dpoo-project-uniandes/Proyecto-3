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

	public static Input Instance(String title, String type) {
		switch (type) {
		case "secret":
			return new Input(title, new RoundJPasswordField());
		case "text":
			return new Input(title, new RoundJTextField());
		//case "number": {
		//	return new Input(title, new RoundJNumberField());
		//}
		default:
			return new Input(title, new RoundJTextField());
		}
	}

	private JLabel title;
	private JTextField input;

	private Input(String title, JTextField input) {
		configPanel();
		configTitle(title);
		configInput(input);
	}

	private void configPanel() {
		this.setOpaque(false);
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

	private void configInput(JTextField input) {
		this.input = input;
		this.input.setHorizontalAlignment(SwingConstants.LEFT);
		this.input.setAlignmentX(LEFT_ALIGNMENT);
		this.add(this.input);
	}

	public JLabel getTitle() {
		return title;
	}

	@SuppressWarnings("unchecked")
	public <T extends JTextField> T getInput() {
		return (T) input;
	}
	
	public String getValue() {
		return this.input.getText();
	}
}
