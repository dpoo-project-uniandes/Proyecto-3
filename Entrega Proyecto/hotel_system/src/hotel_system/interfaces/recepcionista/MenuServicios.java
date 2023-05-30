package hotel_system.interfaces.recepcionista;

import java.awt.event.ActionListener;
import java.util.function.Function;

import hotel_system.interfaces.MainMenu;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.MenuButton;

public class MenuServicios extends MainMenu {

    private MenuButton restauranteBtn;
    private MenuButton spaBtn;
    private MenuButton alojamientoBtn;

    public MenuServicios(
    		String user,
    		HeaderButtonsActions headerButtonsActions,
    		Function<MenuServicios, ActionListener> restauranteAction,
    		Function<MenuServicios, ActionListener> spaAction,
    		Function<MenuServicios, ActionListener> alojamientoAction
	) {
    	super(user, "Menu Principal", 3, headerButtonsActions);
        configMenu(restauranteAction, spaAction, alojamientoAction);
    }
    
    private void configMenu(
    		Function<MenuServicios, ActionListener> restauranteAction,
    		Function<MenuServicios, ActionListener> spaAction,
    		Function<MenuServicios, ActionListener> alojamientoAction
	) {    	
    	// BUTTONS
    	this.restauranteBtn = new MenuButton("Restaurante", "rest");
    	this.spaBtn = new MenuButton("Spa", "spa");
    	this.alojamientoBtn = new MenuButton("Alojamiento", "room");
    	
    	// ACTIONS LISTENERS
    	this.restauranteBtn.addActionListener(restauranteAction.apply(this));
    	this.spaBtn.addActionListener(spaAction.apply(this));
    	this.alojamientoBtn.addActionListener(alojamientoAction.apply(this));
    	
    	addButton(this.restauranteBtn);
    	addButton(this.spaBtn);
    	addButton(this.alojamientoBtn);
    }
}