package hotel_system.interfaces.components;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Tabla extends JPanel {
	
	private JTable table;
	private JScrollPane scrollPane;
	private List<String> headers;
	private List<List<Object>> data;

    public Tabla(List<String> headers, List<List<Object>> data) {
    	this.headers = headers;
    	this.data = data;
    	configPanel();
    	configTable();
    	configComponents();
    }
    
    private void configComponents() {
    	this.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void configPanel() {
    	this.setLayout(new BorderLayout());
    }
    
    private void configTable() {
    	// DATA
    	DefaultListModel<String> headers = new DefaultListModel<>();
    	DefaultListModel<List<Object>> data = new DefaultListModel<>();
    	
    	headers.addAll(this.headers);
    	data.addAll(this.data);
    	
    	// TABLE
        this.table = new JTable(new TableModel(this.headers, this.data));
        this.table.setFillsViewportHeight(true);
        
        // CUSTOMIZATION
        TableCell cellRenderer = new TableCell();
        for (int i = 0; i < this.headers.size(); i++) {
            this.table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
		}
        this.table.getTableHeader().setDefaultRenderer(new TableHeader());
        this.table.setRowHeight(35);
  
        this.scrollPane = new JScrollPane(this.table);
    }
    
    class TableModel extends AbstractTableModel {
    	
        private List<Object[]> data = new ArrayList<>();
        private String[] headers;
        
        public TableModel(List<String> headers, List<List<Object>> data) {
        	this.headers = headers.toArray(String[]::new);
        	this.data = data.stream().map(e -> e.toArray(Object[]::new)).toList();
        }
    	
        public int getColumnCount() {
            return headers.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return headers[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }
        
        public boolean isCellEditable(int row, int col) {
        	return false;
        }
    }
}