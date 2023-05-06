package hotel_system.interfaces;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class Tabla extends JPanel {

    public Tabla() {
        super(new GridLayout(1, 0));

        JTable table = new JTable(new CSVTableModel());

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
    }

    class CSVTableModel extends AbstractTableModel {
        private List<String[]> data = new ArrayList<>();
        private String[] columnNames;

        public CSVTableModel() {
            loadData();
        }

        private void loadData() {
        	File archivo = new File("C:\\Users\\esteb\\OneDrive\\Documents\\GitHub\\Proyecto-2\\Entrega 2\\hotel_system\\data\\productos_spa.csv");
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String line;
                boolean header = true;
                while ((line = br.readLine()) != null) {
                    if (header) {
                        columnNames = line.split(",");
                        header = false;
                    } else {
                        data.add(line.split(","));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }
    }

    private static void createAndShowGUI() {

        JFrame frame = new JFrame("Detalles del Producto/Servicio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new Tabla();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}