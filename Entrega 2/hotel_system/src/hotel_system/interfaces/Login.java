package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.Input;

public class Login extends JPanel {
	
	private JLabel title;
	private JLabel iconLogin;
	private Input userInput;
	private Input passwordInput;
	private Button loginBtn;
	private Button signUpBtn;
	private JPanel buttonsPanel;
	private BufferedImage userIcon;
	
	public Login() {
		configPanel();
		configTitle();
		configIconLogin();
		configInputs();
		configButtons();
		configComponents();
	}
	
	private void configComponents() {
		this.add(title);
		this.add(Box.createRigidArea(new Dimension(0, 30)));
		this.add(iconLogin);
		this.add(Box.createRigidArea(new Dimension(0, 30)));
		this.add(userInput);
		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(passwordInput);
		this.add(Box.createRigidArea(new Dimension(0, 40)));
		this.add(buttonsPanel);
	}
	
	private void configTitle() {
		title = new JLabel("Hotel System Management");
		title.setBackground(Color.WHITE);
		title.setForeground(Color.BLACK);
		title.setOpaque(true);
		title.setFont(new Font(getName(), Font.BOLD, 24));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	
	private void configIconLogin() {
		this.iconLogin = new JLabel();
		try {
			this.userIcon = ImageIO.read(new File("./assets/icon-login.png"));
			ImageIcon icon = new ImageIcon(
					new ImageIcon(userIcon)
						.getImage()
						.getScaledInstance(200, 200, Image.SCALE_DEFAULT)
			);
			this.iconLogin.setIcon(icon);
			this.iconLogin.setAlignmentX(CENTER_ALIGNMENT);
		} catch (Exception e) {
			System.out.println("Error cargando la imagen de userIcon");
			e.printStackTrace();
		}
	}
	
	private void configInputs() {
		this.userInput = new Input("Usuario");
		this.passwordInput = new Input("Contraseña");
		this.userInput.setAlignmentX(CENTER_ALIGNMENT);
		this.passwordInput.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void configButtons() {
		this.loginBtn = new Button("Ingresar", new Dimension(130, 40));
		this.signUpBtn = new Button("Registrarse", new Dimension(130, 40));
		
		this.buttonsPanel = new JPanel(new FlowLayout(1, 30, 1));
		this.buttonsPanel.setBackground(Color.WHITE);
		this.buttonsPanel.add(this.loginBtn);
		this.buttonsPanel.add(this.signUpBtn);
	}
	
	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(70, 120, 90, 120));
	}
}
