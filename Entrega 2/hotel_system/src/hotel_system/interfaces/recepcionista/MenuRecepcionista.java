package hotel_system.interfaces.recepcionista;

import java.awt.event.ActionListener;
import java.util.function.Function;

import hotel_system.interfaces.MainMenu;
import hotel_system.interfaces.components.MenuButton;

public class MenuRecepcionista extends MainMenu {

    private MenuButton bookingBtn;
    private MenuButton staysBtn;
    private MenuButton consumiblesBtn;

    public MenuRecepcionista(
    		String user,
    		Function<MenuRecepcionista, ActionListener> bookingAction,
    		Function<MenuRecepcionista, ActionListener> staysAction,
    		Function<MenuRecepcionista, ActionListener> consumiblesAction
	) {
    	super(user, "Menu Principal", 3);
        configMenu(bookingAction, staysAction, consumiblesAction);
    }
    
    private void configMenu(
    		Function<MenuRecepcionista, ActionListener> bookingAction,
    		Function<MenuRecepcionista, ActionListener> staysAction,
    		Function<MenuRecepcionista, ActionListener> consumiblesAction
	) {    	
    	// BUTTONS
    	this.bookingBtn = new MenuButton("Reservas", "booking");
    	this.staysBtn = new MenuButton("Estadias", "accommodation");
    	this.consumiblesBtn = new MenuButton("Consumibles", "consumible");
    	
    	// ACTIONS LISTENERS
    	this.bookingBtn.addActionListener(bookingAction.apply(this));
    	this.staysBtn.addActionListener(staysAction.apply(this));
    	this.consumiblesBtn.addActionListener(consumiblesAction.apply(this));
    	
    	addButton(this.bookingBtn);
    	addButton(this.staysBtn);
    	addButton(this.consumiblesBtn);
    }
}