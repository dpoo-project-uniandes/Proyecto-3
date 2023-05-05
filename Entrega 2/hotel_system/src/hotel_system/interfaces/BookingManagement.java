package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BookingManagement extends JPanel {
	
	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	
	public BookingManagement(
		String user,
		Function<Finder, ActionListener> findAction
	) {
		configPanel();
		configHeader(user, "Reservas");
		configFinder("Documento Titular / Nro de Reserva", findAction);
		configDataPanel("Detalles de la Reserva");
		configComponents();
	}
	
	private void configComponents() {
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(finder);
		this.add(Box.createRigidArea(new Dimension(0, 50)));
		this.add(dataPanel);
	}
	
	private void configPanel() {
    	this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(50, 50, 50, 50));
	}
	
	private void configHeader(String user, String title) {
    	this.header = new MainHeader(user, title);
	}
	
	private void configFinder(
		String text,
		Function<Finder, ActionListener> findAction
	) {
		this.finder = new Finder(text, findAction);
		this.finder.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	private void configDataPanel(String title) {
		this.dataPanel = new DataPanel(title);
		this.dataPanel.setAlignmentX(LEFT_ALIGNMENT);
	}
}
