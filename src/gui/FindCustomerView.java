/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;

import control.OrderController;

import dataaccesslayer.DataAccessException;
import model.Customer;
import model.Order;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 * @1.Semesterprojekt
 *
 */
@SuppressWarnings("serial")
public class FindCustomerView extends JFrame {

	private JTextField textFieldCustomerNumber;
	private Container contentPane;
	private Order order;

	public FindCustomerView(Order order) {
		this.order = order;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		((JComponent) contentPane).setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblCustomerNumber = new JLabel("Indtast kundens telefon nummer");
		lblCustomerNumber.setBounds(0, 86, 156, 14);
		panel.add(lblCustomerNumber);

		textFieldCustomerNumber = new JTextField();
		textFieldCustomerNumber.setBounds(165, 83, 86, 20);
		panel.add(textFieldCustomerNumber);
		textFieldCustomerNumber.setColumns(10);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton btnSearch = new JButton("Søg");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					searchClicked();
				} catch (DataAccessException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnNewButton = new JButton("Opret kunde");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createCustomerClicked();
			}

		});
		panel_1.add(btnNewButton);
		panel_1.add(btnSearch);

		JButton btnCancel = new JButton("Annuller");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});
		panel_1.add(btnCancel);
	}

	public void searchClicked() throws DataAccessException {
		Customer c = findCustomerByPhone();
		if (c != null) {
			confirmCustomer(c, order);
		} else {
			// TODO JOPTIONERROR MSG
		}

	}

	/**
	 * 
	 */
	private boolean confirmCustomer(Customer customer, Order order) {
		boolean customerAdded = false;
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
				"Vil du vælge " + customer.getFirstName() + "?", "Kunde", JOptionPane.YES_NO_OPTION)) {
			order.setCustomer(customer);
			customerAdded = true;
			this.setVisible(false);

		}
		return customerAdded;
	}

	public void cancelClicked() {
		clearWindow();
	}

	public void createCustomerClicked() {
		CreateCustomerView createCustomerView = new CreateCustomerView();
		createCustomerView.setVisible(true);
	}

	public Customer findCustomerByPhone() throws DataAccessException {
		OrderController orderController = new OrderController();
		String phone = textFieldCustomerNumber.getText();
		return orderController.findCustomerByPhone(phone);
	}

	public void clearWindow() {
		this.setVisible(false);
		this.dispose();
	}

}
