package hotel_system.interfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.function.Function;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import hotel_system.interfaces.components.Button;
import hotel_system.interfaces.components.Input;
import services.ImagesManager;

public class CreditCardPay extends JDialog {
	
	// COMPONENTS
	JLabel title;
	JPanel pasarelasPanel;
	JLabel pasarelasLabel;
	JPanel pasarelasButtonsPanel;
	ButtonGroup pasarelasButtonGroup;
	JPanel creditCardInfo;
	Input nameInput;
	Input numberInput;
	Input cvvInput;
	Input expirationDate;
	Button payButton;
	
	Window owner;
	Function<CreditCardPay, ActionListener> payAction;
	
	public CreditCardPay(
			Window owner,
			Map<String, String> pasarelas,
			Function<CreditCardPay, ActionListener> payAction
	) {
		this.payAction = payAction;
		this.owner = owner;
		configDialog();
		configTitle();
		configPasarelas(pasarelas);
		configInputs();
		configPayButton();
		configComponents();
	}
	
	private void configComponents() {
		this.add(title, UtilsGUI.getConstraints(0, 0, 5, 1, 1, 1, 30, 80, 30, 80, 1, GridBagConstraints.CENTER));
		this.add(pasarelasPanel, UtilsGUI.getConstraints(0, 1, 5, 1, 1, 1, 10, 80, 0, 80, 1, GridBagConstraints.CENTER));
		this.add(creditCardInfo, UtilsGUI.getConstraints(0, 2, 5, 1, 1, 1, 10, 80, 10, 80, 1, GridBagConstraints.CENTER));
		this.add(payButton, UtilsGUI.getConstraints(0, 3, 1, 1, 1, 1, 20, 200, 50, 200, 1, GridBagConstraints.CENTER));
		this.setVisible(true);
	}
	
	private void configDialog() {
		this.setSize(500, 580);
		this.setLocationRelativeTo(owner);
		this.setBackground(Color.WHITE);
		this.setLayout(new GridBagLayout());
		this.setTitle("Pago Virtual");
	}
	
	private void configTitle() {
		this.title = new JLabel("Pagos - Credit Card");
		this.title.setFont(new Font(getName(), Font.BOLD, 22));
		this.title.setHorizontalAlignment(SwingConstants.CENTER);
		this.title.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void configPasarelas(Map<String, String> pasarelas) {
		
		// RADIO BUTTONS
		this.pasarelasButtonGroup = new ButtonGroup();
		this.pasarelasButtonsPanel = new JPanel();
		this.pasarelasButtonsPanel.setOpaque(false);
		this.pasarelasButtonsPanel.setLayout(new BoxLayout(pasarelasButtonsPanel, BoxLayout.X_AXIS));
		this.pasarelasButtonsPanel.setAlignmentX(LEFT_ALIGNMENT);
		for (String key : pasarelas.keySet()) {
			ImageIcon icon = ImagesManager.resizeIcon(ImagesManager.ImageIcon(pasarelas.get(key)), 50, 50);
			JLabel iconLabel = new JLabel(icon);
			JRadioButton radioButton = new JRadioButton();
			iconLabel.setLabelFor(radioButton);
			this.pasarelasButtonsPanel.add(iconLabel);
			this.pasarelasButtonsPanel.add(radioButton);
			this.pasarelasButtonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		}
		
		// TITLE
		this.pasarelasLabel = new JLabel("Plataforma");
		this.pasarelasLabel.setFont(new Font(getName(), Font.BOLD, 16));
		this.pasarelasLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.pasarelasLabel.setAlignmentX(LEFT_ALIGNMENT);
		this.pasarelasLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		// PANEL
		this.pasarelasPanel = new JPanel();
		this.pasarelasPanel.setOpaque(false);
		this.pasarelasPanel.setLayout(new BoxLayout(pasarelasPanel, BoxLayout.Y_AXIS));
		
		this.pasarelasPanel.add(pasarelasLabel);
		this.pasarelasPanel.add(pasarelasButtonsPanel);
	}
	
	private void configInputs() {
		this.creditCardInfo = new JPanel();
		this.creditCardInfo.setOpaque(false);
		this.creditCardInfo.setLayout(new BoxLayout(creditCardInfo, BoxLayout.Y_AXIS));
		
		this.numberInput = Input.Instance("Numero", "number");
		this.nameInput = Input.Instance("Nombre", "text");
		this.expirationDate = Input.Instance("Fecha Vencimiento", "text");
		this.cvvInput = Input.Instance("CVV", "number");
		
		this.creditCardInfo.add(numberInput);
		this.creditCardInfo.add(nameInput);
		this.creditCardInfo.add(expirationDate);
		this.creditCardInfo.add(cvvInput);
	}
	
	private void configPayButton() {
		this.payButton = new Button("Pagar");
		this.payButton.addActionListener(payAction.apply(this));
	}

}
