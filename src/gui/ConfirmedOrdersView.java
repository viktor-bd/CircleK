/**
 * 
 */
package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import control.OrderController;
import dataaccesslayer.DataAccessException;

import model.Customer;
import model.Employee;
import model.Order;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
@SuppressWarnings("serial")
public class ConfirmedOrdersView extends JFrame {
	private JTable tableConfirmed;
	private ConfirmedOrderTableModel confirmedOrderTableModel;
	private OrderController orderController;
	private Employee employee;


	/**
	 * @throws DataAccessException 
	 * @throws SQLException 
	 * 
	 */
	public ConfirmedOrdersView(Employee employee) throws DataAccessException, SQLException {
		this.employee = employee;
		orderController = new OrderController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Bekræftede ordrer");
		setBounds(100, 100, 500, 300);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnReadyForPickup = new JButton("Marker klar");
		btnReadyForPickup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markReadyForPickupClicked();
			}
		});
		btnReadyForPickup.setBounds(326, 227, 98, 23);
		panel.add(btnReadyForPickup);

		JButton btnBackToMenu = new JButton("Tilbage");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToMenuClicked();
			}
		});
		btnBackToMenu.setBounds(321, 11, 103, 23);
		panel.add(btnBackToMenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 26, 296, 224);
		panel.add(scrollPane);
		
		tableConfirmed = new JTable();
		tableConfirmed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		confirmedOrderTableModel = new ConfirmedOrderTableModel();
		
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
		confirmedOrderTableModel.setData(orders);
		tableConfirmed.setModel(confirmedOrderTableModel);
		scrollPane.setViewportView(tableConfirmed);
	}
	
	public ArrayList<Integer> getOrderIDsFromList(ArrayList<Order> orders) {
		ArrayList<Integer> orderIds = new ArrayList<Integer>();
		for (Order o : orders) {
			int orderId = o.getOrderId();
			orderIds.add(orderId);
		}

		return orderIds;
	}
	
	/**
	 * Go back to menu from ConfirmedOrdersView
	 */
	private void backToMenuClicked() {

		OrderView orderView = new OrderView(employee);
		orderView.run(orderView);
		clearWindow();
	}

	/**
	 * Assign employee to order
	 */
	private void markReadyForPickupClicked() {
		// TODO Change status of instance PickUpStatus on selected Order
		
		// GUI updates to show new state
	}

	/**
	 * Close current window
	 */
	public void clearWindow() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void display() {
		
	}
	
	public ArrayList<Order> getOrdersFromDB() throws DataAccessException {
		return orderController.getConfirmedOrders();
	}
	
}
