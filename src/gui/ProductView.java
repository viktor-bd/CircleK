/**
 * 
 */
package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import control.ProductController;
import dataaccesslayer.DataAccessException;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class ProductView extends JFrame {
	private JTextField textField;
	private JTextField textField_Product_1;
	private JTextField textField_Product_2;
	private JTextField textField_Product_3;
	private JTextField textField_Product_4;
	private JTextField textField_Product_5;
	private JTextField textField_Product_6;
	private JTextField textField_Product_7;
	private JTable tableProducts;
	private ArrayList<JTextField> productTextFields;

	/**
	 * @throws DataAccessException
	 * 
	 */
	// TODO Indsæt date med i constructor
	public ProductView(Date creationDate, Date desiredDateofCustomer) throws DataAccessException {
		ProductController productController = new ProductController();
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

		tableProducts = new JTable();
		tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableProducts.setRowHeight(25);
		ProductTableModel productsTableModel = new ProductTableModel();
		productsTableModel.setData(productController.findAllProductFromDB());
		tableProducts.setModel(productsTableModel);
		scrollPane.setViewportView(tableProducts);

		JLabel lblNewLabel = new JLabel("Produkter");
		lblNewLabel.setBounds(10, 11, 108, 14);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Tilføj linjer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOrderLinesClicked();
			}
		});
		btnNewButton.setBounds(184, 125, 83, 23);
		panel.add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(267, 28, 118, 207);
		panel.add(scrollPane_1);

		textField = new JTextField();
		textField.setBounds(296, 236, 89, 14);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnAddCustomer = new JButton("Tilføj kunde");
		btnAddCustomer.setBounds(385, 28, 89, 23);
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCustomerClicked();
			}
		});
		panel.add(btnAddCustomer);

		JButton btnNewButton_2 = new JButton("Godkend ");
		btnNewButton_2.setBounds(385, 220, 89, 30);
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Tilbage");
		btnNewButton_3.setBounds(385, 51, 89, 23);
		panel.add(btnNewButton_3);

		JLabel lblNewLabel_1 = new JLabel("Ordrelinjer");
		lblNewLabel_1.setBounds(267, 11, 118, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Pris:");
		lblNewLabel_2.setBounds(267, 236, 46, 14);
		panel.add(lblNewLabel_2);
		textField_Product_1 = new JTextField();
		textField_Product_1.setBounds(118, 215, 23, 20);
		panel.add(textField_Product_1);
		textField_Product_1.setColumns(10);
		textField_Product_2 = new JTextField();
		textField_Product_2.setBounds(118, 52, 23, 21);
		panel.add(textField_Product_2);
		textField_Product_2.setColumns(10);
		textField_Product_3 = new JTextField();
		textField_Product_3.setBounds(118, 73, 23, 20);
		panel.add(textField_Product_3);
		textField_Product_3.setColumns(10);
		textField_Product_4 = new JTextField();
		textField_Product_4.setBounds(118, 95, 23, 20);
		panel.add(textField_Product_4);
		textField_Product_4.setColumns(10);
		textField_Product_5 = new JTextField();
		textField_Product_5.setBounds(118, 126, 23, 20);
		panel.add(textField_Product_5);
		textField_Product_5.setColumns(10);
		textField_Product_6 = new JTextField();
		textField_Product_6.setBounds(118, 155, 23, 20);
		panel.add(textField_Product_6);
		textField_Product_6.setColumns(10);
		textField_Product_7 = new JTextField();
		textField_Product_7.setBounds(118, 183, 23, 20);
		panel.add(textField_Product_7);
		textField_Product_7.setColumns(10);
		JLabel lblNewLabel_Quantity = new JLabel("Antal");
		lblNewLabel_Quantity.setBounds(95, 11, 46, 14);
		panel.add(lblNewLabel_Quantity);

	}

	/**
	 * 
	 */
	protected void addOrderLinesClicked() {
		// TODO Auto-generated method stub
		
	}

	public void addCustomerClicked() {
		FindCustomerView findCustomerView = new FindCustomerView();
		findCustomerView.setVisible(true);
	}
}
