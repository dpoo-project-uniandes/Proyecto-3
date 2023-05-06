package hotel_system.interfaces;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.Input;

public class Finder extends JPanel {
	
	private Input input;
	private Button button;
	
	public Finder(
		String text,
		Function<Finder, ActionListener> findAction
	) {
		configPanel();
		configInput(text);
		configButton(findAction);
		configComponents();
	}
	
	private void configComponents() {
		this.add(input);
		this.add(Box.createRigidArea(new Dimension(15, 0)));
		this.add(button);
	}
	
	private void configPanel() {
		this.setOpaque(false);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}

	private void configInput(String text) {
		this.input = Input.Instance(text, "text");
	}
	
	private void configButton(Function<Finder, ActionListener> findAction) {
		this.button = new Button("Buscar");
		this.button.addActionListener(findAction.apply(this));
		this.button.setAlignmentY(TOP_ALIGNMENT);
	}
}
