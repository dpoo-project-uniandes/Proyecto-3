package hotel_system.interfaces;

import services.SecValidation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class HotelSystemInterface extends JFrame {
	
	public static void main(String[] args) throws IOException  {
		HotelSystemInterface HotelSystemInterface = new HotelSystemInterface();
	}
	
	private Login login;
	private MenuPrincipal menuP;
	private MenuAdmin menuA;
	private MenuCargaAdmin menuCA;
	
	public HotelSystemInterface() throws IOException {
		configLogin();
		componentsFrame();
	}
	

	private void configLogin() {
	    // ACTIONS LISTENERS
	    Function<Login, ActionListener> loginAction = (panel) -> {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Boolean authenticated = login(
	                    panel.getUserInput().getInput().getText(),
	                    panel.getPasswordInput().getInput().getText()
	                );
	                if (!authenticated)
	                    panel.displayUnauthorizedWarning();
	                else
	                    dispose();
	            }
	        };
	    };
	    Function<Login, ActionListener> signUpAction = (panel) -> {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                showRegistrarse();
	            }
	        };
	    };
	    this.login = new Login(loginAction, signUpAction);

	    // LAYOUT CONFIGURATION
	    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
	    this.add(login);

	    // SETTINGS
	    this.getContentPane().setBackground(Color.WHITE);
	    this.setSize(600, 700);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    this.setTitle("Hotel System Management");
	    this.setResizable(false);
	}
	
	private void showLogin() {
	    this.getContentPane().removeAll();
	    this.add(login);
	    this.revalidate();
	    this.repaint();
	}

	private void showRegistrarse() {
	    this.remove(login);

	    Function<Registrarse, ActionListener> registerAction = (panel) -> {
	        return new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String password = panel.getPasswordInput().getInput().getText();
	                if (!isValidPassword(password)) {
	                    panel.displayPasswordRequirementsWarning();
	                } else {
	                    System.out.println("Usuario registrado correctamente");
	                    showLogin();
	                }
	            }
	        };
	    };
	    Registrarse registrarse = new Registrarse(registerAction);
	    this.add(registrarse);

	    this.revalidate();
	    this.repaint();
	}

	
	private boolean isValidPassword(String password) {
	    return SecValidation.checkPassword(password);
	}
	
	private Boolean login(String user, String password) {
		return true;
	}
	
	private void componentsFrame() {
		
	}
}


