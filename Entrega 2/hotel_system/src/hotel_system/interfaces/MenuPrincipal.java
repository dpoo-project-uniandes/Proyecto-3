
package hotel_system.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.components.Button;

	public class MenuPrincipal extends JPanel implements ActionListener{

	    private BufferedImage iconoR;
	    private Button BotonReservas;
	    private Button BotonEstadias;
	    private Button BotonConsumibles;
	    private JLabel title;
	    private JPanel PanelOpciones;

	    public 	MenuPrincipal()  {
                configPanel();
                configTitle();
	            configOpciones();
	            configComponents();
	         }

	    private void configComponents() {
	    		this.add(title);
	    		this.add(PanelOpciones);

	    }

	    private void configPanel() {
	        this.setOpaque(true);
	        this.setBackground(Color.WHITE);
	        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setBorder(new EmptyBorder(130, 10, 10, 12));

	    }

	    private void configTitle() {
	    	title = new JLabel("Menú Principal:");
	    	title.setBackground(Color.WHITE);
	    	title.setForeground(Color.BLACK);
	    	title.setOpaque(true);
	    	title.setFont(new Font(getName(), Font.BOLD, 15));
	    	title.setHorizontalAlignment(SwingConstants.LEFT);
	    	title.setAlignmentX(Component.RIGHT_ALIGNMENT);
	    }



	    private void configOpciones()  {
	    	 this.BotonReservas = new Button("Reservas",new Dimension(150,150));
	    	 this.BotonReservas.setBackground(Color.BLACK);
	    	 this.BotonReservas.setIcon(new ImageIcon("./assets/reservas.png"));
	    	 this.BotonEstadias = new Button("Estadias",new Dimension(150,150));
	    	 this.BotonEstadias.setIcon(new ImageIcon("./assets/estadias.png"));
	    	 this.BotonConsumibles = new Button("Consumibles",new Dimension(150,150));
	    	 this.BotonConsumibles.setIcon(new ImageIcon("./assets/consumibles.png"));

	    	 this.PanelOpciones = new JPanel(new FlowLayout(1, 30, 1));
	    	 this.PanelOpciones.setBackground(Color.WHITE);
	 		 this.PanelOpciones.add(this.BotonReservas,BorderLayout.CENTER);
	 		 this.PanelOpciones.add(this.BotonEstadias,BorderLayout.CENTER);
	 		 this.PanelOpciones.add(this.BotonConsumibles,BorderLayout.CENTER);

	    }



	    @Override
	    public void actionPerformed(ActionEvent e) {
	    }

}


