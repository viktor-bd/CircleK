package control;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.InvalidConcurrencyException;
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

	public Order createOrderNoOrderIDOnlyDate(LocalDateTime date, boolean pickUpStatus, LocalDateTime pickupDate,
			boolean isPaid, Customer customer, Employee employee) {
		Order newOrder = new Order(date, pickUpStatus, pickupDate, false, null, null);
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

	public ArrayList<Integer> addOrderLinesToDB(Order order) throws SQLException {
		return orderDB.insertOrderLines(order.getOrderLines());
	}

	public OrderLine createOrderLine(Product product, int quantity) {
		OrderLine orderLine = new OrderLine(quantity, product);
		return orderLine;
	}

	public void confirmOrder(Order order) {
		order.setPickUpStatus(true);
	}

	public Customer findCustomerByPhone(String phoneNumber) throws DataAccessException {
		return personController.lookUpCustomerInDB(phoneNumber);
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
	private ArrayList<Order> getOrdersWithBoolean(boolean isConfirmed, ArrayList<Product> products) {
		ArrayList<Order> orders = orderDB.getOrdersWithBoolean(isConfirmed, products);
		return orders;
	}

	public ArrayList<Order> getConfirmedOrders() throws DataAccessException {
		return getOrdersWithBoolean(true, productController.findAllProductFromDB());
	}

	public ArrayList<Order> getUnconfirmedOrders() throws DataAccessException {
		return getOrdersWithBoolean(false, productController.findAllProductFromDB());
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

	public void updateOrderToConfirmed(Order foundOrder) throws SQLException, InvalidConcurrencyException {
		if (foundOrder.checkOrder()) {
			foundOrder.setIsConfirmed(true);
			insertUpdatedOrder(foundOrder);
		} else {
			System.err.println("Ordre kunne ikke godkendes");
		}
	}

	public int insertOrderFromGui(Order order) throws SQLException {
		return orderDB.insertOrderFromGUI(order);
	}

	public void insertUpdatedOrder(Order foundOrder) throws SQLException, InvalidConcurrencyException {
			orderDB.insertUpdatedOrder(foundOrder);

	}

	/**
	 * @param intGetQuantityFromTabel
	 * @param findAllProductFromDB
	 */
	public ArrayList<OrderLine> createOrderLinesFromView(int[] intQuantities, ArrayList<Product> products) {
		ArrayList<OrderLine> orderLinesToBeAddedToOrder = new ArrayList<OrderLine>();
		ArrayList<Product> OrderLineProducts = products;
		int[] intArray = intQuantities;
		for (int i = 0; i < products.size(); i++) {
			if (intArray[i] > 0) {
				OrderLine ol = createOrderLine(OrderLineProducts.get(i), intArray[i]);
				orderLinesToBeAddedToOrder.add(ol);
			}
		}
		return orderLinesToBeAddedToOrder;
	}

	/**
	 * @throws SQLException
	 * 
	 */
	public Employee getEmployeeFromEmployeeId(int id) throws SQLException {
		return personController.findEmployeeByEmployeeId(id);

	}

	/**
	 * @param orderLineIds
	 * @param orderId
	 * @throws SQLException
	 */
	public void insertOrderIDandOrderLinesIDsIntoDB(ArrayList<Integer> orderLineIds, int orderId) throws SQLException {
		orderDB.insertOrderIdAndOrderLinesIdsIntoDB(orderLineIds, orderId);
	}
}
