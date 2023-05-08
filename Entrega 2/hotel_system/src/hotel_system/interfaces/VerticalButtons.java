package hotel_system.interfaces;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VerticalButtons extends JPanel {
	
	private JPanel verticalPanel;
	private Integer quantityButtons;
	
	public VerticalButtons(Integer quantityButtons) {
		this.quantityButtons = quantityButtons;
		configPanel();
	}
	
	private void configPanel() {
		this.setOpaque(false);
        this.setLayout(new GridLayout(quantityButtons, 1, 40, 30));
	}
	
	public void addButton(JButton btn) {
		this.add(btn);
	}

}
