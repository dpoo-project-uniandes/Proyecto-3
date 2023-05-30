package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import hotel_system.interfaces.components.HeaderButtonsActions;
import services.ImagesManager;

public class MainHeader extends JPanel {

	private JPanel infoPanel;
	private JPanel userDataPanel;
	private JLabel userIcon;
	private JLabel user;
	private JLabel title;
	private JPanel headerButtonsPanel;
	private JButton backBtn;
	private JButton homeMenuBtn;
	
	private HeaderButtonsActions actions;

	public MainHeader(String user, String title, HeaderButtonsActions headerButtonsActions) {
		this.actions = headerButtonsActions;
		configPanel();
		configDataUser(user);
		configTitle(title);
		configHeaderButtons();
		configInfoPanel();
		configComponents();		
	}
	
	private void configComponents() {
		this.add(this.infoPanel);
		this.add(this.headerButtonsPanel);
	}

	private void configPanel() {
		this.setOpaque(false);
		this.setLayout(new GridLayout(1, 2));
		this.setMaximumSize(new Dimension(5000, 250));
	}
	
	private void configInfoPanel() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, BoxLayout.Y_AXIS));
		this.infoPanel.setOpaque(false);

		// COMPONENTS
		this.infoPanel.add(this.userDataPanel);
		this.infoPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		this.infoPanel.add(this.title);
	}

	private void configDataUser(String user) {
		// LOGO
		this.userIcon = new JLabel();
		this.userIcon.setIcon(ImagesManager.resizeIcon(ImagesManager.ImageIcon("icon-login"), 60, 60));
		MainHeader clazz = this;
		this.userIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				actions.getIconUserAction().apply(clazz);
			}
		});

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
	}
	
	private void configHeaderButtons() {
		// BACK BUTTON
		this.backBtn = headerButton("back");
		this.backBtn.addActionListener(actions.getBackAction().apply(this));
		
		// HOME BUTTON
		this.homeMenuBtn = headerButton("home");
		this.homeMenuBtn.addActionListener(actions.getHomeAction().apply(this));
		
		// PANEL
		this.headerButtonsPanel = new JPanel();
		this.headerButtonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));
		this.headerButtonsPanel.setOpaque(false);
		
		this.headerButtonsPanel.add(backBtn);
		this.headerButtonsPanel.add(homeMenuBtn);
	}
	
	private JButton headerButton(String image) {
		JButton btn = new JButton();
		JPanel btnPanel = new JPanel(new FlowLayout());
		btn.setOpaque(true);
	    btn.setFocusable(false);
	    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    btn.setContentAreaFilled(false);
	    btn.setForeground(Color.BLACK);
	    btn.setIcon(ImagesManager.resizeIcon(ImagesManager.ImageIcon(image), 45, 45));
	    btn.setBorder(BorderFactory.createEmptyBorder());
	    return btn;
	}
}
