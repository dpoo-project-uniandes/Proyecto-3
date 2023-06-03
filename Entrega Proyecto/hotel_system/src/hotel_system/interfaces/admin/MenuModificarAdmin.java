package hotel_system.interfaces.admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.UtilsGUI;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.Input;
import hotel_system.models.Consumible;
import hotel_system.models.Producto;
import hotel_system.models.ProductoRestaurante;
import hotel_system.utils.Utils;

public class MenuModificarAdmin extends JPanel{
	
	// BASIC
	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private Button createProductBtn;
	private VerticalButtons verticalButtons;
	private JPanel finderAndNewBookingPanel;
	private String title;
	private Button newProductBtn;
	private Producto productInjected;
	private Function<MenuModificarAdmin, ActionListener> newProductAction;
	private Function<MenuModificarAdmin, ActionListener> updateAction;
	private Function<MenuModificarAdmin, ActionListener> deleteAction;
	private Button updateProductBtn;
	private Button deleteProductBtn;
	// FORM NEW PRODUCT
	private JPanel formNewProduct;
	private JPanel inputsPanel;
	private Input id;
	private Input nombre;
	private Input precio;
	// PRODUCTO RESTAURANTE
	private Input tipo;
	private Input alCuarto;
	private Input rangoHorario1;
	private String tipoProducto;
	private Input rangoHorario2;
	private JPanel productDataPanel;
	private VerticalButtons actionsProductPanel;



	public MenuModificarAdmin(
			String user,
			HeaderButtonsActions headerButtonsActions,
			Function<Finder, ActionListener> findAction,
			Function<MenuModificarAdmin, ActionListener> deleteAction,
			Function<MenuModificarAdmin, ActionListener> updateAction,
			Function<MenuModificarAdmin, ActionListener> createAction, 
			Function<MenuModificarAdmin, ActionListener> newProductAction
			
		) {
			this.productInjected = null;
			this.title = "Detalles del consumible";
			configPanel();
			configHeader(user, "Modificar Productos o Servicios", headerButtonsActions);
			configFinder("ID del producto/servicio: ", findAction);
			configNewBookingButton(createAction);
			configVerticalButtons();
			configPanelFinderAndNewBooking();
			configDataPanel(title);
			configComponents();
			this.deleteAction = deleteAction;
			this.updateAction = updateAction;
			this.newProductAction = newProductAction;
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
		
		private void configHeader(String user, String title, HeaderButtonsActions headerButtonsActions) {
	    	this.header = new MainHeader(user, title, headerButtonsActions);
		}
		
		private void configFinder(
			String text,
			Function<Finder, ActionListener> findAction
		) {
			this.finder = new Finder(text, findAction);
		}
		
		private void configNewBookingButton(Function<MenuModificarAdmin, ActionListener> createAction) {
			this.createProductBtn = new Button("Nuevo Consumible");
			this.createProductBtn.addActionListener(createAction.apply(this));
			// this.formNewProduct.add(this.createProductBtn);
		}
		
		private void configVerticalButtons() {
			this.verticalButtons = new VerticalButtons(1);
			this.verticalButtons.addButton(createProductBtn);
		}

		public void configNewFormProduct() {
			this.formNewProduct = new JPanel();
			this.formNewProduct.setLayout(new GridBagLayout());
			this.formNewProduct.setOpaque(false);
			// this.formNewProduct.setAlignmentX(LEFT_ALIGNMENT);
			// INPUTS
			configFormNewProductInputs();
			
			
			// BUTTON
			configButtonNewProduct();
			
			// INJECT
			this.dataPanel.injectDataPanel(this.formNewProduct);
		}
		
		private void configButtonNewProduct() {
			this.newProductBtn = new Button("Crear Producto", new Dimension(200, 50));
			this.newProductBtn.setAlignmentY(TOP_ALIGNMENT);
			this.newProductBtn.setAlignmentX(CENTER_ALIGNMENT);

			this.newProductBtn.addActionListener(this.newProductAction.apply(this));
			this.formNewProduct.add(this.newProductBtn);
		}

		private void configFormNewProductInputs() {
			if (tipoProducto.equals("Producto") || tipoProducto.equals("ProductoSpa")) {
				this.inputsPanel = new JPanel();
				this.inputsPanel.setLayout(new GridLayout(2, 4, 230, 60));
				this.inputsPanel.setOpaque(false);
				this.inputsPanel.setAlignmentY(TOP_ALIGNMENT);
				// this.inputsPanel.setAlignmentX(LEFT_ALIGNMENT);
				// this.id = Input.Instance("ID", "text");
				this.nombre = Input.Instance("Nombre del Producto", "text");
				this.precio = Input.Instance("Precio", "text");
				// this.tipo = Input.Instance("Tipo", "text");
				// this.inputsPanel.add(this.id);
				this.inputsPanel.add(this.nombre);
				this.inputsPanel.add(this.precio);
				// this.inputsPanel.add(this.tipo);
				this.formNewProduct.add(this.inputsPanel);
				this.formNewProduct.add(Box.createRigidArea(new Dimension(400, 40)));
			} else {
				this.inputsPanel = new JPanel();
				this.inputsPanel.setLayout(new GridLayout(4, 2, 230, 20));
				this.inputsPanel.setOpaque(false);
				this.inputsPanel.setAlignmentX(LEFT_ALIGNMENT);
				// this.id = Input.Instance("ID", "text");
				this.nombre = Input.Instance("Nombre", "text");
				this.precio = Input.Instance("Precio", "text");
				this.tipo = Input.Instance("Tipo", "text");
				this.alCuarto = Input.Instance("Al Cuarto (Si/No)", "text");
				this.rangoHorario1 = Input.Instance("Horario Inicio en formato yyyy/MM/dd", "text");
				this.rangoHorario2 = Input.Instance("Horario Fin en formato yyyy/MM/dd", "text");
				// this.inputsPanel.add(this.id);
				this.inputsPanel.add(this.nombre);
				this.inputsPanel.add(this.precio);
				this.inputsPanel.add(this.tipo);
				this.inputsPanel.add(this.alCuarto);
				this.inputsPanel.add(this.rangoHorario1);
				this.inputsPanel.add(this.rangoHorario2);
				this.formNewProduct.add(this.inputsPanel);
				this.formNewProduct.add(Box.createRigidArea(new Dimension(200, 20)));

			}
			
		}

		public void cleanUserInputs() {
			this.finder.setValue("");
			// PANEL
			this.productDataPanel = new JPanel();
			this.productDataPanel.setLayout(new GridBagLayout());
			this.productDataPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
			this.productDataPanel.setOpaque(false);
			// REFRESH
			this.dataPanel.injectDataPanel(this.productDataPanel);
			this.revalidate();
		}
		
		public void formNewBookingInjectData(Producto producto) {
			// Restart form panel
			configNewFormProduct();
			
			// Set information from booking data
			if (tipoProducto.equals("Producto") || tipoProducto.equals("ProductoSpa")) {
				this.nombre.getInput().setText(producto.getNombre());
				this.precio.getInput().setText(producto.getPrecio().toString());
			} else {
				ProductoRestaurante productoRestaurante = (ProductoRestaurante) producto;
				this.nombre.getInput().setText(productoRestaurante.getNombre());
				this.precio.getInput().setText(productoRestaurante.getPrecio().toString());
				this.tipo.getInput().setText(productoRestaurante.getTipo());
				this.alCuarto.getInput().setText(productoRestaurante.getAlCuarto() ? "Si" : "No");
				this.rangoHorario1.getInput().setText(Utils.stringLocalDate(productoRestaurante.getRangoHorario().get(0))) ;
				this.rangoHorario2.getInput().setText(Utils.stringLocalDate(productoRestaurante.getRangoHorario().get(productoRestaurante.getRangoHorario().size()-1)));

				
			}
		}


		public Map<String, String> getDataMap() {
			if (tipoProducto.equals("Producto") || tipoProducto.equals("ProductoSpa")) {
				return Map.of(
					"id", productInjected == null ? Utils.generateId4Length().toString() : productInjected.getId().toString(),
					"nombre", this.nombre.getValue(),
					"precio", this.precio.getValue()
				);
			} else {
				return Map.of(
					"id", productInjected == null ? Utils.generateId4Length().toString() : productInjected.getId().toString(),
					"nombre", this.nombre.getValue(),
					"precio", this.precio.getValue(),
					"tipo", this.tipo.getValue().toLowerCase().equals("producto") ? "producto" : "productoRestaurante",
					"alCuarto", this.alCuarto.getValue().toLowerCase().equals("si") ? "true" : "false",
					"rangoHorario1", this.rangoHorario1.getValue().toLowerCase().replace("-", "/"),
					"rangoHorario2", this.rangoHorario2.getValue().toLowerCase().replace("-", "/")
				);
			}

			
		}
		private void configPanelFinderAndNewBooking() {
			this.finderAndNewBookingPanel = new JPanel();
			this.finderAndNewBookingPanel.setLayout(new GridBagLayout());
			this.finderAndNewBookingPanel.setOpaque(false);
			// this.finderAndNewBookingPanel.setAlignmentX(LEFT_ALIGNMENT);
			
			this.finderAndNewBookingPanel.add(this.finder, UtilsGUI.getConstraints(0, 0, 1, 1, 0.2, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
			this.finderAndNewBookingPanel.add(this.verticalButtons, UtilsGUI.getConstraints(1, 0, 1, 1, 0.1, 1, 20, 600, 0, 0, 1, GridBagConstraints.EAST));
			
			this.finderAndNewBookingPanel.setMaximumSize(new Dimension(5000, 80));
		}
		private void configDataPanel(String title) {
			// DATA PANEL
			this.dataPanel = new DataPanel(title);
			// this.dataPanel.setAlignmentX(LEFT_ALIGNMENT);
		}
		
		public void withoutResults() {
			this.dataPanel.emptyResults();
			this.productInjected = null;
		}
		
		public void injectData(Consumible producto) {

			this.productInjected = (Producto) producto;

			this.productDataPanel = new JPanel();
			this.productDataPanel.setLayout(new GridBagLayout());
			this.productDataPanel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
			this.productDataPanel.setOpaque(false);
			System.out.println(getTipoProducto());

			List<String> titles;
			List<String> values;
			if (getTipoProducto().equals("Producto") || getTipoProducto().equals("ProductoSpa")){
				titles = List.of("ID", "Nombre", "Precio", "Tipo");
				System.out.println(getTipoProducto());
				values = List.of(
					this.productInjected.getId().toString(),
					this.productInjected.getNombre(),
					this.productInjected.getPrecio().toString(),
					getTipoProducto()
				);
			} else {
				titles = List.of("ID", "Nombre", "Precio", "Tipo", "Al Cuarto", "Rango Horario Inicio", "Rango Horario Fin");
				ProductoRestaurante productoRestaurante = (ProductoRestaurante) producto;
				String fecha1 = Utils.stringLocalDate(productoRestaurante.getRangoHorario().get(0));
				String fecha2 = Utils.stringLocalDate(productoRestaurante.getRangoHorario().get(productoRestaurante.getRangoHorario().size() - 1));
				values = List.of(
					this.productInjected.getId().toString(),
					this.productInjected.getNombre(),
					this.productInjected.getPrecio().toString(),
					getTipoProducto(),
					productoRestaurante.getAlCuarto() ? "Si" : "No",
					fecha1,
					fecha2
				);
			}
			// COLUMNS DATA
			Integer middle = (titles.size() / 2);
			JPanel col1 = getColumnPanelDataBooking();
			JPanel col2 = getColumnPanelDataBooking();
			
			for(int i = 0; i < titles.size(); i++) {
				if (middle < i)
					col1.add(getLabelDataBooking(titles.get(i), values.get(i)));
				else
					col2.add(getLabelDataBooking(titles.get(i), values.get(i)));
			}
			this.productDataPanel.add(col2, UtilsGUI.getConstraints(0, 0, 1, 1, 0.4, 1, 0, 0, 0, 0, 1, GridBagConstraints.WEST));
			this.productDataPanel.add(col1, UtilsGUI.getConstraints(1, 0, 1, 1, 0.4, 1, 0, 0, 0, 0, 1, GridBagConstraints.WEST));


			// BUTTONS
			this.actionsProductPanel = new VerticalButtons(2);
			this.updateProductBtn = new Button("Modificar");
			this.updateProductBtn.addActionListener(this.updateAction.apply(this));
			this.deleteProductBtn = new Button("Eliminar");
			this.deleteProductBtn.addActionListener(this.deleteAction.apply(this));
			this.actionsProductPanel.addButton(this.updateProductBtn);
			this.actionsProductPanel.addButton(this.deleteProductBtn);
			
			this.productDataPanel.add(this.actionsProductPanel, UtilsGUI.getConstraints(2, 0, 1, 1, 0.2, 1, 60, 0, 60, 0, 1, GridBagConstraints.EAST));
		
		// REFRESH
		this.dataPanel.injectDataPanel(this.productDataPanel);
		this.revalidate();

	}
	private JPanel getLabelDataBooking(String title, String value) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleLabel = new JLabel(title + ":");
		JLabel valueLabel = new JLabel(value);
		
		// CUSTOMOZATION
		titleLabel.setFont(new Font(getName(), Font.BOLD, 20));
		valueLabel.setFont(new Font(getName(), Font.PLAIN, 20));
		
		panel.add(titleLabel);
		panel.add(valueLabel);
		panel.setOpaque(false);
		
		return panel;
	}

		private JPanel getColumnPanelDataBooking() {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setOpaque(false);
			return panel; 
		}

		public void setTipoProducto(String tipo) {
			this.tipoProducto = tipo;
			
		}
		public String getTipoProducto() {
			return this.tipoProducto;
			
		}
		
		public Producto getProductoActual() {
			return this.productInjected;
			
		}
		
}
