package hotel_system.interfaces.admin;

import java.awt.event.ActionListener;
import java.util.function.Function;

import hotel_system.interfaces.MainMenu;
import hotel_system.interfaces.components.MenuButton;

public class MenuCargaAdministrador extends MainMenu {
	  private MenuButton RestauranteBtn;
	  private MenuButton SpaBtn;
	  private MenuButton HabitacionesBtn;

	   public MenuCargaAdministrador(
	    	String user,
	    	Function<MenuCargaAdministrador, ActionListener> RestauranteAction,
	    	Function<MenuCargaAdministrador, ActionListener> SpaAction,
	    	Function<MenuCargaAdministrador, ActionListener> HabitacionesAction
		) {
	    	super(user, "Seleccione el servicio que desea cargar: ", 3);
	        configMenu(RestauranteAction, SpaAction, HabitacionesAction);
	    }
	    
	    private void configMenu(
	    		Function<MenuCargaAdministrador, ActionListener> RestauranteAction,
		    	Function<MenuCargaAdministrador, ActionListener> SpaAction,
		    	Function<MenuCargaAdministrador, ActionListener> HabitacionesAction
		) {    	
	    	// BUTTONS
	    	this.RestauranteBtn = new MenuButton("Restaurante", "rest");
	    	this.SpaBtn = new MenuButton("Spa", "spa");
	    	this.HabitacionesBtn = new MenuButton("Habitaciones", "room");
	    	
	    	// ACTIONS LISTENERS
	    	this.RestauranteBtn.addActionListener(RestauranteAction.apply(this));
	    	this.SpaBtn.addActionListener(SpaAction.apply(this));
	    	this.HabitacionesBtn.addActionListener(HabitacionesAction.apply(this));
	    	
	    	addButton(this.RestauranteBtn);
	    	addButton(this.SpaBtn);
	    	addButton(this.HabitacionesBtn);
	    }

}
