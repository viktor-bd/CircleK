/**
 * 
 */
package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class ProductView extends JFrame {
	private JTextField textField;

	/**
	 * 
	 */
	// TODO Indsæt date med i constructor
	public ProductView() {
		setTitle("Opret ordre forespørgsel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 108, 207);
		panel.add(scrollPane);

		JLabel lblNewLabel = new JLabel("Produkter");
		lblNewLabel.setBounds(10, 11, 108, 14);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Tilføj linjer");
		btnNewButton.setBounds(117, 123, 96, 23);
		panel.add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(213, 28, 118, 207);
		panel.add(scrollPane_1);

		textField = new JTextField();
		textField.setBounds(242, 236, 89, 14);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnAddCustomer = new JButton("Tilføj kunde");
		btnAddCustomer.setBounds(335, 28, 89, 23);
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCustomerClicked();
			}
		});
		panel.add(btnAddCustomer);
	

	JButton btnNewButton_2 = new JButton("Godkend ");btnNewButton_2.setBounds(335,220,89,30);panel.add(btnNewButton_2);

	JButton btnNewButton_3 = new JButton("Tilbage");btnNewButton_3.setBounds(335,51,89,23);panel.add(btnNewButton_3);

	JLabel lblNewLabel_1 = new JLabel("Ordrelinjer");lblNewLabel_1.setBounds(213,11,118,14);panel.add(lblNewLabel_1);

	JLabel lblNewLabel_2 = new JLabel("Pris:");lblNewLabel_2.setBounds(213,236,46,14);panel.add(lblNewLabel_2);

	}

	public void addCustomerClicked() {
		FindCustomerView findCustomerView = new FindCustomerView();
		findCustomerView.setVisible(true);
	}
}
