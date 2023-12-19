/**
 * 
 */
package gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dataaccesslayer.DataAccessException;
import model.Employee;

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
	private Employee employee;

	/**
	 * Creation of frame
	 */
	public OrderView(Employee emp) {
		this.employee = emp;
		System.out.println(employee.getFirstName() + " IN ORDERVIEW ");
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

		JLabel lbl_Employee = new JLabel(employee.getFirstName() + " " + employee.getEmployeeId());
		lbl_Employee.setBounds(0, 0, 46, 14);
		panel.add(lbl_Employee);

		lblEmployee = new JLabel("");
		lblEmployee.setBounds(0, 5, 145, 21);
		getContentPane().add(lblEmployee);
		btnCreateOrder.addActionListener(this::btnCreateOrderClicked);
		btnConfirmDenyOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					confirmDenyClicked();
				} catch (DataAccessException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnViewOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					confirmedOrdersViewClicked();
				} catch (DataAccessException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logOutClicked();
				} catch (DataAccessException e1) {
					e1.printStackTrace();
				}
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
		pickDesiredDate();
	}

	/**
	 * Opens a DatePickerView when createOrderClicked
	 */
	public void pickDesiredDate() {
		DatePickerView dpv = new DatePickerView(employee);
		dpv.setVisible(true);
		clearWindow();
	}

	/**
	 * Opens a ConfirmedOrdersView when confirmOrdersViewClicked
	 * 
	 * @throws DataAccessException
	 * @throws SQLException 
	 */
	private void confirmedOrdersViewClicked() throws DataAccessException, SQLException {
		ConfirmedOrdersView confirmedOrdersView = new ConfirmedOrdersView(employee);
		confirmedOrdersView.setVisible(true);
		clearWindow();
	}

	public void confirmDenyClicked() throws DataAccessException, SQLException {
		UnconfirmedOrderView unconfirmedView = new UnconfirmedOrderView(this);
		unconfirmedView.setVisible(true);
		clearWindow();
	}

	// Resets to loginscreen and clears the employee logged in
	public void logOutClicked() throws DataAccessException {
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

	/**
	 * 
	 */
	public void openWindow() {
		this.setVisible(true);

	}
}
