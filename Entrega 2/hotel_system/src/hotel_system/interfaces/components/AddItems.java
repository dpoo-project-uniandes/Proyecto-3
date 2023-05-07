package hotel_system.interfaces.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddItems extends JPanel {
	
	private Button addBtn;
	private Button removeBtn;
	private RoundJNumberField input;
	private Dimension btnsDimension;
	private Integer value;
	
	public AddItems() {
		this.value = 0;
		configPanel();
		configInput();
		configButtons();
		configComponents();
	}
	
	private void configComponents() {
		this.add(this.addBtn);
		this.add(this.input);
		this.add(this.removeBtn);
	}
	
	private void configPanel() {
		this.setOpaque(false);
		this.setLayout(new FlowLayout());
		this.setBorder(new EmptyBorder(5,5,5,5));
	}
	
	private void configInput() {
		this.input = new RoundJNumberField();
		this.input.setPreferredSize(new Dimension(30, 20));
		this.input.setHorizontalAlignment(SwingConstants.CENTER);
		this.input.setEditable(false);
	}
	
	private void configButtons() {
		// BUTTONS
		this.btnsDimension = new Dimension(20, 20);
		this.addBtn = new Button("+", btnsDimension, 10);
		this.removeBtn = new Button("-", btnsDimension, 10);
		this.addBtn.setPreferredSize(btnsDimension);
		this.removeBtn.setPreferredSize(btnsDimension);
		this.addBtn.setBorder(null);
		this.removeBtn.setBorder(null);
		
		// ACTION LISTENERS
		this.addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				value += 1;
				updateInput();
			}
		});
		this.removeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				value -= 1;
				updateInput();
			}
		});
	}
	
	private void updateInput() {
		this.input.setText(value.toString());
		this.revalidate();
	}
	
	public Integer getValut() {
		return value;
	}
}
