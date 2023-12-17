package control;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.OrderDB;
import model.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

	private OrderDB orderDB;
	private PersonController personController;
	private ProductController productController;

	public OrderController() throws DataAccessException {
		this.orderDB = new OrderDB();
		this.personController = new PersonController();
		this.productController = new ProductController();
	}

	public Order createOrder(int orderID, boolean pickUpStatus, LocalDateTime pickupDate, boolean isPaid,
			Customer customer, Employee employee) {
		Order newOrder = new Order(orderID, pickUpStatus, pickupDate, isPaid, customer, employee);
		return newOrder;
	}

	public Order saveOrder(Order order) throws SQLException {
		orderDB.saveOrder(order);
		return order;
	}

	public Order addOrderLineToOrder(OrderLine orderLine, Order order) {
		order.addOrderLine(orderLine);
		return order;
	}

	public OrderLine createOrderLine(Product product, int quantity) {
		OrderLine orderLine = new OrderLine(quantity, product);
		return orderLine;
	}

	public void confirmOrder(Order order) {
		order.setPickUpStatus(true);
	}

	public void addCustomerToOrder(Customer customer, Order order) {
		order.setCustomer(customer);
	}

	public void testaddEmployeeToOrder(Employee testEmployee, Order newOrder) {
		newOrder.setEmployee(testEmployee);
	}

	/**
	 * Returns orders from DB that is either confirmed or not
	 */
	private ArrayList<Order> getOrdersWithBoolean(boolean isConfirmed) {
		ArrayList<Order> orders = orderDB.getOrdersWithBoolean(isConfirmed);
		return orders;
	}

	public ArrayList<Order> getConfirmedOrders() {
		return getOrdersWithBoolean(true);
	}

	public ArrayList<Order> getUnconfirmedOrders() {
		return getOrdersWithBoolean(false);
	}

	/**
	 * 
	 * @param orderId integer
	 * @return a single order from DB based on orderId
	 * @throws DataAccessException 
	 */
	public Order getOrderWithOrderId(int orderId) throws DataAccessException {
	    List<Product> products = productController.findAllProductFromDB();
	    return orderDB.getOrderWithOrderId(orderId, products);
	}


	public Customer getCustomerFromOrderId(int orderId) throws SQLException {
		return personController.getCustomerFromOrderId(orderId);

	}

	public Employee getEmployeeFromOrderId(int orderId) throws SQLException {
		return personController.getEmployeeFromOrderId(orderId);
	}

	public void updateOrderToConfirmed(Order foundOrder) throws SQLException {
		if (foundOrder.checkOrder()) {
			foundOrder.setIsConfirmed(true);
			insertUpdatedOrder(foundOrder);
		} else {
			System.err.println("Ordre kunne ikke godkendes");
		}
	}

	public void insertUpdatedOrder(Order foundOrder) throws SQLException {
		orderDB.insertUpdatedOrder(foundOrder);

	}
}
