package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.Input;
import hotel_system.interfaces.components.Select;
import services.ImagesManager;

public class Registrarse extends JPanel {

    private JLabel title;
	private JLabel iconLogin;
	private ImageIcon userIcon;
	private Select rolSelect;
    private Input userInput;
    private Input passwordInput;
    private Button registerBtn;
    private JLabel passwordRequirements;

    public Registrarse(
    		String[] roles,
    		Function<Registrarse, ActionListener> registerAction
	) {
        configPanel();
        configTitle();
        configSelect(roles);
        configInputs();
        configButtons(registerAction);
        configPasswordRequirements();
        configIconLogin();
        configComponents();
    }
    
    private void configComponents() {
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
		this.add(iconLogin);
        this.add(Box.createRigidArea(new Dimension(0, 30)));
        this.add(rolSelect);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(userInput);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(passwordInput);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(passwordRequirements);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(registerBtn);
    }
    
    private void configPanel() {
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(70, 120, 90, 120));
    }
    
	private void configIconLogin() {
		this.iconLogin = new JLabel();
		try {
			this.userIcon = ImagesManager.ImageIcon("icon-login");
			this.iconLogin.setIcon(ImagesManager.resizeIcon(userIcon, 180, 180));
			this.iconLogin.setAlignmentX(CENTER_ALIGNMENT);
		} catch (Exception e) {
			System.out.println("Error cargando la imagen de userIcon");
			e.printStackTrace();
		}
	}

    private void configTitle() {
        title = new JLabel("Registro de Usuario");
        title.setBackground(Color.WHITE);
        title.setForeground(Color.BLACK);
        title.setOpaque(true);
        title.setFont(new Font(getName(), Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void configInputs() {
        this.userInput = Input.Instance("Usuario", "text");
        this.passwordInput = Input.Instance("Contraseña", "secret");
        this.userInput.setAlignmentX(CENTER_ALIGNMENT);
        this.passwordInput.setAlignmentX(CENTER_ALIGNMENT);
    }
    
    private void configSelect(String[] roles) {
    	this.rolSelect = new Select("Rol", roles);
    	this.rolSelect.setAlignmentX(CENTER_ALIGNMENT);
    }

    private void configButtons(Function<Registrarse, ActionListener> registerAction) {
        this.registerBtn = new Button("Registrarse", new Dimension(130, 40));
        this.registerBtn.setAlignmentX(CENTER_ALIGNMENT);
        if (registerAction != null) {
            this.registerBtn.addActionListener(e -> {
                if (userInput.getInput().getText().length() < 3) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario debe tener al menos 3 caracteres.");
                } else {
                    // Expresión regular para validar la contraseña
                    Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,14}$");
                    Matcher passwordMatcher = passwordPattern.matcher(passwordInput.getInput().getText());

                    if (!passwordMatcher.matches()) {
                        JOptionPane.showMessageDialog(null, "La contraseña debe tener de 6 a 14 caracteres, incluir al menos 1 letra, 1 número y 1 caracter especial.");
                    } else {
                        registerAction.apply(this).actionPerformed(e);
                    }
                }
            });
        }
    }
    
    private void configPasswordRequirements() {
        this.passwordRequirements = new JLabel("La contraseña debe tener de 6 a 14 caracteres, incluir al menos 1 letra, 1 número y 1 caracter especial.");
        this.passwordRequirements.setFont(new Font(getName(), Font.PLAIN, 7));
        this.passwordRequirements.setForeground(Color.RED);
        this.passwordRequirements.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.passwordRequirements.setVisible(false);
    }

    public void displayPasswordRequirementsWarning() {
        this.passwordRequirements.setVisible(true);
        this.removeAll();
        configComponents();
        this.revalidate();
    }

    public Input getUserInput() {
        return userInput;
    }

    public Input getPasswordInput() {
        return passwordInput;
    }
    
    public Select getRolSelect() {
    	return rolSelect;
    }
}
