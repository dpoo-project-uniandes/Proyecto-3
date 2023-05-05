package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import services.ImagesManager;

public class MainHeader extends JPanel {

	private JPanel userDataPanel;
	private JLabel userIcon;
	private JLabel user;
	private JLabel title;

	public MainHeader(String user, String title) {
		configPanel();
		configDataUser(user);
		configTitle(title);
		configComponents();
	}
	
	private void configComponents() {
		this.add(this.userDataPanel);
		this.add(Box.createRigidArea(new Dimension(0, 40)));
		this.add(this.title);
	}

	private void configPanel() {
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void configDataUser(String user) {
		// LOGO
		this.userIcon = new JLabel();
		this.userIcon.setIcon(ImagesManager.resizeIcon(ImagesManager.ImageIcon("icon-login"), 60, 60));

		// NAME USER
		this.user = new JLabel(user);
		this.user.setForeground(Color.BLACK);
		this.user.setFont(new Font(getName(), Font.BOLD, 25));

		// PANEL DATA USER
		this.userDataPanel = new JPanel();
		this.userDataPanel.setLayout(new BoxLayout(this.userDataPanel, BoxLayout.X_AXIS));
		this.userDataPanel.setOpaque(false);
		this.userDataPanel.add(this.userIcon);
		this.userDataPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		this.userDataPanel.add(this.user);
		this.userDataPanel.setAlignmentX(LEFT_ALIGNMENT);
	}

	private void configTitle(String title) {
		this.title = new JLabel(title);
		this.title.setForeground(Color.BLACK);
		this.title.setFont(new Font(getName(), Font.BOLD, 22));
		this.title.setAlignmentX(LEFT_ALIGNMENT);
	}
}
