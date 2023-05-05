package hotel_system.interfaces;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MenuRecepcionista extends JPanel {

    private MainHeader header;

    public MenuRecepcionista(String user, String title) {
        configPanel();
        configHeader(user, title);
    }

    private void configPanel() {
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(50, 50, 50, 52));
    }

    private void configHeader(String user, String title) {
    	this.header = new MainHeader(user, title);
    	this.add(header);
    }
}