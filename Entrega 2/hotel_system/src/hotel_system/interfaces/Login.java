package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hotel_system.interfaces.components.Input;

public class Login extends JPanel {
	
	private JLabel title;
	private JLabel iconLogin;
	private Input userInput;
	private Input passwordInput;
	private BufferedImage userIcon;
	
	public Login() {
		configPanel();
		configTitle();
		configInputs();
	}
	
	private void configTitle() {
		title = new JLabel("Hotel System Management");
		title.setBackground(Color.WHITE);
		title.setForeground(Color.BLACK);
		title.setOpaque(true);
		title.setFont(new Font(getName(), Font.BOLD, 30));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(title);
	}
	
	private void configInputs() {
		this.userInput = new Input("Usuario");
		this.passwordInput = new Input("Contraseña");
		this.userInput.setAlignmentX(CENTER_ALIGNMENT);
		this.passwordInput.setAlignmentX(CENTER_ALIGNMENT);
		this.add(userInput);
		this.add(passwordInput);
	}
	
	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
}
