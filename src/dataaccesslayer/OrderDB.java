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
			// insertOrder =
			// DBConnection.getInstance().getDBcon().prepareStatement(insertOrderQuery);
			insertOrder = DBConnection.getInstance().getDBcon().prepareStatement(insertOrderQuery,
					Statement.RETURN_GENERATED_KEYS);
			insertOrderLine = DBConnection.getInstance().getDBcon().prepareStatement(insertOrderLineQuery, Statement.RETURN_GENERATED_KEYS);
			insertOrderOrderLine = DBConnection.getInstance().getDBcon().prepareStatement(insertOrderOrderLineQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DataAccessException(e, "Could not prepare statement");
		}
	}

	public void saveOrder(Order newOrder) throws SQLException {
		// TODO implement this
		try {
			DBConnection.startTransaction();
			// Insert Order into Order table
			// System.out.println(insertOrder.toString()); // Log the SQL query

			int orderID = insertOrder(newOrder);
			// Insert OrderLines into OrderLine table
			ArrayList<Integer> orderLineID = insertOrderLines(newOrder.getOrderLines());
			// Insert into JOIN table Order_OrderLine
			insertIntoOrderOrderLine(orderLineID, orderID);

			DBConnection.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Vi ramte rollback");
			DBConnection.rollbackTransaction();
		}
	}

	private void insertOrderOrderLine(ArrayList<Integer> orderLineID, int orderID) {
		// TODO Auto-generated method stub
		for (int j = 0; j < orderLineID.size(); j++) {

			try {
				insertOrderOrderLine.setInt(1, orderID);
				insertOrderOrderLine.setInt(2, orderLineID.get(j));
				
				insertOrderOrderLine.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}*/
	private void insertIntoOrderOrderLine(ArrayList<Integer> orderLineID, int orderID) {
	    System.out.println(orderLineID.size());
		for (Integer orderLineId : orderLineID) {
	        try {
	            insertOrderOrderLine.setInt(1, orderID);
	            insertOrderOrderLine.setInt(2, orderLineId);
	            insertOrderOrderLine.executeUpdate();
	            System.out.println(orderLineId);
	        } catch (SQLException e) {
	            // Handle the exception appropriately (e.g., log it)
	            e.printStackTrace();
	        }
	    }
	}


	private ArrayList<Integer> insertOrderLines(ArrayList<OrderLine> orderLines) {
		// TODO Auto-generated method stub
		ArrayList<Integer> orderLineID = new ArrayList<>();

		for (OrderLine orderLine : orderLines) {
			try {

				orderLineID = insertOrderLine(orderLine);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderLineID;
	}


	private ArrayList<Integer> insertOrderLine(OrderLine orderLine) throws SQLException {

		insertOrderLine.setInt(1, orderLine.getProduct().getSku());
		insertOrderLine.setInt(2, orderLine.getQuantity());

		int rowsAffected = insertOrderLine.executeUpdate();

		if (rowsAffected == 0) {
			throw new SQLException("Inserting order line failed, no rows affected.");
		}
		ArrayList<Integer> orderLineID = new ArrayList<>();
		try (ResultSet generatedKeys = insertOrderLine.getGeneratedKeys()) {
			while (generatedKeys.next()) {
				orderLineID.add(generatedKeys.getInt(1));
			} if (orderLineID.isEmpty()){
				throw new SQLException("Inserting order line failed, no ID obtained.");
			}
		}
		return orderLineID;
	}
	private int convertBooleanToInt(boolean bool) {
		int bit = 0;
		if (bool = true) {
			bit = 1;
		} return bit;
	}

	private int insertOrder(Order newOrder) throws SQLException {
		insertOrder.setDate(1, java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		System.out.println( java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		// insertOrder.setInt(2, 0); // newOrder.isPickUpStatus());
		insertOrder.setInt(2, convertBooleanToInt(newOrder.isPickUpStatus()));
		insertOrder.setDate(3, java.sql.Date.valueOf(newOrder.getDate().toLocalDate()));
		// insertOrder.setInt(4, 0);// Order.isPaid());
		// insertOrder.setInt(5, 0); // isConfirmed());
		insertOrder.setInt(4, convertBooleanToInt(newOrder.isPaid()));
		insertOrder.setInt(5, 0); // A new order is never true
		insertOrder.setInt(6, newOrder.getCustomer().getCustomerId());
		insertOrder.setInt(7, newOrder.getEmployee().getEmployeeId());

		int rowsAffected = insertOrder.executeUpdate();

		if (rowsAffected == 0) {
			throw new SQLException("Inserting order failed, no rows affected.");
		}
		try (ResultSet generatedKeys = insertOrder.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1); // Return the generated order_id
			} else {
				throw new SQLException("Inserting order failed, no ID obtained.");
			}

		}
	}

	public void rejectOrder(Order rejectedOrder) {
		// TODO implement this
	}

	public void confirmOrder(Order confirmedOrder) {
		// TODO implement this
	}


	/*
	 * Obsolete FIXME
	 * private PreparedStatement getPreparedStatementInsertOrder() throws SQLException {
		PreparedStatement foundPrepStat = null;
		String baseQuery = "sql here";
		foundPrepStat = connection.prepareStatement(baseQuery);
		return foundPrepStat;
	}*/

}
