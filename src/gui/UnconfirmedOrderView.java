/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JScrollPane;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class UnconfirmedOrderView extends JFrame {

	/**
	 * 
	 */
	public UnconfirmedOrderView() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUnconfirmedOrders = new JLabel("Ordrer til godkendelse");
		lblUnconfirmedOrders.setBounds(0, 0, 331, 22);
		panel.add(lblUnconfirmedOrders);
		
		JButton btnBackToMenu = new JButton("Tilbage");
		btnBackToMenu.setBounds(331, 0, 103, 23);
		panel.add(btnBackToMenu);
		
		JButton btnNewButton_1 = new JButton("Godkend ordre");
		btnNewButton_1.setBounds(331, 238, 103, 23);
		panel.add(btnNewButton_1);
		
		JButton btnRejectOrder = new JButton("Afvis ordre");
		btnRejectOrder.setBounds(331, 214, 103, 23);
		panel.add(btnRejectOrder);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 21, 331, 240);
		panel.add(scrollPane);
		// TODO Auto-generated constructor stub
	}
}
