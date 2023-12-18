/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import control.OrderController;
import dataaccesslayer.DataAccessException;
import model.Employee;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class EmployeeLoginView extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEmployeeNumber;
	private Employee employee;
	private OrderController orderController;

	/**
	 * Launches the application by starting with an employee login.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeLoginView frame = new EmployeeLoginView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 * @throws DataAccessException 
	 */
	public EmployeeLoginView() throws DataAccessException {
		this.orderController = new OrderController();
		this.setTitle("Medarbejder login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panelButton = new JPanel();
		contentPane.add(panelButton, BorderLayout.SOUTH);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginClicked();
			}
		});
		panelButton.add(btnLogin);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 166, 96, 0 };
		gbl_panel.rowHeights = new int[] { 20, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblEmployeeNumber = new JLabel("Medarbejdernummer");
		GridBagConstraints gbc_lblEmployeeNumber = new GridBagConstraints();
		gbc_lblEmployeeNumber.insets = new Insets(0, 0, 5, 0);
		gbc_lblEmployeeNumber.gridx = 1;
		gbc_lblEmployeeNumber.gridy = 2;
		panel.add(lblEmployeeNumber, gbc_lblEmployeeNumber);

		textFieldEmployeeNumber = new JTextField();
		GridBagConstraints gbc_textFieldEmployeeNumber = new GridBagConstraints();
		gbc_textFieldEmployeeNumber.anchor = GridBagConstraints.NORTHWEST;
		gbc_textFieldEmployeeNumber.gridx = 1;
		gbc_textFieldEmployeeNumber.gridy = 3;
		panel.add(textFieldEmployeeNumber, gbc_textFieldEmployeeNumber);
		textFieldEmployeeNumber.setColumns(10);
	}

	/**
	 * Checks whether or not the employeeLogin textfield is filled correctly If
	 * filled correctly, OrderView is called If not, exception thrown to user
	 */
	private void loginClicked() {
		int employeeId = getEmployeeIdFromLogIn();
		try {
			employee = findEmployee(employeeId);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (employee != null) {
			OrderView orderView = new OrderView(employee);
			orderView.openWindow();
			closeWindow();
		} else {
			JOptionPane.showMessageDialog(this, "Ugyldigt medarbejdernummer", "Fejl", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * 
	 */
	private int getEmployeeIdFromLogIn() {
		int employeeId = -1;
		String employeeNumberText = textFieldEmployeeNumber.getText();
		try {
			employeeId = Integer.parseInt(employeeNumberText);
		} catch (NumberFormatException nfe) {
			// TODO JOptionError
		}
		return employeeId;

	}

	/**
	 * @return
	 * @throws SQLException 
	 */
	private Employee findEmployee(int id) throws SQLException {
		return orderController.getEmployeeFromEmployeeId(id);

	}

	/*
	 * Closes current window
	 */
	private void closeWindow() {
		this.setVisible(false);
		this.dispose();
	}

	/**
	 * Resets employee to null
	 */
	public void resetEmployee() {
		this.employee = null;
	}
}