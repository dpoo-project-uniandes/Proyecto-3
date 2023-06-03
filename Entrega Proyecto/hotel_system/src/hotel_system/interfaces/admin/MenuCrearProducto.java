package hotel_system.interfaces.admin;

import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JPanel;

import hotel_system.interfaces.MainMenu;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.MenuButton;

public class MenuCrearProducto extends MainMenu {
	private MenuButton restaurante;
    private MenuButton normal;
	private MenuButton spa;


    public MenuCrearProducto(
    		String user,
    		HeaderButtonsActions headerButtonsActions,
    		Function<MenuCrearProducto, ActionListener> restaurante,
    		Function<MenuCrearProducto, ActionListener> normal,
			Function<MenuCrearProducto, ActionListener> spa

	) {
    	super(user, "Escoge el tipo de producto", 3, headerButtonsActions);
        configMenu(restaurante, normal, spa);
    }
    
    private void configMenu(
    		Function<MenuCrearProducto, ActionListener> restaurante,
    		Function<MenuCrearProducto, ActionListener> normal,
			Function<MenuCrearProducto, ActionListener> spa
	) {    	
    	// BUTTONS
    	this.restaurante = new MenuButton("Producto Restaurante", "rest");
    	this.normal = new MenuButton("Producto Base", "products");
		this.spa = new MenuButton("Producto Spa", "spa");
    	
    	// ACTIONS LISTENERS
    	this.restaurante.addActionListener(restaurante.apply(this));
    	this.normal.addActionListener(normal.apply(this));
		this.spa.addActionListener(spa.apply(this));
    	addButton(this.restaurante);  
    	addButton(this.normal);
		addButton(this.spa);
    }
}
	  
	

