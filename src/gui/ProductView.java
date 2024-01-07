/**
 * 
 */
package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import control.OrderController;
import control.ProductController;
import dataaccesslayer.DataAccessException;
import model.Employee;
import model.Order;
import model.OrderLine;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
@SuppressWarnings("serial")
public class ProductView extends JFrame {
	private JTextField textField;
	private JTable tableProducts;
	private ProductTableModel productsTableModel;
	private ProductController productController;
	private OrderController orderController;
	private JTable tableOrderLines;
	private ArrayList<OrderLine> orderLinesToTable;
	private Order order;
	private Employee employee;

	/**
	 * @throws DataAccessException
	 * 
	 */

	public ProductView(LocalDateTime creationDate, LocalDateTime desiredDateofCustomer, Employee employee)
			throws DataAccessException {
		this.employee = employee;
		this.setVisible(false);
		productController = new ProductController();
		orderController = new OrderController();
		order = orderController.createOrderNoOrderIDOnlyDate(creationDate, false, desiredDateofCustomer, false, null,
				null);
		this.orderLinesToTable = new ArrayList<>();
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

		tableProducts.setRowHeight(25);

		productsTableModel = new ProductTableModel();
		productsTableModel.setData(productController.findAllProductFromDB());
		tableProducts.setModel(productsTableModel);
		TableColumn column1 = tableProducts.getColumnModel().getColumn(0);
		column1.setPreferredWidth(145);

		TableColumn column2 = tableProducts.getColumnModel().getColumn(1);
		column2.setPreferredWidth(30);
		scrollPane.setViewportView(tableProducts);

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

		JScrollPane scrollPane_OrderLines = new JScrollPane();
		scrollPane_OrderLines.setBounds(267, 28, 118, 207);
		panel.add(scrollPane_OrderLines);

		tableOrderLines = new JTable();
		scrollPane_OrderLines.setViewportView(tableOrderLines);

		textField = new JTextField();
		textField.setBounds(296, 236, 89, 14);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnAddCustomer = new JButton("Tilføj kunde");
		btnAddCustomer.setBounds(385, 28, 89, 23);
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCustomerClicked(order);
			}
		});
		panel.add(btnAddCustomer);

		JButton btnConfirmOrderCreation = new JButton("Godkend ");
		btnConfirmOrderCreation.setBounds(385, 220, 89, 30);
		btnConfirmOrderCreation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					confirmCreateOrder();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		panel.add(btnConfirmOrderCreation);

		JButton btnNewButton_3 = new JButton("Tilbage");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});
		btnNewButton_3.setBounds(385, 51, 89, 23);
		panel.add(btnNewButton_3);

		JLabel lblNewLabel_1 = new JLabel("Ordrelinjer");
		lblNewLabel_1.setBounds(267, 11, 118, 14);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Pris:");
		lblNewLabel_2.setBounds(267, 236, 46, 14);
		panel.add(lblNewLabel_2);
		JLabel lblNewLabel_Quantity = new JLabel("Antal");
		lblNewLabel_Quantity.setBounds(145, 11, 46, 14);
		panel.add(lblNewLabel_Quantity);

		init();
	}

	/**
	 * @throws SQLException
	 */
	protected void confirmCreateOrder() throws SQLException {
		/// Read orderLines, add to order.
		if (order.getCustomer() != null) {
			order.addOrderLines(orderLinesToTable);
			order.setEmployee(this.employee);
			if (checkOrderBeforeConfirmation(order)) {
				int orderId = orderController.insertOrderFromGui(order);
				order.setOrderId(orderId);
			}
			// insert orderlines into orderline (DB)
			ArrayList<Integer> orderLineIds = insertOrderLinesWithOrderId(order);
			// insert into OrderOrderLines
			insertIntoOrderOrderLine(orderLineIds, order.getOrderId());
			OrderView orderView = new OrderView(this.employee);
			closeWindow();
			orderView.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "Fejl: Mangler kunde.");
		}

	}

	/**
	 * 
	 */
	private void closeWindow() {
		this.setVisible(false);

	}

	/**
	 * @param i
	 * @param orderLineIds
	 * @throws SQLException
	 * 
	 */
	private void insertIntoOrderOrderLine(ArrayList<Integer> orderLineIds, int orderId) throws SQLException {
		orderController.insertOrderIDandOrderLinesIDsIntoDB(orderLineIds, orderId);

	}

	/**
	 * @param order2
	 * @throws SQLException
	 */
	private ArrayList<Integer> insertOrderLinesWithOrderId(Order order) throws SQLException {
		return orderController.addOrderLinesToDB(order);

	}

	/**
	 * 
	 */
	private boolean checkOrderBeforeConfirmation(Order order) {
		boolean ready = false;
		if (order.getCustomer() != null && order.getEmployee() != null) {
			ready = true;
		}
		return ready;

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
		if (calculateTotalProducts(intGetQuantityFromTabel)) {
			orderLinesToTable = orderController.createOrderLinesFromView(intGetQuantityFromTabel,
					productController.findAllProductFromDB());
			ProductOrderLinesTableModel productOrderLinesTableModel = new ProductOrderLinesTableModel();
			productOrderLinesTableModel.setData(orderLinesToTable);
			tableOrderLines.setModel(productsTableModel);
		}
	}

	/**
	 * 
	 */
	private int[] getQuantityFromTable() {
		int[] intGetQuantityFromTabel = new int[productsTableModel.getList().size()];
		for (int i = 0; i < productsTableModel.getList().size(); i++) {
			int j = (int) tableProducts.getModel().getValueAt(i, 1);
			intGetQuantityFromTabel[i] = j;
		}
//		for (int value : intGetQuantityFromTabel) {
//		    System.out.println(value);
//		}
		return intGetQuantityFromTabel;
	}

	public void addCustomerClicked(Order o) {
		FindCustomerView findCustomerView = new FindCustomerView(o);
		findCustomerView.setVisible(true);
	}

	public void cancelClicked() {
		OrderView orderView = new OrderView(employee);
		orderView.setVisible(true);
		clearWindow();
	}

	public void clearWindow() {
		this.setVisible(false);
		this.dispose();
	}

	public boolean calculateTotalProducts(int[] quantities) {
		boolean totalProductsMinimum20 = false;
		int check = 0;
		for (Integer ints : quantities) {
			check += ints;
		}
		if (check >= 20) {
			totalProductsMinimum20 = true;
		} else {
			JOptionPane.showMessageDialog(null, "Ordren har mindre end 20 produkter");
		}

		return totalProductsMinimum20;
	}
}
