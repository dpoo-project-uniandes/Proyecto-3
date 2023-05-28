package hotel_system.interfaces.components;

import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.JTextField;

public class RoundJTextField extends JTextField {

	private Shape shape;
	private RoundInputField roundInput;

    public RoundJTextField() {
        super();
        this.roundInput = new RoundInputField(this);
    }

    @Override
	protected void paintComponent(Graphics g) {
         roundInput.paintComponent(g);
         super.paintComponent(g);
    }

    @Override
	protected void paintBorder(Graphics g) {
         roundInput.paintBorder(g);
    }

    @Override
	public boolean contains(int x, int y) {
         return roundInput.contains(x, y);
    }
}