package hotel_system.interfaces.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class RoundInputField {
	
	private Shape shape;
	private JTextField field;
    
    public RoundInputField(JTextField field) {
        super();
        this.field = field;
        this.field.setOpaque(false);
    }
    
    protected void paintComponent(Graphics g) {
         g.setColor(field.getBackground());
         g.fillRoundRect(0, 0, field.getWidth()-1, field.getHeight()-1, 15, 15);
    }
    
    protected void paintBorder(Graphics g) {
   	     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g.setColor(field.getForeground());
         g.drawRoundRect(0, 0, field.getWidth()-1, field.getHeight()-1, 15, 15);
    }
    
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(field.getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, field.getWidth()-1, field.getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }
}
