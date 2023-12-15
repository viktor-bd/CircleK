package dataaccesslayer;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Order;
import model.OrderLine;

public class OrderDB implements OrderDBIF {
	private Connection connection;

	private static final String insertOrderQuery = "INSERT INTO [Order] (date, pickUpStatus, pickupDate, isPaid, isConfirmed, customer_id, employee_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String insertOrderLineQuery = "INSERT INTO OrderLine (quantity, sku) VALUES (?, ?)";
	private static final String insertOrderOrderLineQuery = "INSERT INTO Order_OrderLine (order_id, orderline_id) VALUES (?, ?)";
	private PreparedStatement insertOrder;
	private PreparedStatement insertOrderLine;
	private PreparedStatement insertOrderOrderLine;

	public OrderDB() throws DataAccessException {
		try {
			connection = DBConnection.getInstance().getDBcon();
			insertOrder = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
			insertOrderLine = connection.prepareStatement(insertOrderLineQuery, Statement.RETURN_GENERATED_KEYS);
			insertOrderOrderLine = connection.prepareStatement(insertOrderOrderLineQuery);
		} catch (SQLException e) {
			throw new DataAccessException(e, "Could not prepare statement");
		}
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

	private ArrayList<Integer> insertOrderLines(ArrayList<OrderLine> orderLines) {
		ArrayList<Integer> orderLineID = new ArrayList<>();
		for (OrderLine orderLine : orderLines) {
		    try {
		        // Add the result of insertOrderLine to the existing ArrayList
		        orderLineID.addAll(insertOrderLine(orderLine));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
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

	private int convertBooleanToInt(boolean bool) {
		int bit = 0;
		if (bool = true) {
			bit = 1;
		}
		return bit;
	}

	private int insertOrder(Order newOrder) throws SQLException {

		insertOrder.setDate(1, java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
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

	public void rejectOrder(Order rejectedOrder) {
		
	}

	public void confirmOrder(Order confirmedOrder) {
		
	}
}
