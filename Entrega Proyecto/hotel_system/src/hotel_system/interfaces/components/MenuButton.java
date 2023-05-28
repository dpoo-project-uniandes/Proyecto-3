package hotel_system.interfaces.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import services.ImagesManager;

public class MenuButton extends JButton {
	
	private String text;
	private ImageIcon icon;
	private Integer radius;
	private Dimension dimension;
	private Shape shape;
	
	public MenuButton(String text, String icon) {
		this.text = text;
		this.icon = ImagesManager.resizeIcon(ImagesManager.ImageIcon(icon), 90, 90);
		this.radius = 25;
		this.dimension = getPreferredSize();
		configButton();
	}
	
	public MenuButton(String text, String icon, Dimension dimension) {
		this.text = text;
		this.icon = ImagesManager.resizeIcon(ImagesManager.ImageIcon(icon), 90, 90);
		this.radius = 25;
		this.dimension = dimension;
		configButton();
	}
	
	private void configButton() {
		setOpaque(true);
		setFocusable(false);
		setContentAreaFilled(false);
	    setBackground(Color.WHITE);
	    setForeground(Color.BLACK);
	    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    setBorder(new EmptyBorder(0,0,0,0));
	    setFont(new Font(getName(), Font.BOLD, 30));
	    setPreferredSize(dimension);
	    setIcon(icon);
	    setText(text);
	    setVerticalTextPosition(SwingConstants.BOTTOM);
	    setHorizontalTextPosition(SwingConstants.CENTER);
	}

	@Override
	protected void paintComponent(Graphics g) {
	    if (getModel().isArmed())
	      g.setColor(Color.GRAY);
	    else
	      g.setColor(getBackground());

    	g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	    super.paintComponent(g);
    }

	@Override
	protected void paintBorder(Graphics g) {
  	    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  	    ((Graphics2D)g).setStroke(new BasicStroke(6));
		g.setColor(getForeground());
    	g.drawRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	}

	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}

}
