package hotel_system.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.components.Button;

public class BookingManagement extends JPanel {
	
	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private JLabel withoutResultsLabel;
	private Button newBookingBtn;
	private JPanel finderAndNewBookiingPanel;
	
	public BookingManagement(
		String user,
		Function<Finder, ActionListener> findAction
	) {
		configPanel();
		configHeader(user, "Reservas");
		configFinder("Documento Titular / Nro de Reserva", findAction);
		configNewBookingButton();
		configPanelFinderAndNewBooking();
		configDataPanel("Detalles de la Reserva");
		configComponents();
	}
	
	private void configComponents() {
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(finderAndNewBookiingPanel);
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
	
	private void configNewBookingButton() {
		this.newBookingBtn = new Button("Nueva Reserva");
	}
	
	private void configPanelFinderAndNewBooking() {
		this.finderAndNewBookiingPanel = new JPanel();
		this.finderAndNewBookiingPanel.setLayout(new BorderLayout());
		this.finderAndNewBookiingPanel.setAlignmentX(LEFT_ALIGNMENT);
		this.finderAndNewBookiingPanel.setOpaque(false);
		
		this.finderAndNewBookiingPanel.add(this.finder, BorderLayout.WEST);
		this.finderAndNewBookiingPanel.add(this.newBookingBtn, BorderLayout.EAST);
	}
	
	private void configDataPanel(String title) {
		// DATA PANEL
		this.dataPanel = new DataPanel(title);
		this.dataPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		// WITHOUT RESULTS LABEL
		this.withoutResultsLabel = new JLabel("Sin Resultados");
		this.withoutResultsLabel.setFont(new Font(getName(), Font.PLAIN, 20));
		this.withoutResultsLabel.setBorder(new EmptyBorder(160,50,160,50));
		this.withoutResultsLabel.setForeground(Color.GRAY);
		this.withoutResultsLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.dataPanel.add(this.withoutResultsLabel);
	}
}