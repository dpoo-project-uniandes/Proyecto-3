package hotel_system.interfaces.recepcionista;

import java.awt.event.ActionListener;
import java.util.function.Function;

import hotel_system.interfaces.MainMenu;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.MenuButton;

public class MenuConsumible extends MainMenu{
	
	 private MenuButton ProductosBtn;
	 private MenuButton ServiciosBtn;
	 
	 public MenuConsumible(
			 String user,
			 HeaderButtonsActions headerButtonsActions,
			 Function<MenuConsumible, ActionListener> ProductosAction,
		     Function<MenuConsumible, ActionListener> ServiciosAction
			 ) {
		     super(user, "Menú Consumibles: ", 3, headerButtonsActions);
	         configMenu(ProductosAction, ServiciosAction);
	 }
	 private void configMenu(
	    		Function<MenuConsumible, ActionListener> ProductosAction,
		    	Function<MenuConsumible, ActionListener> ServiciosAction
		) {    	
	    	// BUTTONS
	    	this.ProductosBtn = new MenuButton("Productos", "products");
	    	this.ServiciosBtn = new MenuButton("Servicios", "services");
	    	
	    	// ACTIONS LISTENERS
	    	this.ProductosBtn.addActionListener(ProductosAction.apply(this));
	    	this.ServiciosBtn.addActionListener(ServiciosAction.apply(this));
	    	
	    	addButton(this.ProductosBtn);
	    	addButton(this.ServiciosBtn);
	    }

}


