package hotel_system.interfaces.admin;

import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.JPanel;

import hotel_system.interfaces.MainMenu;
import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.MenuButton;

public class MenuAdministrador extends MainMenu {
	private MenuButton loadBtn;
    private MenuButton modifyBtn;
    // private MenuButton searchBtn;

    public MenuAdministrador(
    		String user,
    		HeaderButtonsActions headerButtonsActions,
    		Function<MenuAdministrador, ActionListener> loadAction,
    		Function<MenuAdministrador, ActionListener> modifyAction,
    		Function<MenuAdministrador, ActionListener> searchAction
	) {
    	super(user, "Menu Principal", 2, headerButtonsActions);

        configMenu(loadAction, modifyAction, searchAction);
    }
    
    private void configMenu(
    		Function<MenuAdministrador, ActionListener> loadAction,
    		Function<MenuAdministrador, ActionListener> modifyAction,
    		Function<MenuAdministrador, ActionListener> searchAction
	) {    	
    	// BUTTONS
    	this.loadBtn = new MenuButton("Cargar", "loading");
    	this.modifyBtn = new MenuButton("Modificar", "modifying");
    	// this.searchBtn = new MenuButton("Consultar", "searching");
    	
    	// ACTIONS LISTENERS
    	this.loadBtn.addActionListener(loadAction.apply(this));
    	this.modifyBtn.addActionListener(modifyAction.apply(this));
    	// this.searchBtn.addActionListener(searchAction.apply(this));
    	addButton(this.loadBtn);  
    	addButton(this.modifyBtn);
    	// addButton(this.searchBtn);
    }
}
	  
	

