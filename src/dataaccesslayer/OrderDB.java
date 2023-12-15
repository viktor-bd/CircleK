package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Customer;
import model.Employee;
import model.Order;

public class OrderDB implements OrderDBIF {
	private Connection connection;
	private static final String insertQuery = "Insert sql here";
	private static final String selectAllQuery = "";
	private PreparedStatement insert;
	private PreparedStatement selectAll;

	public OrderDB() throws DataAccessException {
		try {
			connection = DBConnection.getInstance().getDBcon();
			insert = DBConnection.getInstance().getDBcon().prepareStatement(insertQuery);
			selectAll = DBConnection.getInstance().getDBcon().prepareStatement(selectAllQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}

	public void saveOrder(Order newOrder) {
		// TODO implement this
	}

	public void rejectOrder(Order rejectedOrder) {
		// TODO implement this
	}

	public void confirmOrder(Order confirmedOrder) {
		// TODO implement this
	}

	/**
	 * Should return the orders from the database that is either confirmed or not
	 * 
	 * @param isConfirmed
	 */
	
	public ArrayList<Order> getOrdersWithBoolean(boolean isConfirmed) {
		ArrayList<Order> orders = new ArrayList<Order>();
		ResultSet rs;
		int bit = 0;
		if (isConfirmed) {
			bit = 1;
		}
		try {
			// Construct the SQL query dynamically based on the isConfirmed parameter
			String selectQuery = "SELECT * FROM [dbo].[Order] WHERE isConfirmed = ?";
			selectAll = DBConnection.getInstance().getDBcon().prepareStatement(selectQuery);
			selectAll.setInt(1, bit);

			// Execute the query
			rs = selectAll.executeQuery();

			// Process the result set and populate the list of orders
			while (rs.next()) {
				// Assuming you have a method to create an Order object from ResultSet
				Order order = buildObject(rs);
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
	 * public Order(int orderId, boolean pickUpStatus, LocalDateTime pickupDate,
	 * boolean isPaid, Customer customer, Employee employee)
	 */
	private Order buildObject(ResultSet rs) throws SQLException {
		// placeholder Customer and Employee
		Customer customer = null;
		Employee employee = null;
		// Converting bit to boolean for PickUpStatus
		int booleanCheck = rs.getInt("pickUpStatus");
		boolean pickUpStatus = false;
		if (booleanCheck == 1) {
			pickUpStatus = true;
		}
		int booleanCheckPaid = rs.getInt("isPaid");
		boolean isPaid = false;
		if (booleanCheckPaid == 1) {
			pickUpStatus = true;
		}

		Order order = new Order(pickUpStatus, rs.getDate("pickupDate").toLocalDate(), isPaid, customer, employee);
		order.setOrderId(rs.getInt("order_id"));
		// rs.getInt("customer_id"),
		// rs.getInt("employee_id")),
		return order;
	}

}
