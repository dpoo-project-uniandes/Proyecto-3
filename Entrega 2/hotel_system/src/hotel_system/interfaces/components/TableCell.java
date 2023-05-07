package hotel_system.interfaces.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCell extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		if(value instanceof Component) {
			((JComponent)value).setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));
			return (Component) value;
		}
		
		this.setText(value.toString());
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setFont(new Font("Arial", Font.PLAIN, 13));
        this.setForeground(Color.BLACK);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));

        return this;
	}

}
