/**
 * 
 */
package gui;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.awt.FlowLayout;


/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class OrderView extends JFrame {
	private JButton btnCreateOrder;
	private JButton btnViewOrders;
	private JButton btnLogOut;
	private JButton btnConfirmDenyOrders;

	/**
	 * Creation of frame
	 */
	public OrderView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Startskærm");
		setBounds(100,100, 500, 300);
		JPanel panel = new JPanel();
		panel.setBounds(172, 5, 89, 23);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnCreateOrder = new JButton("Opret ordre");
		btnCreateOrder.setBounds(172, 52, 89, 23);
		getContentPane().add(btnCreateOrder);
		btnCreateOrder.addActionListener(this::btnCreateOrderClicked);
		
		btnViewOrders = new JButton("Se ordrer");
		btnViewOrders.setBounds(172, 86, 89, 23);
		getContentPane().add(btnViewOrders);
		
		btnConfirmDenyOrders = new JButton("Godkend/Afvis ordre");
		btnConfirmDenyOrders.setBounds(172, 120, 89, 23);
		getContentPane().add(btnConfirmDenyOrders);
		btnLogOut = new JButton("Log ud(?)");
		btnLogOut.setBounds(172, 154, 89, 23);
		getContentPane().add(btnLogOut);
		// TODO Auto-generated constructor stub
	}
	public void run(OrderView orderView) {
		orderView.setVisible(true);
	}
	public void btnCreateOrderClicked(ActionEvent e) {
		
	}
    

}

