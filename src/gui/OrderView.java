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
	private JLabel lblEmployee;

	/**
	 * Creation of frame
	 */
	public OrderView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setTitle("Startskærm");
		setBounds(100, 100, 500, 300);
		JPanel panel = new JPanel();
		panel.setBounds(0, 5, 484, 256);
		getContentPane().add(panel);
		panel.setLayout(null);
		btnLogOut = new JButton("Log ud");
		btnLogOut.setBounds(186, 149, 89, 23);
		panel.add(btnLogOut);

		btnViewOrders = new JButton("Se ordrer");
		btnViewOrders.setBounds(186, 115, 89, 23);
		panel.add(btnViewOrders);

		btnConfirmDenyOrders = new JButton("Godkend/Afvis ordre");
		btnConfirmDenyOrders.setBounds(186, 81, 89, 23);
		panel.add(btnConfirmDenyOrders);

		btnCreateOrder = new JButton("Opret ordre");
		btnCreateOrder.setBounds(186, 47, 89, 23);
		panel.add(btnCreateOrder);

		lblEmployee = new JLabel("");
		lblEmployee.setBounds(0, 5, 145, 21);
		getContentPane().add(lblEmployee);
		btnCreateOrder.addActionListener(this::btnCreateOrderClicked);
		btnConfirmDenyOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmDenyClicked();
			}
		});
		btnViewOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmedOrdersViewClicked();
			}
		});
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

	/**
	 * Opens a ConfirmedOrdersView when confirmOrdersViewClicked
	 */
	private void confirmedOrdersViewClicked() {
		ConfirmedOrdersView confirmedOrdersView = new ConfirmedOrdersView();
		confirmedOrdersView.setVisible(true);
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
