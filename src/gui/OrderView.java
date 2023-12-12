/**
 * 
 */
package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class OrderView extends JFrame {
	private JButton btnCreateOrder;
	private JButton btnViewOrders;
	private JButton btnLogOut;
	private JButton btnConfirmDenyOrders;
	private JDatePanelImpl datePanel;
	private Component datePicker;

	/**
	 * Creation of frame
	 */
	public OrderView() {
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
		
	}

	public void run(OrderView orderView) {
		orderView.setVisible(true);
	}

	public void btnCreateOrderClicked(ActionEvent e) {
		openDatePickerView();
	}

	/**
	 * 
	 */
	private void openDatePickerView() {
		DatePickerView dpv = new DatePickerView();
		dpv.setVisible(true);
	}

	public void enableProductView() {
		ProductView productView = new ProductView();
		productView.setVisible(true);
	}
}
