/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import control.OrderController;
import dataaccesslayer.DataAccessException;
import dataaccesslayer.InvalidConcurrencyException;

import model.Order;
import model.Customer;
import model.Employee;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
@SuppressWarnings("serial")
public class UnconfirmedOrderView extends JFrame {
	/**
	 * 
	 */
	private JTable tableUnconfirmedOrders;
	private UnconfirmedOrderTableModel unconfirmedOrderTableModel;
	private OrderController orderController;
	private OrderView orderView;
	private ScheduledExecutorService exec;
	private volatile boolean viewRunning;

	/**
	 * Creates and sets the view
	 * 
	 * @throws DataAccessException
	 * @throws SQLException
	 */
	public UnconfirmedOrderView(OrderView orderView) throws DataAccessException, SQLException {
		viewRunning = true;
		this.orderView = orderView;
		orderController = new OrderController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bekræft ordrer");
		setBounds(100, 100, 500, 300);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblUnconfirmedOrders = new JLabel("Ordrer til godkendelse");
		lblUnconfirmedOrders.setBounds(0, 0, 331, 22);
		panel.add(lblUnconfirmedOrders);

		JButton btnBackToMenu = new JButton("Tilbage");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenuClicked();
			}
		});
		btnBackToMenu.setBounds(331, 23, 103, 23);
		panel.add(btnBackToMenu);

		JButton btnConfirmOrder = new JButton("Godkend ordre");
		btnConfirmOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					confirmOrderClicked();
				} catch (SQLException | InvalidConcurrencyException e1) {
					// e1.printStackTrace();
					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(UnconfirmedOrderView.this, "Ordre kunne ikke godkendes.",
								"Prøv igen eller tjek ordren", JOptionPane.ERROR_MESSAGE);
					});
				}
			}
		});
		btnConfirmOrder.setBounds(331, 238, 103, 23);
		panel.add(btnConfirmOrder);

		JButton btnRejectOrder = new JButton("Afvis ordre");
		btnRejectOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rejectOrderClicked();
			}
		});
		btnRejectOrder.setBounds(331, 214, 103, 23);
		panel.add(btnRejectOrder);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 21, 331, 240);
		panel.add(scrollPane);

		tableUnconfirmedOrders = new JTable();
		tableUnconfirmedOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unconfirmedOrderTableModel = new UnconfirmedOrderTableModel();
		unconfirmedOrderTableModel.setData(getOrdersFromDB());
		tableUnconfirmedOrders.setModel(unconfirmedOrderTableModel);
		scrollPane.setViewportView(tableUnconfirmedOrders);
		executeUpdateToTable();
		ArrayList<Order> orders = getOrdersFromDB();
		ArrayList<Integer> orderIds = new ArrayList<Integer>();
		orderIds = getOrderIDsFromList(orders);

		HashMap<Integer, Customer> orderIdCustomerHashMap = new HashMap<Integer, Customer>();
		HashMap<Integer, Employee> orderIdEmployeeHashMap = new HashMap<Integer, Employee>();
		for (Integer currentOrderId : orderIds) {
			Customer currentCustomer = orderController.getCustomerFromOrderId(currentOrderId);
			Employee currentEmployee = orderController.getEmployeeFromOrderId(currentOrderId);

			orderIdCustomerHashMap.put(currentOrderId, currentCustomer);
			orderIdEmployeeHashMap.put(currentOrderId, currentEmployee);

			Order currentOrder = null;
			for (Order order : orders) {
				if (order.getOrderId() == currentOrderId) {
					currentOrder = order;
					break;
				}
			}

			if (currentOrder != null) {
				orderController.addCustomerToOrder(currentCustomer, currentOrder);
				orderController.addEmployeeToOrder(currentEmployee, currentOrder);
			}
		}
	}

	/**
	 * 
	 */
	private void executeUpdateToTable() {

		if (viewRunning) {
			if (exec == null || exec.isShutdown() || exec.isTerminated()) {
				exec = Executors.newSingleThreadScheduledExecutor();
				exec.scheduleAtFixedRate(() -> {
					try {
						updateSwingComponents();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}, 5, 30, TimeUnit.SECONDS);
			}
		} else {
			if (exec != null && !exec.isShutdown()) {
				exec.shutdownNow();
				exec.shutdown();
			}
		}

	}

	private void updateSwingComponents() throws SQLException {
		if (viewRunning) {
			System.out.println("Starting updateSwingComponents");
			try {

				ArrayList<Order> updatedOrders = getOrdersFromDB();
				unconfirmedOrderTableModel.updateList(updatedOrders);
				tableUnconfirmedOrders.revalidate();
				tableUnconfirmedOrders.repaint();
				ArrayList<Integer> orderIds = getOrderIDsFromList(updatedOrders);

				HashMap<Integer, Customer> orderIdCustomerHashMap = new HashMap<>();
				HashMap<Integer, Employee> orderIdEmployeeHashMap = new HashMap<>();
				for (Integer currentOrderId : orderIds) {
					Customer currentCustomer = orderController.getCustomerFromOrderId(currentOrderId);
					Employee currentEmployee = orderController.getEmployeeFromOrderId(currentOrderId);

					orderIdCustomerHashMap.put(currentOrderId, currentCustomer);
					orderIdEmployeeHashMap.put(currentOrderId, currentEmployee);

					Order currentOrder = null;
					for (Order order : updatedOrders) {
						if (order.getOrderId() == currentOrderId) {
							currentOrder = order;
						}
					}
					if (currentOrder != null) {
						orderController.addCustomerToOrder(currentCustomer, currentOrder);
						orderController.addEmployeeToOrder(currentEmployee, currentOrder);
					}
				}
				 System.out.println("Number of threads " + Thread.activeCount());

				tableUnconfirmedOrders.revalidate();
				tableUnconfirmedOrders.repaint();

				System.out.println("Finished updateSwingComponents");
			} catch (DataAccessException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The selected order will be rejected // Not implemented
	 */
	protected void rejectOrderClicked() {
		clearWindow();
		System.out.println("Not implemented");
	}

	/**
	 * The selected order will be confirmed on click
	 * 
	 * @throws SQLException
	 * @throws InvalidConcurrencyException
	 */
	protected void confirmOrderClicked() throws SQLException, InvalidConcurrencyException {
		int selectedRow = tableUnconfirmedOrders.getSelectedRow();
		ArrayList<Order> orders = unconfirmedOrderTableModel.getOrders();
		Order orderToUpdate = orders.get(selectedRow);
		orderController.updateOrderToConfirmed(orderToUpdate);
		backToMenuClicked();

	}

	/**
	 * Go back to menu from OrderView
	 */
	private void backToMenuClicked() {
		orderView.run(orderView);
		clearWindow();
	}

	/**
	 * Close current window
	 */
	public void clearWindow() {
		this.setVisible(false);
		viewNotRunning();
		this.dispose();
	}

	/**
	 * 
	 */
	private void viewNotRunning() {
		viewRunning = false;
		if (exec != null && !exec.isShutdown()) {
			exec.shutdownNow();
			exec.shutdown();
		}
	}

	public void display() {
		this.repaint();
		this.revalidate();
	}

	public ArrayList<Order> getOrdersFromDB() throws DataAccessException {
		return orderController.getUnconfirmedOrders();

	}

	public ArrayList<Integer> getOrderIDsFromList(ArrayList<Order> orders) {
		ArrayList<Integer> orderIds = new ArrayList<Integer>();
		for (Order o : orders) {
			int orderId = o.getOrderId();
			orderIds.add(orderId);
		}

		return orderIds;
	}
}
