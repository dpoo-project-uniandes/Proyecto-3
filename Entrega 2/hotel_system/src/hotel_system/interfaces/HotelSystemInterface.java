package hotel_system.interfaces;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class HotelSystemInterface extends JFrame {
	
	public static void main(String[] args) {
		HotelSystemInterface HotelSystemInterface = new HotelSystemInterface();
	}
	
	private Login login;
	
	public HotelSystemInterface() {
		this.login = new Login();
		loginConfigurationFrame();
		componentsFrame();
	}
	
	private void loginConfigurationFrame() {
		// LAYOUT CONFIGURATION
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(login);
		
		// SETTINGS
		this.getContentPane().setBackground(Color.BLUE);
		this.setSize(600, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Hotel System Management");
		this.setResizable(false);
	}
	
	private void componentsFrame() {
		
	}
}
