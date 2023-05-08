package hotel_system.interfaces.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.UtilsGUI;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.Button;
import hotel_system.models.Producto;

public class MenuModificarAdmin extends JPanel{
	// BASIC
	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private Button newBookingBtn;
	private VerticalButtons verticalButtons;
	private JPanel finderAndNewBookingPanel;
	private String title;
	private String setTipoProducto;
	

	public MenuModificarAdmin(
			String user,
			Function<Finder, ActionListener> findAction,
			Function<Finder, ActionListener> deleteAction,
			Function<Finder, ActionListener> updateAction,
			Function<Finder, ActionListener> createAction
			
		) {
			this.title = "Detalles del consumible";
			configPanel();
			configHeader(user, "Modificar Productos o Servicios");
			configFinder("ID del producto/servicio: ", findAction);
			configNewBookingButton();
			configVerticalButtons();
			configPanelFinderAndNewBooking();
			configDataPanel(title);
			configComponents();
		}
		  
		private void configComponents() {
			this.add(header);
			this.add(Box.createRigidArea(new Dimension(0, 20)));
			this.add(finderAndNewBookingPanel);
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
		}
		
		private void configNewBookingButton() {
			this.newBookingBtn = new Button("Nuevo Consumible");
			this.newBookingBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//configFormNewBooking();
				}
			});
		}
		
		private void configVerticalButtons() {
			this.verticalButtons = new VerticalButtons(1);
			this.verticalButtons.addButton(newBookingBtn);
		}
		
		private void configPanelFinderAndNewBooking() {
			this.finderAndNewBookingPanel = new JPanel();
			this.finderAndNewBookingPanel.setLayout(new GridBagLayout());
			this.finderAndNewBookingPanel.setOpaque(false);
			this.finderAndNewBookingPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			this.finderAndNewBookingPanel.add(this.finder, UtilsGUI.getConstraints(0, 0, 1, 1, 0.2, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
			this.finderAndNewBookingPanel.add(this.verticalButtons, UtilsGUI.getConstraints(1, 0, 1, 1, 0.1, 1, 20, 600, 0, 0, 1, GridBagConstraints.EAST));
			
			this.finderAndNewBookingPanel.setMaximumSize(new Dimension(5000, 80));
		}
		private void configDataPanel(String title) {
			// DATA PANEL
			this.dataPanel = new DataPanel(title);
			this.dataPanel.setAlignmentX(LEFT_ALIGNMENT);
		}
		
		public void withoutResults() {
			configDataPanel(title);
		}
		
		public void injectData(String booking) {
			System.out.println(booking);
		}


		public void setTipoProducto(String tipo) {
			this.setTipoProducto = tipo;
			
		}
		public String getTipoProducto() {
			return this.setTipoProducto;
			
		}
		
		public Producto getProductoActual() {
			return null;
			
		}
		
}
