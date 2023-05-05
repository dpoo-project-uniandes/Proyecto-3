
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

		public class MenuAdmin extends JPanel implements ActionListener{

		    private BufferedImage iconoR;
		    private Button BotonCargar;
		    private Button BotonModificar;
		    private Button BotonConsultar;
		    private JLabel title;
		    private JPanel PanelOpciones;

		    public 	MenuAdmin()  {
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
				this.setBorder(new EmptyBorder(130, 30, 10, 12));

		    }

		    private void configTitle() {
		    	title = new JLabel("Menú Administrador:");
		    	title.setBackground(Color.WHITE);
		    	title.setForeground(Color.BLACK);
		    	title.setOpaque(true);
		    	title.setFont(new Font(getName(), Font.BOLD, 16));
		    	title.setHorizontalAlignment(SwingConstants.CENTER);
		    	title.setAlignmentX(Component.RIGHT_ALIGNMENT);
		    }



		    private void configOpciones()  {
		    	 this.BotonCargar = new Button("Cargar",new Dimension(150,150));
		    	 this.BotonCargar.setBackground(Color.BLACK);
		    	 this.BotonCargar.setIcon(new ImageIcon("./assets/cargar.png"));
		    	 this.BotonModificar = new Button("Modificar",new Dimension(150,150));
		    	 this.BotonModificar.setIcon(new ImageIcon("./assets/modificar.png"));
		    	 this.BotonConsultar = new Button("Consultar",new Dimension(150,150));
		    	 this.BotonConsultar.setIcon(new ImageIcon("./assets/consultar.png"));

		    	 this.PanelOpciones = new JPanel(new FlowLayout(1, 30, 1));
		    	 this.PanelOpciones.setBackground(Color.WHITE);
		 		 this.PanelOpciones.add(this.BotonCargar,BorderLayout.CENTER);
		 		 this.PanelOpciones.add(this.BotonModificar,BorderLayout.CENTER);
		 		 this.PanelOpciones.add(this.BotonConsultar,BorderLayout.CENTER);

		    }



		    @Override
		    public void actionPerformed(ActionEvent e) {
		    }

	}




