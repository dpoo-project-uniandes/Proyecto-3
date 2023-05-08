package hotel_system.interfaces.recepcionista;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.DataPanel;
import hotel_system.interfaces.Finder;
import hotel_system.interfaces.MainHeader;
import hotel_system.interfaces.Utils;
import hotel_system.interfaces.VerticalButtons;
import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.Input;
import hotel_system.interfaces.components.Tabla;



//TODO 3. Conectar los botones de esta interfaz con el PMS 

public class MenuProductosServicios extends JPanel {
	

	private MainHeader header;
	private Finder finder;
	private DataPanel dataPanel;
	private Button payLaterBtn;
	private Button payNowBtn;
	private VerticalButtons verticalButtons;
	private JPanel finderAndButtonsPanel;
	private String title;
	
	public MenuProductosServicios(
		    String user,
		    Function<Finder, ActionListener> generateAction,
		    Function<Finder, ActionListener> payNowAction,
		    Function<Finder, ActionListener> payLaterAction,
		    String filePath
		) {
		    this.title = "Detalles de los productos/servicios";
		    configPanel();
		    configHeader(user, "Factura");
		    configFinder("Numero de habitacion", generateAction);
		    configPayNowButton(payNowAction);
		    configPayLaterButton(payLaterAction);
		    configVerticalButtons();
		    configPanelFinderAndButtons();
		    configDataPanel(title,filePath);
		    injectData(filePath);
		    configComponents();
		}

	
	private void configComponents() {
		this.add(header);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.add(finderAndButtonsPanel);
		this.add(Box.createRigidArea(new Dimension(0, 50)));
		this.add(dataPanel);
	}
	
	private void configPanel() {
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(50, 50, 50, 50));
	}
	
	private void configHeader(String user, String title) {
		this.header = new MainHeader(user, title);
	}
	
	private void configFinder(
		String text,
		Function<Finder, ActionListener> generateAction
	) {
		this.finder = new Finder(text, generateAction);
		this.finder.setButtonText("Generar");
	}
	
	private void configPayNowButton(Function<Finder, ActionListener> payNowAction) {
		this.payNowBtn = new Button("Pagar ahora");
		this.payNowBtn.addActionListener(payNowAction.apply(finder));
	}
	
	private void configPayLaterButton(Function<Finder, ActionListener> payLaterAction) {
		this.payLaterBtn = new Button("Pagar despues");
		this.payLaterBtn.addActionListener(payLaterAction.apply(finder));
	}
	
	private void configVerticalButtons() {
		this.verticalButtons = new VerticalButtons(2);
		this.verticalButtons.addButton(payNowBtn);
		this.verticalButtons.addButton(payLaterBtn);
	}
	
	private void configPanelFinderAndButtons() {
		this.finderAndButtonsPanel = new JPanel();
		this.finderAndButtonsPanel.setLayout(new GridBagLayout());
		this.finderAndButtonsPanel.setOpaque(false);
		this.finderAndButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		this.finderAndButtonsPanel.add(this.finder, Utils.getConstraints(0, 0, 1, 1, 0.2, 1, 0, 0, 0, 50, 1, GridBagConstraints.WEST));
		this.finderAndButtonsPanel.add(this.verticalButtons, Utils.getConstraints(1, 0, 1, 1, 0.1, 1, 20, 600, 0, 0, 1, GridBagConstraints.EAST));
		this.finderAndButtonsPanel.setMaximumSize(new Dimension(5000, 80));
	}

	private void configDataPanel(String title, String filePath) {
	    this.dataPanel = new DataPanel(title);
	    this.dataPanel.setAlignmentX(LEFT_ALIGNMENT);
	    injectData(filePath);
	}



	public void withoutResults(String filePath) {
		configDataPanel(title,filePath);
	}

	public void injectData(String filePath) {
	    List<String> headers = new ArrayList<>();
	    List<List<String>> data = readCsvData(filePath);
	    if (data.size() > 0) {
	        headers = data.get(0);
	        data.remove(0);
	    }
	    Tabla tabla = new Tabla(headers, data);
	    dataPanel.injectDataPanel(tabla);
	}


	private List<List<String>> readCsvData(String filePath) {
	    List<List<String>> data = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            data.add(Arrays.asList(line.split(",")));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return data;
	}	
}