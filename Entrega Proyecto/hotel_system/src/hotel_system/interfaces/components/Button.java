package hotel_system.interfaces.components;

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

public class Button extends JButton {

	private String text;
	private Integer radius;
	private Dimension dimension;
	private Shape shape;

	public Button(String text) {
		super(text);
		this.text = text;
		this.radius = 20;
		this.dimension = getPreferredSize();
		configButton();
  }

	public Button(String text, Dimension dimension) {
		super(text);
		this.text = text;
		this.radius = 20;
		this.dimension = dimension;
		configButton();
	}
	
	public Button(String text, Dimension dimension, Integer radius) {
		super(text);
		this.text = text;
		this.radius = radius;
		this.dimension = dimension;
		configButton();
	}
	
	public Button(ImageIcon icon) {
		super(icon);
		this.text = null;
		this.radius = 20;
		this.dimension = getPreferredSize();
		configButton();
	}

	private void configButton() {
		setOpaque(true);
	    setBackground(Color.BLACK);
	    setFocusable(false);
	    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    setContentAreaFilled(false);
	    setBorder(new EmptyBorder(10,20,10,20));
	    setForeground(Color.WHITE);
	    setFont(new Font(getName(), Font.BOLD, getFont().getSize()));
	    setPreferredSize(dimension);
	    setMinimumSize(dimension);
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
		g.setColor(getBackground());
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
