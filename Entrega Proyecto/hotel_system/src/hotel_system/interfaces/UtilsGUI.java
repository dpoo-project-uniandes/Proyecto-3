package hotel_system.interfaces;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UtilsGUI {
    public static GridBagConstraints getConstraints(
    		int x, 
			int y, 
			int width, 
			int height, 
			double weightX, 
			double weightY, 
			int padTop, 
			int padLeft, 
			int padBottom,
			int padRigth, 
			int fill,
			int anchor
		) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.anchor = anchor;
        constraints.fill = fill;
        constraints.insets = new Insets(padTop, padLeft, padBottom, padRigth);

        return constraints;
    }
}
