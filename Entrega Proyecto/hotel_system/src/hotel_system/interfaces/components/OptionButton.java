package hotel_system.interfaces.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import hotel_system.interfaces.UtilsGUI;
import hotel_system.utils.Utils;

public class OptionButton extends JPanel {
	
	private Long id = Utils.generateId();
	private Button btn;
	
	public OptionButton(String text) {
		configPanel();
		configButton(text);
		configComponents();
	}
	
	public OptionButton(ImageIcon icon) {
		configPanel();
		configButton(icon);
		configComponents();
	}
	
	private void configComponents() {
		btn.setName(id.toString());
		this.add(btn, UtilsGUI.getConstraints(2, 0, 3, 1, 0.2, 1, 5, 0, 5, 0, 0, GridBagConstraints.CENTER));
	}
	
	private void configPanel() {
		this.setOpaque(false);
		this.setLayout(new GridBagLayout());
	}
	
	private void configButton(String text) {
		this.btn = new Button(text);
	}
	
	private void configButton(ImageIcon icon) {
		this.btn = new Button(icon);
	}

	public Button getButton() {
		return btn;
	}
	
	public Long getId() {
		return id;
	}
}
