package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComboBox;

public class RoundJComboBox extends JComboBox<String> {

	private Shape shape;

    public RoundJComboBox() {
        super(new String[]{"1", "2"});
        this.setOpaque(false);
        this.setBackground(Color.BLUE);
    }

    public RoundJComboBox(Dimension dimension) {
        super();
        this.setOpaque(false);
        this.setBackground(Color.BLUE);
        this.setSize(dimension);
    }

    @Override
	protected void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(4, 0, getWidth()-9, getHeight()-4, 15, 15);
         super.paintComponent(g);
    }

    @Override
	protected void paintBorder(Graphics g) {
    	 ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g.setColor(getForeground());
         ((Graphics2D)g).drawRoundRect(4, 0, getWidth()-9, getHeight()-4, 15, 15);
    }

    @Override
	public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}
