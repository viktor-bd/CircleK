package dataaccesslayer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Customer;
import model.Employee;
import java.sql.Statement;
import model.Order;
import model.OrderLine;
import model.Product;

public class OrderDB implements OrderDBIF {
	private static final String insertOrderQuery = "INSERT INTO [Order] (date, pickUpStatus, pickupDate, isPaid, isConfirmed, customer_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String insertOrderLineQuery = "INSERT INTO OrderLine (quantity, sku) VALUES (?, ?)";
	private static final String insertOrderOrderLineQuery = "INSERT INTO Order_OrderLine (order_id, orderline_id) VALUES (?, ?)";
	private static final String updateOrderQuery = "UPDATE [Order] SET isConfirmed = 1 WHERE order_id = ?";
	private static final String selectConcurrencyTokenQuery = "SELECT concurrency_token FROM [Order] WHERE order_id = ?";
	private static final String selectOrderOnOrderLineQuery = "SELECT ool.order_id, ol.* FROM OrderLine ol JOIN Order_OrderLine ool ON ol.orderline_id = ool.orderline_id WHERE order_id = ?";
	private static final String insertIntoOrderOrderLineQ = "INSERT INTO Order_OrderLine (order_id, orderline_id) VALUES (?, ?)";
	private PreparedStatement insertOrder;
	private PreparedStatement insertOrderLine;
	private PreparedStatement insertOrderOrderLine;
	private PreparedStatement updateOrder;
	private PreparedStatement selectOrderOnOrderLine;
	private PreparedStatement insertIntoOrderOrderLine;
	private PreparedStatement selectConcurrencyToken;
	private Connection connection;

	public OrderDB() throws DataAccessException {
		try {
			connection = DBConnection.getInstance().getDBcon();
			insertOrder = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
			insertOrderLine = connection.prepareStatement(insertOrderLineQuery, Statement.RETURN_GENERATED_KEYS);
			insertOrderOrderLine = connection.prepareStatement(insertOrderOrderLineQuery);
			selectOrderOnOrderLine = connection.prepareStatement(selectOrderOnOrderLineQuery);
			insertIntoOrderOrderLine = connection.prepareStatement(insertIntoOrderOrderLineQ);
			updateOrder = connection.prepareStatement(updateOrderQuery);
			selectConcurrencyToken = connection.prepareStatement(selectConcurrencyTokenQuery);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}

	/**
	 * Should return the orders from the database that is either confirmed or not
	 * 
	 * @param isConfirmed
	 */

	public ArrayList<Order> getOrdersWithBoolean(boolean isConfirmed, ArrayList<Product> products) {
		ArrayList<Order> orders = new ArrayList<Order>();
		ResultSet rs;
		int bit = 0;
		if (isConfirmed) {
			bit = 1;
		}
		try {
			// Construct the SQL query dynamically based on the isConfirmed parameter
			String selectQuery = "SELECT * FROM [dbo].[Order] WHERE isConfirmed = ?";
			PreparedStatement selectAll = DBConnection.getInstance().getDBcon().prepareStatement(selectQuery);
			selectAll.setInt(1, bit);

			// Execute the query
			rs = selectAll.executeQuery();

			// Process the result set and populate the list of orders
			while (rs.next()) {
				Order order = buildOrderObject(rs, products); 
				// Add to list
				orders.add(order);
			}
			// Close the result set
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace(); // Handle the exception appropriately
		}

		return orders;
	}

	/*
	 * Builds the Order Object from the ResultSet
	 */
	private Order buildOrderObject(ResultSet rs, List<Product> products) throws SQLException {
		Customer customer = null;
		Employee employee = null;
		int booleanCheck = rs.getInt("pickUpStatus");
		boolean pickUpStatus = false;
		if (booleanCheck == 1) {
			pickUpStatus = true;
		}
		int booleanCheckPaid = rs.getInt("isPaid");
		boolean isPaid = false;
		if (booleanCheckPaid == 1) {
			isPaid = true;
		}
		LocalDateTime date = getLocalDateFromSQLDate(rs.getDate("date"));
		LocalDateTime pickupDate = getLocalDateFromSQLDate(rs.getDate("pickupDate"));
		byte[] concurrencyToken = rs.getBytes("concurrency_token");
		Order order = new Order(date, pickUpStatus, pickupDate, isPaid, customer, employee, concurrencyToken);
		order.setOrderId(rs.getInt("order_id"));
		order.setIsConfirmed(convertIntToBoolean(rs.getInt("isConfirmed")));
		ArrayList<OrderLine> orderLines = buildOrderLineObject(order, products);
		order.addOrderLines(orderLines);
		return order;
	}

	private ArrayList<OrderLine> buildOrderLineObject(Order order, List<Product> products) throws SQLException {
		ArrayList<OrderLine> orderLines = new ArrayList<>();

		try {
			selectOrderOnOrderLine.setInt(1, order.getOrderId());
			ResultSet rs = selectOrderOnOrderLine.executeQuery();

			while (rs.next()) {
				int quantity = rs.getInt("quantity");
				int sku = rs.getInt("sku");
				int orderLineId = rs.getInt("orderline_id");
				Product product = searchListOfProductsForProductWithGivenSku(products, sku);
				OrderLine orderLine = new OrderLine(quantity, product);
				orderLine.setOrderLineId(orderLineId);

				orderLines.add(orderLine);
			}
		} catch (SQLException e) {

		}
		return orderLines;
	}

	private Product searchListOfProductsForProductWithGivenSku(List<Product> products, int sku) {
		for (Product product : products) {
			if (product.getSku() == sku) {
				return product;
			}
		}
		return null;
	}

	public void saveOrder(Order newOrder) throws SQLException {
		try {
			DBConnection.startTransaction();
			addOrder(newOrder);
			addOrderLines(newOrder);
			DBConnection.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			DBConnection.rollbackTransaction();
		}
	}

	private void addOrder(Order newOrder) throws SQLException {
		int orderID = insertOrder(newOrder);
		newOrder.setOrderId(orderID);
	}

	private void addOrderLines(Order newOrder) throws SQLException {
		ArrayList<Integer> orderLineID = insertOrderLines(newOrder.getOrderLines());
		insertIntoOrderOrderLine(orderLineID, newOrder.getOrderId());
	}

	private void insertIntoOrderOrderLine(ArrayList<Integer> orderLineID, int orderID) throws SQLException {
		for (Integer orderLineId : orderLineID) {
			insertOrderOrderLine.setInt(1, orderID);
			insertOrderOrderLine.setInt(2, orderLineId);
			insertOrderOrderLine.executeUpdate();
		}
	}

	public ArrayList<Integer> insertOrderLines(ArrayList<OrderLine> orderLines) throws SQLException {
		ArrayList<Integer> orderLineID = new ArrayList<>();
		for (OrderLine orderLine : orderLines) {
			orderLineID.addAll(insertOrderLine(orderLine));
		}
		return orderLineID;
	}

	private ArrayList<Integer> insertOrderLine(OrderLine orderLine) throws SQLException {
		insertOrderLine.setInt(2, orderLine.getProduct().getSku());
		insertOrderLine.setInt(1, orderLine.getQuantity());
		System.out.println(orderLine.getProduct().getSku() + " " + orderLine.getQuantity());
		int rowsAffected = insertOrderLine.executeUpdate();
		if (rowsAffected == 0) {
			throw new SQLException("Inserting order line failed, no rows affected.");
		}
		ArrayList<Integer> orderLineID = new ArrayList<>();
		try (ResultSet generatedKeys = insertOrderLine.getGeneratedKeys()) {
			while (generatedKeys.next()) {
				int generatedKey = generatedKeys.getInt(1);
				System.out.println("Generated Key: " + generatedKey);
				orderLineID.add(generatedKey);
			}
			if (orderLineID.isEmpty()) {
				throw new SQLException("Inserting order line failed, no ID obtained.");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return orderLineID;
	}

	private boolean convertIntToBoolean(int bit) {
		boolean bool = false;
		if (bit == 1) {
			bool = true;
		}
		return bool;
	}

	private int convertBooleanToInt(boolean bool) {
		int bit = 0;
		if (bool = true) {
			bit = 1;
		}
		return bit;
	}

	private int insertOrder(Order newOrder) throws SQLException {
		insertOrder.setDate(1, java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
		System.out.println(java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		insertOrder.setInt(2, convertBooleanToInt(newOrder.isPickUpStatus()));
		insertOrder.setDate(3, java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		insertOrder.setInt(4, convertBooleanToInt(newOrder.isPaid()));
		insertOrder.setInt(5, 0);
		insertOrder.setInt(6, newOrder.getCustomer().getCustomerId());
		insertOrder.setInt(7, newOrder.getEmployee().getEmployeeId());
		int rowsAffected = insertOrder.executeUpdate();
		if (rowsAffected == 0) {
			throw new SQLException("Inserting order failed, no rows affected.");
		}
		try (ResultSet generatedKeys = insertOrder.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				int orderId = generatedKeys.getInt(1);
				return orderId;
			} else {
				throw new SQLException("Inserting order failed, no ID obtained.");
			}
		}
	}

	public int insertOrderFromGUI(Order newOrder) throws SQLException {
		insertOrder.setDate(1, java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		System.out.println(java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		insertOrder.setInt(2, convertBooleanToInt(newOrder.isPickUpStatus()));
		insertOrder.setDate(3, java.sql.Date.valueOf(newOrder.getPickupDate().toLocalDate()));
		insertOrder.setInt(4, convertBooleanToInt(newOrder.isPaid()));
		insertOrder.setInt(5, 0);
		insertOrder.setInt(6, newOrder.getCustomer().getCustomerId());
		insertOrder.setInt(7, newOrder.getEmployee().getEmployeeId());
		int rowsAffected = insertOrder.executeUpdate();
		if (rowsAffected == 0) {
			throw new SQLException("Inserting order failed, no rows affected.");
		}
		try (ResultSet generatedKeys = insertOrder.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				int orderId = generatedKeys.getInt(1);
				System.out.println(orderId);
				return orderId;
			} else {
				throw new SQLException("Inserting order failed, no ID obtained.");
			}
		}
	}

	private LocalDateTime getLocalDateFromSQLDate(Date date) {
		LocalDateTime localDateTime = null;
		if (date != null) {
			Instant instant = date.toLocalDate().atStartOfDay(ZoneOffset.UTC).toInstant();
			localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
		}
		return localDateTime;
	}

	public void rejectOrder(Order rejectedOrder) {

	}

	public void confirmOrder(Order confirmedOrder) {

	}

	public Order getOrderWithOrderId(int orderId, List<Product> products) {
		// TODO Extract prep and string to top of class
		Order foundOrder = null;
		ResultSet rs;
		try {
			String selectOrderQuery = "SELECT DISTINCT o.*, ol.orderline_id, ol.quantity, ol.sku FROM [dbo].[Order] o JOIN [dbo].[Order_OrderLine] oo ON o.order_id = oo.order_id JOIN OrderLine ol ON oo.orderline_id = ol.orderline_id WHERE o.order_id = ?";
			PreparedStatement selectOrder = connection.prepareStatement(selectOrderQuery);
			selectOrder.setInt(1, orderId);
			rs = selectOrder.executeQuery();
			while (rs.next()) {
				foundOrder = buildOrderObject(rs, products);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return foundOrder;
	}

	public void insertUpdatedOrder(Order foundOrder) throws SQLException, InvalidConcurrencyException {
		if (validateConcurrencyToken(foundOrder)) {
			updateOrder.setInt(1, foundOrder.getOrderId());
			updateOrder.executeUpdate();
		} else {
			throw new InvalidConcurrencyException(null, "Concurrency check failed for order ID: " + foundOrder.getOrderId());
		}
	}

	private boolean validateConcurrencyToken(Order foundOrder) throws SQLException {
		boolean valid = false;
		selectConcurrencyToken.setInt(1, foundOrder.getOrderId());
		var rs = selectConcurrencyToken.executeQuery();
		while (rs.next()) {

			var existingConcurrencyToken = rs.getBytes("concurrency_token");
			var oldConcurrencyToken = foundOrder.getConcurrencyToken();
			var validateTokens = Arrays.equals(oldConcurrencyToken, existingConcurrencyToken);

			if (validateTokens) {
				valid = true;
			}
		}
		return valid;
	}

	/**
	 * @param orderLineIds
	 * @param orderId
	 * @throws SQLException
	 */
	public void insertOrderIdAndOrderLinesIdsIntoDB(ArrayList<Integer> orderLineIds, int orderId) throws SQLException {
		for (Integer orderLineId : orderLineIds) {
			insertIntoOrderOrderLine.setInt(1, orderId);
			insertIntoOrderOrderLine.setInt(2, orderLineId);
			insertIntoOrderOrderLine.executeUpdate();
		}

	}
}
