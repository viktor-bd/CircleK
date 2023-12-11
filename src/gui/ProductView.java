/**
 * 
 */
package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class ProductView extends JFrame {

	private JButton btnCreateOrder;
	private JButton btnViewOrders;
	private JButton btnConfirmDenyOrders;
	private JButton btnLogOut;

	/**
	 * 
	 */
	public ProductView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Startskærm");
		setBounds(100, 100, 500, 300);
		JPanel panel = new JPanel();
		panel.setBounds(172, 5, 89, 23);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnCreateOrder = new JButton("Opret ordre");
		btnCreateOrder.setBounds(172, 52, 89, 23);
		getContentPane().add(btnCreateOrder);
		

		btnViewOrders = new JButton("Se ordrer");
		btnViewOrders.setBounds(172, 86, 89, 23);
		getContentPane().add(btnViewOrders);

		btnConfirmDenyOrders = new JButton("Godkend/Afvis ordre");
		btnConfirmDenyOrders.setBounds(172, 120, 89, 23);
		getContentPane().add(btnConfirmDenyOrders);
		btnLogOut = new JButton("Log ud(?)");
		btnLogOut.setBounds(172, 154, 89, 23);
		getContentPane().add(btnLogOut);
	}

}
