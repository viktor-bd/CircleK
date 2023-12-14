package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Order;

public class OrderDB implements OrderDBIF {
    private Connection connection;
    private static final String insertQuery = "Insert sql here";
    private static final String selectAllQuery = "";
    private PreparedStatement insert; 
    private PreparedStatement selectAll;
    
    public OrderDB() throws DataAccessException{
    	   try {
   			insert = DBConnection.getInstance().getDBcon().prepareStatement(insertQuery);
   			selectAll = DBConnection.getInstance().getDBcon().prepareStatement(selectAllQuery);
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			throw new DataAccessException(e, "Could not prepare statement");
   		}
    }
    
	public void saveOrder(Order newOrder) {
        //TODO implement this
    }

    public void rejectOrder(Order rejectedOrder) {
        //TODO implement this
    }

    public void confirmOrder(Order confirmedOrder) {
        //TODO implement this
    }

	/**
	 * Should return the orders from the database that is either confirmed or not
	 * @param isConfirmed
	 */
	public List<Order> getOrdersWithBoolean(boolean isConfirmed) {
	        List<Order> orders = new ArrayList<>();

	        try {
	            // Construct the SQL query dynamically based on the isConfirmed parameter
	            String selectQuery = "SELECT * FROM orders WHERE isConfirmed = ?";
	            selectAll = DBConnection.getInstance().getDBcon().prepareStatement(selectQuery);
	            selectAll.setBoolean(1, isConfirmed);

	            // Execute the query
	            ResultSet resultSet = selectAll.executeQuery();

	            // Process the result set and populate the list of orders
	            while (resultSet.next()) {
	                // Assuming you have a method to create an Order object from ResultSet
	                Order order = createOrderFromResultSet(resultSet);
	                orders.add(order);
	            }

	            // Close the result set
	            resultSet.close();
	        } catch (SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }

	        return orders;
	    }
		
	}

}
