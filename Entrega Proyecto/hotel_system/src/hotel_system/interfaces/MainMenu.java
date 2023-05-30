package hotel_system.interfaces;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.components.HeaderButtonsActions;
import hotel_system.interfaces.components.MenuButton;

public abstract class MainMenu extends JPanel {
	
	private MainHeader header;
    private JPanel buttonsPanel;

    public MainMenu(String user, String title, Integer quantityButtons, HeaderButtonsActions headerButtonsActions) {
        configPanel();
        configHeader(user, title, headerButtonsActions);
        configButtonsPanel(quantityButtons);
        configComponents();
    }
    
    private void configComponents() {
    	this.add(this.header);
    	this.add(this.buttonsPanel);
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
    
    private void configButtonsPanel(Integer quantityButtons) {
    	// BUTTONS PANEL
    	Integer rows = quantityButtons / 3;
    	this.buttonsPanel = new JPanel(new GridLayout(rows, 3, 100, 0));
    	this.buttonsPanel.setOpaque(false);
    	this.buttonsPanel.setBorder(new EmptyBorder(100,100,100,100));
	}
    
    protected void addButton(MenuButton btn) {
    	this.buttonsPanel.add(btn);
    	this.revalidate();
    }
}
