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
		this.add(this.title);
	}

	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void configDataUser(String user) {
		// LOGO
		this.userIcon = new JLabel();
		this.userIcon.setIcon(
			ImagesManager.resizeIcon(ImagesManager.ImageIcon("icon-login"), 60, 60)
		);
		this.userIcon.setBackground(Color.WHITE);

		// NAME USER
		this.user = new JLabel(user);
		this.user.setOpaque(true);
		this.user.setBackground(Color.WHITE);
		this.user.setForeground(Color.BLACK);
		this.user.setFont(new Font(getName(), Font.BOLD, 25));

		// PANEL DATA USER
		this.userDataPanel = new JPanel(new FlowLayout(0, 10, 0));
		this.userDataPanel.setBackground(Color.WHITE);
		this.userDataPanel.add(this.userIcon);
		this.userDataPanel.add(this.user);
		this.userDataPanel.setAlignmentX(LEFT_ALIGNMENT);
	}

	private void configTitle(String title) {
		this.title = new JLabel(title);
		this.title.setOpaque(true);
		this.title.setBackground(Color.WHITE);
		this.title.setForeground(Color.BLACK);
		this.title.setFont(new Font(getName(), Font.BOLD, 20));
		this.title.setAlignmentX(LEFT_ALIGNMENT);
	}
}
