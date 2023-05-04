package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
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
		configButton();
  }
	
	public Button(String text, Dimension dimension) {
		super(text);
		this.text = text;
		this.radius = 20;
		this.dimension = dimension;
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
	}
		 
	protected void paintComponent(Graphics g) {
	    if (getModel().isArmed())
	      g.setColor(Color.GRAY);
	    else
	      g.setColor(getBackground());
	    
    	g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	    super.paintComponent(g);
    }
		 
	protected void paintBorder(Graphics g) {
		g.setColor(getBackground());
    	g.drawRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	}
		 
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}
}
