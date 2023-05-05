package hotel_system.interfaces;

import hotel_system.interfaces.components.MenuButton;

public class MenuRecepcionista extends MainMenu {

    private MenuButton bookingBtn;
    private MenuButton staysBtn;
    private MenuButton consumiblesBtn;

    public MenuRecepcionista(String user) {
    	super(user, "Menu Principal", 3);
        configMenu();
    }
    
    private void configMenu() {    	
    	// BUTTONS
    	this.bookingBtn = new MenuButton("Reservas", "booking");
    	this.staysBtn = new MenuButton("Estadias", "accommodation");
    	this.consumiblesBtn = new MenuButton("Consumibles", "consumible");
    	
    	addButton(this.bookingBtn);
    	addButton(this.staysBtn);
    	addButton(this.consumiblesBtn);
    }
}