/**
 * 
 */
package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import control.OrderController;
import control.ProductController;
import dataaccesslayer.DataAccessException;
import model.OrderLine;

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
	private ProductTableModel  productsTableModel;
	private ProductController productController;
	private OrderController orderController;
	private JTable tableOrderLines;

	/**
	 * @throws DataAccessException
	 * 
	 */
	// TODO Indsæt date med i constructor
	public ProductView(Date creationDate, Date desiredDateofCustomer) throws DataAccessException {
		this.setVisible(false);
		productController = new ProductController();
		orderController = new OrderController();
		setTitle("Opret ordre forespørgsel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 175, 207);
		panel.add(scrollPane);

		tableProducts = new JTable();
		tableProducts.setCellSelectionEnabled(true);
		tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 // Adjust the width of the first column (index 0) to 115
		tableProducts.setRowHeight(25);
	

//		tableProducts.setDefaultRenderer(Object.class, new CellRenderer());
		productsTableModel = new ProductTableModel();
		productsTableModel.setData(productController.findAllProductFromDB());
		tableProducts.setModel(productsTableModel);
		TableColumn column1 = tableProducts.getColumnModel().getColumn(0);
        column1.setPreferredWidth(145);

        // Adjust the width of the second column (index 1) to 45
        TableColumn column2 = tableProducts.getColumnModel().getColumn(1);
        column2.setPreferredWidth(30);
		scrollPane.setViewportView(tableProducts);

//		JTextField[] quantityTextFields = new JTextField[productsTableModel.getRowCount()];
//		for (int i = 0; i < quantityTextFields.length; i++) {
//			quantityTextFields[i] = new JTextField();
//			quantityTextFields[i].setBounds(118, 52 + i * 21, 70, 20);
//			panel.add(quantityTextFields[i]);
//			quantityTextFields[i].setColumns(10);
//		}

		JLabel lblNewLabel = new JLabel("Produkter");
		lblNewLabel.setBounds(10, 11, 108, 14);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Tilføj linjer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addOrderLinesClicked();
				} catch (DataAccessException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(184, 125, 83, 23);
		panel.add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(267, 28, 118, 207);
		panel.add(scrollPane_1);
		
		tableOrderLines = new JTable();
		scrollPane_1.setViewportView(tableOrderLines);

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
//		textField_Product_1 = new JTextField();
//		textField_Product_1.setBounds(194, 233, 23, 20);
//		panel.add(textField_Product_1);
//		textField_Product_1.setColumns(10);
//		textField_Product_2 = new JTextField();
//		textField_Product_2.setBounds(186, 52, 23, 21);
//		panel.add(textField_Product_2);
//		textField_Product_2.setColumns(10);
//		textField_Product_3 = new JTextField();
//		textField_Product_3.setBounds(184, 70, 23, 20);
//		panel.add(textField_Product_3);
//		textField_Product_3.setColumns(10);
//		textField_Product_4 = new JTextField();
//		textField_Product_4.setBounds(186, 94, 23, 20);
//		panel.add(textField_Product_4);
//		textField_Product_4.setColumns(10);
//		textField_Product_5 = new JTextField();
//		textField_Product_5.setBounds(186, 183, 23, 20);
//		panel.add(textField_Product_5);
//		textField_Product_5.setColumns(10);
//		textField_Product_6 = new JTextField();
//		textField_Product_6.setBounds(194, 152, 23, 20);
//		panel.add(textField_Product_6);
//		textField_Product_6.setColumns(10);
//		textField_Product_7 = new JTextField();
//		textField_Product_7.setBounds(196, 215, 23, 20);
//		panel.add(textField_Product_7);
//		textField_Product_7.setColumns(10);
		JLabel lblNewLabel_Quantity = new JLabel("Antal");
		lblNewLabel_Quantity.setBounds(145, 11, 46, 14);
		panel.add(lblNewLabel_Quantity);

		init();
	}
	private void init() {
		this.setVisible(true);
	}
	/**
	 * @throws DataAccessException 
	 * 
	 */
	protected void addOrderLinesClicked() throws DataAccessException {
		int[] intGetQuantityFromTabel = getQuantityFromTable();
		ArrayList<OrderLine> orderLinesToTable = orderController.createOrderLinesFromView(intGetQuantityFromTabel, productController.findAllProductFromDB());
		ProductOrderLinesTableModel productOrderLinesTableModel = new ProductOrderLinesTableModel();
		productOrderLinesTableModel.setData(orderLinesToTable);
		tableOrderLines.setModel(productsTableModel);
	}
//	productsTableModel = new ProductTableModel();
//	productsTableModel.setData(productController.findAllProductFromDB());
//	tableProducts.setModel(productsTableModel);
//	TableColumn column1 = tableProducts.getColumnModel().getColumn(0);
//    column1.setPreferredWidth(145);
//
//    // Adjust the width of the second column (index 1) to 45
//    TableColumn column2 = tableProducts.getColumnModel().getColumn(1);
//    column2.setPreferredWidth(30);
//	scrollPane.setViewportView(tableProducts);

	/**
	 * 
	 */
	private int[] getQuantityFromTable() {
		int[] intGetQuantityFromTabel = new int[productsTableModel.getList().size()] ;
		System.out.println(productsTableModel.getList().size());
		for (int i = 0; i < productsTableModel.getList().size(); i++) {
			int j = (int) tableProducts.getModel().getValueAt(i, 1);
			intGetQuantityFromTabel[i] = j;
		}
		for (int value : intGetQuantityFromTabel) {
		    System.out.println(value);
		}
		return intGetQuantityFromTabel;
	}
	public void addCustomerClicked() {
		FindCustomerView findCustomerView = new FindCustomerView();
		findCustomerView.setVisible(true);
	}
}
