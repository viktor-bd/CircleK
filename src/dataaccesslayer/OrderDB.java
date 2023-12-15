package dataaccesslayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dataaccesslayer.DBConnection;
import dataaccesslayer.DataAccessException;
import model.Order;

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
			insertOrderOrderLine(orderLineID, orderID);

			DBConnection.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Vi ramte rollback");
			DBConnection.rollbackTransaction();
		}
	}

    public void rejectOrder(Order rejectedOrder) {
        //TODO implement this
    }

    public void confirmOrder(Order confirmedOrder) {
        //TODO implement this
    }

}
