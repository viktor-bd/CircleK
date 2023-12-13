/**
 * 
 */
package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		btnConfirmDenyOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmDenyClicked();
			}
		});
		btnConfirmDenyOrders.setBounds(172, 120, 89, 23);
		getContentPane().add(btnConfirmDenyOrders);
		btnLogOut = new JButton("Log ud(?)");
		btnLogOut.setBounds(172, 154, 89, 23);
		getContentPane().add(btnLogOut);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logOutClicked();
			}
		});

	}

	/**
	 * Makes the OrderView visible
	 * 
	 * @param orderView
	 */
	public void run(OrderView orderView) {
		orderView.setVisible(true);
	}

	/*
	 * Opens the date picker to create order
	 */
	public void btnCreateOrderClicked(ActionEvent e) {
		openDatePickerView();
	}

	/**
	 * Opens a DatePickerView when createOrderClicked
	 */
	private void openDatePickerView() {
		DatePickerView dpv = new DatePickerView();
		dpv.setVisible(true);
		clearWindow();
	}

	public void confirmDenyClicked() {
		UnconfirmedOrderView unconfirmedView = new UnconfirmedOrderView();
		unconfirmedView.setVisible(true);
		clearWindow();
	}

	// Resets to loginscreen and clears the employee logged in
	public void logOutClicked() {
		EmployeeLoginView employeeLogin = new EmployeeLoginView();
		employeeLogin.resetEmployee();
		employeeLogin.setVisible(true);
		clearWindow();
	}

	/**
	 * Closes current window
	 */
	public void clearWindow() {
		this.setVisible(false);
		this.dispose();
	}

	/**
	 * Updates the display
	 */
	private void updateDisplay() {
		this.revalidate();
		this.repaint();
	}
}
