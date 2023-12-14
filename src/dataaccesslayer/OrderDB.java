package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Order;

public class OrderDB implements OrderDBIF{
    private Connection connection;
    private static final String insertQuery = "Insert sql here";
    private PreparedStatement insert; 
    
    public OrderDB() throws DataAccessException{
    	   try {
   			insert = DBConnection.getInstance().getDBcon().prepareStatement(insertQuery);
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

}
