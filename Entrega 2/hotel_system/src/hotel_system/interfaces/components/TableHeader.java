package hotel_system.interfaces.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.table.TableCellRenderer;

public class TableHeader implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JComponent component = new JLabel((String) value.toString());
        ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
       
        component.setOpaque(true);
        component.setBackground(Color.BLACK);
        component.setForeground(Color.white);
        component.setFont(new Font("Arial", Font.BOLD, 13));
        component.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE), BorderFactory.createEmptyBorder(5,5,5,5)));
        component.setMaximumSize(new Dimension(1000, 50));

        return component;
    }
}